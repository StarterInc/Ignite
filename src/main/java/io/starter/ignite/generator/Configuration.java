package io.starter.ignite.generator;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.starter.ignite.util.SystemConstants;

/**
 * Global Configuration
 * 
 * The following values can be set as System properties or on the command line:
 * 
 * <pre>
 * verbose
 * debug
 * swaggerLang
 * swaggerLib
 * dbGenDropTable
 * genOutputFolder
 * REACT_APP_NAME 
 * REACT_EXPORT_FOLDER
 * REACT_TEMPLATE_FOLDER
 * </pre>
 * 
 * @author John McMahon (@TechnoCharms)
 *
 */
public interface Configuration extends SystemConstants {

	static final Logger				logger							= LoggerFactory
			.getLogger(Configuration.class);

	public static String			adminServiceURL					= (System
			.getProperty("adminServiceURL") != null
					? System.getProperty("adminServiceURL")
					: "http://localhost:8099/");

	public static String			defaultHostname					= (System
			.getProperty("defaultHostname") != null
					? System.getProperty("defaultHostname")
					: "localhost");

	public static String			defaultPort						= (System
			.getProperty("defaultPort") != null
					? System.getProperty("defaultPort")
					: "8100");

	public static String			gitRepoId						= (System
			.getProperty("gitRepoId") != null ? System.getProperty("gitRepoId")
					: "StackGen");

	public static String			gitUserId						= (System
			.getProperty("gitUserId") != null ? System.getProperty("gitUserId")
					: "spaceghost69");

	public static String			developerOrganizationUrl		= (System
			.getProperty("developerOrganizationUrl") != null
					? System.getProperty("developerOrganizationUrl")
					: "http://starter.io");

	public static String			developerName					= (System
			.getProperty("developerName") != null
					? System.getProperty("developerName")
					: "Stack Dev");

	public static String			developerEmail					= (System
			.getProperty("developerEmail") != null
					? System.getProperty("developerEmail")
					: "info@stackgen.io");

	public static String			developerOrganization			= (System
			.getProperty("developerOrganization") != null
					? System.getProperty("developerOrganization")
					: "Starter Inc.");

	/**
	 * set the value of allowed CORS request paths
	 */
	public static String			CORSMapping						= (System
			.getProperty("CORSMapping") != null
					? System.getProperty("CORSMapping")
					: "*/**");

	public static String			CORSOrigins						= (System
			.getProperty("CORSMapping") != null
					? System.getProperty("CORSOrigins")
					: "localhost");

	public static String			adminUser						= (System
			.getProperty("adminUser") != null ? System.getProperty("adminUser")
					: "admin");

	public static String			adminPassword					= (System
			.getProperty("adminPassword") != null
					? System.getProperty("adminPassword")
					: "ch@ng3m3");

	public static boolean			skipDbGen						= (System
			.getProperty("skipDbGen") != null
					? Boolean.parseBoolean(System.getProperty("skipDbGen"))
					: false);

	public static boolean			skipMybatisGen					= (System
			.getProperty("skipMybatisGen") != null
					? Boolean.parseBoolean(System.getProperty("skipMybatisGen"))
					: false);

	public static boolean			skipMavenBuildGeneratedApp		= (System
			.getProperty("skipMavenBuildGeneratedApp") != null
					? Boolean.parseBoolean(System
							.getProperty("skipMavenBuildGeneratedApp"))
					: true);

	public static boolean			overwriteMode					= (System
			.getProperty("overwriteMode") != null
					? Boolean.parseBoolean(System.getProperty("overwriteMode"))
					: true);

	public static boolean			iteratePluginGen				= (System
			.getProperty("iteratePluginGen") != null ? Boolean
					.parseBoolean(System.getProperty("iteratePluginGen"))
					: true);

	public static boolean			mergePluginGen					= (System
			.getProperty("mergePluginGen") != null
					? Boolean.parseBoolean(System.getProperty("mergePluginGen"))
					: false);

	public static boolean			verbose							= (System
			.getProperty("verbose") != null
					? Boolean.parseBoolean(System.getProperty("verbose"))
					: false);

	public static final boolean		debug							= (System
			.getProperty("debug") != null
					? Boolean.parseBoolean(System.getProperty("debug"))
					: false);

	// spring, java, resteasy
	public static final String		swaggerLang						= (System
			.getProperty("swaggerLang") != null
					? System.getProperty("swaggerLang")
					: "spring");

	// spring-boot ,jersey2
	public static final String		swaggerLib						= (System
			.getProperty("swaggerLib") != null
					? System.getProperty("swaggerLib")
					: "spring-boot");

	public static String			artifactId						= (System
			.getProperty("artifactId") != null
					? System.getProperty("artifactId")
					: "ignite");

	public static final String		schemaName						= (System
			.getProperty("schemaName") != null
					? System.getProperty("schemaName")
					: "ignite");

	public static String			TABLE_NAME_PREFIX				= schemaName
			+ "$";

	// DML/DB section
	// default is lowercase, this forces uppercase
	public static boolean			columnsUpperCase				= (System
			.getProperty("columnsUpperCase") != null ? Boolean
					.parseBoolean(System.getProperty("columnsUpperCase"))
					: false);

	public static boolean			dbGenDropTable					= (System
			.getProperty("dbGenDropTable") != null
					? Boolean.parseBoolean(System.getProperty("dbGenDropTable"))
					: false);

	public static String			CREATE_TABLE					= "CREATE TABLE";
	public static String			CREATE_TABLE_BEGIN_BLOCK		= "(";
	public static String			CREATE_TABLE_END_BLOCK			= ");";
	public static String			DROP_TABLE						= "DROP TABLE";
	public static String			ALTER_TABLE						= "ALTER TABLE";
	public static String			RENAME_TABLE_PREFIX				= "BK_";
	public static String			TUPLE_TABLE_SUFFIX				= "_idx";

	// end DML section

	public static String			javaGenFolderName					= (System
			.getProperty("javaGenFolderName") != null
					? System.getProperty("javaGenFolderName")
					: "/gen");

	public static String			genOutputFolder					= (System
			.getProperty("genOutputFolder") != null
					? System.getProperty("genOutputFolder")
					: rootFolder)
			+ javaGenFolderName;

	public static String			JAVA_GEN_ARCHIVE_FOLDER			= "/archive"
			+ javaGenFolderName;

	public static String			javaGenArchivePath				= (System
			.getProperty("javaGenArchivePath") != null
					? System.getProperty("javaGenArchivePath")
					: rootFolder + JAVA_GEN_ARCHIVE_FOLDER);

	public static String			SOURCE_MAIN						= (System
			.getProperty("SOURCE_MAIN") != null
					? System.getProperty("SOURCE_MAIN")
					: rootFolder)
			+ "/src/main";

	public static String			JAVA_GEN_SRC_FOLDER				= genOutputFolder
			+ "/src/main/java";

	public static File				JAVA_GEN_SRC					= new File(
			JAVA_GEN_SRC_FOLDER);

	public static String			JAVA_GEN_RESOURCES_FOLDER		= genOutputFolder
			+ "/resources";

	public static String			PUBLIC_ROOT						= javaGenFolderName
			+ "/public";

	public static String			SOURCE_MAIN_JAVA				= SOURCE_MAIN
			+ "/java";

	public static String			SOURCE_RESOURCES				= "/src/resources";

	public static final boolean		DISABLE_DATA_FIELD_ASPECT		= true;
	public static final boolean		DISABLE_SECURE_FIELD_ASPECT		= false;

	// ## SwaggerGen OPEN API
	public static String			artifactVersion					= (System
			.getProperty("artifactVersion") != null
					? System.getProperty("artifactVersion")
					: "1.0.1-SNAPSHOT");

	public static String			ADD_GEN_CLASS_NAME				= "Service";

	public static String			orgPackage						= (System
			.getProperty("orgPackage") != null
					? System.getProperty("orgPackage")
					: "io.starter.");

	public static String			orgFolder						= (System
			.getProperty("orgFolder") != null ? System.getProperty("orgFolder")
					: "io/starter/");

	public static String			SPEC_LOCATION					= rootFolder
			+ SOURCE_RESOURCES + "/openapi_specs/";

	public static String			PLUGIN_SPEC_LOCATION			= SPEC_LOCATION
			+ "plugins/";

	public static String			CONFIG_FILE						= rootFolder
			+ SOURCE_RESOURCES + "/swagger_config.json";

	public static String			IGNITE_MODEL_PACKAGE			= orgPackage
			+ artifactId + ".model";

	public static String			API_MODEL_PACKAGE				= orgPackage
			+ artifactId + ".model";

	public static String			API_PACKAGE						= orgPackage
			+ artifactId + ".api";

	public static String			MODEL_PACKAGE					= orgPackage
			+ artifactId + ".model";

	public static String			MODEL_DAO_PACKAGE				= MODEL_PACKAGE
			+ ".dao";

	public static String			INVOKER_PACKAGE					= orgPackage
			+ artifactId + ".invoker";

	public static String			LONG_DATE_FORMAT				= "MMM/d/yyyy HH:mm:ss Z";

	static SimpleDateFormat			DATE_FORMAT						= new SimpleDateFormat(
			LONG_DATE_FORMAT);

	public static String			PACKAGE_DIR						= orgFolder
			+ artifactId;

	public static String			MODEL_PACKAGE_DIR				= PACKAGE_DIR
			+ "/model/";

	public static String			API_PACKAGE_DIR					= PACKAGE_DIR
			+ "/api/";

	public static String			MODEL_DAO_PACKAGE_DIR			= PACKAGE_DIR
			+ "/model/dao/";

	public static String			MODEL_CLASSES					= JAVA_GEN_SRC_FOLDER
			+ "/" + MODEL_PACKAGE_DIR;

	public static String			MODEL_DAO_CLASSES				= JAVA_GEN_SRC_FOLDER
			+ MODEL_DAO_PACKAGE_DIR;

	public static String			API_CLASSES						= JAVA_GEN_SRC_FOLDER
			+ API_PACKAGE_DIR;

	// ## Mybatis
	public static int				DB_TIMEOUT						= 10000;

	public static String			TIMEZONE_OFFSET					= (System
			.getProperty("TIMEZONE_OFFSET") != null
					? System.getProperty("TIMEZONE_OFFSET")
					: "-08:00");

	public static final String		MYBATIS_COL_ENUM_FLAG			= "ENUM";
	public String					ANNOTATAION_CLASS				= "io.starter.ignite.security.securefield.SecureField";

	public static String			SQL_MAPS_PATH					= orgFolder
			+ artifactId + "/model/dao/";

	public static final String		MYBATIS_GEN_CONFIG_TEMPLATE		= rootFolder
			+ SOURCE_RESOURCES + "/templates/MyBatisGeneratorConfig.xml";

	public static final String		MYBATIS_GEN_CONFIG_OUT			= genOutputFolder
			+ SOURCE_RESOURCES + "/MyBatisGeneratorConfig.xml";

	public static final String		MYBATIS_CONFIG_TEMPLATE			= rootFolder
			+ SOURCE_RESOURCES + "/templates/MyBatisConfig.xml";

	public static final String		MYBATIS_CONFIG_OUT				= genOutputFolder
			+ javaGenFolderName + "/src/main/resources/MyBatisConfig.xml";

	public static List<String>		FOLDER_SKIP_LIST				= new ArrayList<>(
			Arrays.asList(javaGenFolderName, "org", "swagger", "node_modules"));

	// ## WEB
	// output generated WP PHP code here
	public static String			WP_PLUGIN_ROOT					= SOURCE_MAIN
			+ SOURCE_MAIN + "/wp";

	// output the web content here (including JSP)
	public static String			WEB_ROOT						= SOURCE_MAIN
			+ "/webapp";

	// output the javascript here
	public static String			WEB_JS_ROOT						= SOURCE_MAIN
			+ "/webapp/js";
	public static String			SPRING_DELEGATE					= "ApiDelegate";

	public static String			IGNITE_GEN_MODEL_ENHANCEMENTS	= "igniteGenerateModelEnhancements";
	public static String			IGNITE_GEN_MODEL_CRUD_OPS		= "igniteGenerateCRUDOps";

	public static String			IGNITE_GEN_REST_PATH_PREFIX		= "data/";
	public static String			LINE_FEED						= "\r\n";

	public static int				DB_ENCRYPTED_COLUMN_MULTIPLIER	= 5;

	public static String			language						= "en";													// language
	public static String			country							= "US";													// country

	public final static String[]	RESERVED_WORD_LIST				= {
			"ApiResponse" };

	static final String				GENERATED_TEXT_BLOCK			= "Starter StackGen 'JavaGen' Generated";

	/**
	 * App-wide utility method for checking against list of reserved words
	 * 
	 * @param the string to check
	 * @return whether the string is in the reserved word list (case insensitive)
	 */
	public static boolean checkReservedWord(String k) {
		for (String x : RESERVED_WORD_LIST) {
			if (x.equalsIgnoreCase(k))
				return false;
		}
		return true;
	}

	/**
	 * utility method for setting config values from a JSON object 
	 * 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean copyJSONConfigToSysprops(final JSONObject config) throws IllegalArgumentException, IllegalAccessException {
		String[] names = JSONObject.getNames(config);
		for (String fx : names) {
			try {
				logger.warn("JSON Config setting:" + fx + ":"
						+ config.get(fx).toString());
				System.setProperty(fx, config.get(fx).toString());
			} catch (Exception e) {
				;
			}
		}
		return true;
	}

	/**
	 * utility method for setting sysprops values from this Configuration
	 * 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean copyConfigurationToSysprops() throws IllegalArgumentException, IllegalAccessException {
		Field[] f = Configuration.class.getFields();
		for (Field fx : f) {
			try {
				logger.warn("Config setting:" + fx.getName() + ":"
						+ fx.get(null).toString());
				System.setProperty(fx.getName(), fx.get(null).toString());
			} catch (Exception e) {
				;
			}
		}
		return true;
	}

}