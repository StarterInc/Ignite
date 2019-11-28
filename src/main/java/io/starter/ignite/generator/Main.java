package io.starter.ignite.generator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.util.FileUtil;
import org.codehaus.plexus.util.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.starter.ignite.util.ASCIIArtPrinter;

/**
 * <h2>Generates an app from a Swagger Spec</h2>
 *
 * Features the ability to merge spec files from a templates directory as well
 * as auto-creation of CRUD methods hooked up to Ignite Data Objects.
 * 
 * <pre>
 *   - YAML -> Swagger client
 *   - Swagger Client -> entity classes
 *   - Swagger Client -> DML for database
 *   - Swagger CLient -> React-native JS screens
 * </pre>
 * 
 * <h2>NOTE: Generate a SecureField key</h2>
 * 
 * <pre>
 *	java io.starter.ignite.generator.Main -Dio.starter.generateKey=<secretkey> -jar StarterIgnite-1.0.1.jar
 * </pre>
 *
 * @author John McMahon ~ github: SpaceGhost69 | twitter: @TechnoCharms
 *
 */
@SpringBootApplication
public class Main implements Configuration, CommandLineRunner {

	protected static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		SpringApplication.run(Main.class, args);

	}

	/**
	 * ensure existence
	 * 
	 * @throws IOException
	 */
	public static void checkLog4j() throws IOException {
		String udir = System.getProperty("user.dir");
		File log4j = new File(udir + "/log4j.properties");
		if (!log4j.exists()) {
			File copyLog4j = new File(udir + "/src/resources/log4j.properties");
			FileUtil.copyFile(copyLog4j, log4j);
		}
	}

	@Override
	public void run(String... args) throws IllegalArgumentException, IllegalAccessException, NoSuchAlgorithmException {
		Configuration.copyConfigurationToSysprops();
		String inputSpecFile = "simple_cms.yml"; // "trade_automator.yml";
													// //
		// "simple_cms.yml";
		// String inputSpecFile = "StarterIgnite.yml";

		// check to see if the String array is empty
		if (args == null || args.length == 0 || args[0] == null) {
			System.out.println("No command line arguments Usage:");
			System.out.println();
			System.out.println("java io.starter.ignite.generator.Main <input.yml> -D<option_name>=<option_value> ... ");
			return;
		} else {
			// copy any args to sysprops
			for (String argument : args) {
				if (argument.toLowerCase().endsWith(".yml") || argument.toLowerCase().endsWith(".json")) {
					inputSpecFile = argument;
				} else if (argument.contains("=")) {
					int p = argument.indexOf("=");
					try {
						String narg = argument.substring(0, argument.indexOf(p));
						String varg = argument.substring(argument.indexOf(p));
						System.setProperty(narg, varg);
					} catch (Exception e) {
						logger.warn("Could not set property from arg: " + argument + " due to: " + e.toString());
					}
				}
			}
		}

		if (System.getProperty("schemaFile") != null) {
			inputSpecFile = System.getProperty("schemaFile");
		}
		if (System.getProperty("io.starter.generateKey") != null) {
			if (args.length == 2) {
				System.out.println("--- Begin Generated Key ---");
				System.out.println(generateEncryptionKey(args[1]));
				System.out.println("--- End Generated Key ---");
			} else {
				System.out.println("Usage:");
				System.out.println(
						"java io.starter.ignite.generator.Main -Dio.starter.generateKey=<secretkey> -jar StarterIgnite-1.0.1.jar");
			}
			return;
		}
		logger.info("with: " + inputSpecFile + (args != null ? " and args: " + args.toString() : ""));

		try {
			generateStack(inputSpecFile);
		} catch (Exception e) {
			logger.error("STACKGEN FAILURE: " + e.toString());
			throw new RuntimeException("STACKGEN FAILURE: " + e.toString());
		}
	}

	/**
	 * Create and initialize a new SwaggerGen from a JSON config object
	 * 
	 * @param inputSpec JSONObject containing config data
	 * @return
	 */
	public static void generateApp(JSONObject config) throws Exception {

		try {
			Configuration.copyJSONConfigToSysprops(config);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error("Copying Configuration values from JSON to Sysprops failed while starting App Generation");
			e.printStackTrace();
		}
		generateStack(config.getString("schemaFile"));

	}

	/**
	 * Step by step process to create Stack from spec file
	 * 
	 * @param inputSpecFile
	 */
	public static void generateStack(String inputSpecFile) throws Exception {
		System.out.println(ASCIIArtPrinter.print());
		System.out.println();
		logger.info("Starting App Generation");

		// Clear out the gen and
		if (overwriteMode) {
			initOutputFolders();
		}

		// generate swqgger api clients
		List<File> gfiles = null;
		if (iteratePluginGen) {
			gfiles = iteratePluginsGen(inputSpecFile);
		} else if (mergePluginGen) {
			gfiles = mergePluginsGen(inputSpecFile);
		} else {
			SwaggerGen swaggerGen = new SwaggerGen(SPEC_LOCATION + inputSpecFile);
			gfiles = swaggerGen.generate();
		}

		logger.info("####### SWAGGER Generated: " + (gfiles != null ? gfiles : " NO ") + " Source Files");

		JavaGen.compile(PACKAGE_DIR);
		JavaGen.compile(API_PACKAGE_DIR);
		try {
			JavaGen.compile(MODEL_PACKAGE_DIR);
		} catch (Throwable e) {
			// this one fails in regen mode sometimes. ignore
		}

		if (!skipDbGen) {
			// generate corresponding DML
			// statements to create a JDBC database
			// execute DB creation, connect and test
			DBGen.createDatabaseTablesFromModelFolder();
		}

		// generate MyBatis client classes XML configuration file
		if (!skipMybatisGen) {
			MyBatisGen.createMyBatisFromModelFolder();
		}

		// compile the DataObject Classes
		JavaGen.compile(MODEL_DAO_PACKAGE_DIR);

		JavaGen.compile(PACKAGE_DIR);

		// delegates calls to/from api to the mybatis entity
		JavaGen.generateClassesFromModelFolder();

		// copy misc files into gen project
		copyStaticFiles(staticFiles);

		// package the microservice for deployment
		if (!skipMavenBuildGeneratedApp) {
			MavenBuilder.build();
		}

		logger.info("App Generation Complete.");

	}

	/**
	 * Merge the Plugin Swaggers into the main Swagger then Run the Swagger gen from
	 * the main Swagger
	 * 
	 * @param inputSpecFile of Main Swagger gen
	 * @return
	 */
	private static List<File> mergePluginsGen(String inputSpecFile) {
		SwaggerGen swaggerGen = new SwaggerGen(SPEC_LOCATION + inputSpecFile);

		// iterate the files in the plugins folder
		String[] fin = Main.getPluginFiles();
		if (fin != null) {
			for (String fs : fin) {
				File f = new File(fs);
				logger.info("Generating Plugin Schema: " + fs);
				SwaggerGen pluginSwag = new SwaggerGen(f.getAbsolutePath());
				swaggerGen.addSwagger(pluginSwag);
			}
		}
		return swaggerGen.generate();
	}

	/**
	 * Iterate and run the Plugin Swagger gens, finally Run the Main Swagger gen
	 * 
	 * @param inputSpecFile of Main Swagger gen
	 */
	private static List<File> iteratePluginsGen(String inputSpecFile) {
		List<File> allGen = new ArrayList<File>();
		SwaggerGen swaggerGen = new SwaggerGen(SPEC_LOCATION + inputSpecFile);

		// iterate the files in the plugins folder
		String[] fin = Main.getPluginFiles();
		for (String fs : fin) {
			File f = new File(PLUGIN_SPEC_LOCATION + fs);
			logger.info("Generating Plugin: " + f.getName());
			SwaggerGen pluginSwag = new SwaggerGen(f.getAbsolutePath());
			List<File> fxs = pluginSwag.generate();
			allGen.addAll(fxs);
		}
		allGen.addAll(swaggerGen.generate());
		logger.info("####### SWAGGER Generated: " + allGen.size() + " Source Files");
		return allGen;
	}

	/**
	 * generates an encryption key suitable for use by the SecureField encryption
	 * algorithm
	 * 
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @see io.starter.ignite.security.securefield.SecureField
	 */
	public static String generateEncryptionKey(String salt) throws NoSuchAlgorithmException {
		return io.starter.ignite.security.crypto.EncryptionUtil.generateKey();
	}

	/**
	 * get YML or JSON Schema files from the plugins dir
	 * 
	 * for now we go one level deep only
	 * 
	 * @return array of schema files
	 */
	public static String[] getPluginFiles() {
		File pluginDir = new File(PLUGIN_SPEC_LOCATION);
		if (!pluginDir.exists()) {
			throw new IllegalStateException("getPluginFiles Failure: no path here " + PLUGIN_SPEC_LOCATION);
		}
		String[] pluginFiles = pluginDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".json"))
					return true;
				if (name.toLowerCase().endsWith(".yml") || name.toLowerCase().endsWith(".yaml"))
					return true;

				// otherwise
				return false;
			}
		});

		if (pluginFiles != null && pluginFiles.length < 1) {
			throw new IllegalStateException("Gen.getPluginFiles Failure: no plugin schemas found: "
					+ PLUGIN_SPEC_LOCATION + ". Check the PLUGIN_SPEC_LOCATION config value.");
		}
		return pluginFiles;
	}

	private static void initOutputFolders() {
		File genDir = new File(genOutputFolder);
		logger.info("Initializing output folder: " + genOutputFolder + " exists: " + genDir.exists());
		if (genDir.exists()) {
			String fx = javaGenArchivePath + "." + System.currentTimeMillis();
			File toF = new File(fx);
			if (!toF.exists()) {
				toF.mkdirs();
			}
			if (!genDir.renameTo(toF)) {
				throw new IgniteException("Could not rename: " + genOutputFolder + " to: " + fx);
			}

			// TODO: ZipFileWriter.zip(toF);

			genDir = new File(genOutputFolder);
			genDir.mkdirs();
		}

		boolean outputDir = new File(Configuration.genOutputFolder + "/src/").mkdirs();
		if (!outputDir) {
			logger.error("Could not init: " + outputDir + ". Exiting.");
		}
	}

	/**
	 * a list of file paths to copy relative to project root
	 */
	protected static String[][] staticFiles = {
			// { "/src/resources/templates/application.yml",
			// "/src/main/resources/application.yml" },
			{ "/src/resources/templates/log4j.properties", "/src/main/resources/log4j.properties" },
			{ "/logs/logfile_placeholder.txt", "/logs/logfile_placeholder.txt" },
			{ "/src/main/java/io/starter/spring/boot/stackgen-pro.txt",
					"/src/main/java/io/starter/spring/boot/stackgen-pro.txt" }

	};

	protected static void copyStaticFiles(String[][] staticFiles) {
		File genDir = new File(genOutputFolder);
		logger.info("Copying static files to folder: " + genOutputFolder + " exists: " + genDir.exists());
		if (genDir.exists()) {
			for (String[] fx : staticFiles) {
				File fromF = new File(rootFolder + fx[0]);
				File toF = new File(genOutputFolder + fx[1]);
				if (fromF.exists()) {
					logger.info("Copying static file : " + fromF + " to: " + toF);

					if (!toF.exists()) { // prep dest folder
						toF.mkdirs();
						toF.delete();
					}
					try {
						FileUtils.copyFile(fromF, toF);
					} catch (Exception e) {
						logger.error("Could not copy static file. " + e.toString());
					}
				} else {
					logger.warn("Missing expected static file : " + fromF.getAbsolutePath());
				}
			}
		}
		logger.info("Done copying static files to " + genOutputFolder);
	}
}
