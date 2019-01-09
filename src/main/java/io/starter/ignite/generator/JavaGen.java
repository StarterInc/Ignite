package io.starter.ignite.generator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import io.starter.toolkit.StringTool;
import io.swagger.annotations.ApiModelProperty;

/**
 * Generating Java code
 *
 * @author John McMahon (@TechnoCharms)
 *
 */
public class JavaGen extends Gen implements Generator {

	protected static final Logger logger = LoggerFactory
			.getLogger(JavaGen.class);

	public static Map<?, ?> createClasses(Class<?> c) throws Exception {
		final JavaGen gen = new JavaGen();
		final Map<?, ?> classesToGenerate = gen.processClasses(c, null, gen);
		return classesToGenerate;
	}

	@Override
	public Object createMember(Field fld) {
		return null;
	}

	@Override
	public Object createAccessor(Field fld) {

		boolean isSecure = false;
		boolean isDataField = true;
		String dataField = null;

		try {
			ApiModelProperty apimp = Gen.getApiModelPropertyAnnotation(fld);
			isSecure = apimp.secureField();
			dataField = apimp.dataField();
			isDataField = dataField != null;
		} catch (NoSuchMethodException | SecurityException e1) {
			// no worries
		}

		final String fieldName = fld.getName();
		if (fieldName.startsWith("ajc$") || fieldName.equals("delegate")
				|| fieldName.equals("serialVersionUID")) {
			return null;
		}

		final String className = fld.getDeclaringClass().getName();
		String memberName = className.substring(className.lastIndexOf(".") + 1);
		memberName += "Bean";

		final Class<?> fieldType = fld.getType();
		String fldName = StringTool.getGetMethodNameFromVar(fieldName);

		try {
			final MethodSpec ret = MethodSpec.methodBuilder(fldName)
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()) + "/r/n" + "/r/n"
							+ "@see "
							+ fld.getDeclaringClass().getSuperclass().getName()
							+ "/r/n" + "/r/n" + "@return the value of: "
							+ fieldName)
					.addModifiers(Modifier.PUBLIC).returns(fieldType)
					.addStatement("return " + memberName + "." + fieldName)
					.build();
			return ret;
		} catch (final Exception e) {
			logger.error("COULD NOT GENERATE FIELD: " + memberName + " "
					+ e.toString());
		}
		return null;
	}

	@Override
	public Object createSetter(Field fld) {

		final String fieldName = fld.getName();

		final String className = fld.getDeclaringClass().getName();
		String memberName = className.substring(className.lastIndexOf(".") + 1);
		memberName += "Bean";

		if (fieldName.startsWith("ajc$") || fieldName.equals("delegate")
				|| fieldName.equals("serialVersionUID")) {
			return null;
		}
		final Class<?> fieldType = fld.getType();
		final String fldNameSet = StringTool.getSetMethodNameFromVar(fieldName);

		try {
			final MethodSpec ret = MethodSpec.methodBuilder(fldNameSet)
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					// .addModifiers(Modifier.PUBLIC).addAnnotation(AnnotationSpec.builder(DataField.class).build())
					.addModifiers(Modifier.PUBLIC)
					.addParameter(fieldType, fieldName + "Val")
					.addStatement(memberName + "." + fieldName + " = "
							+ fieldName + "Val")
					.build();

			return ret;
		} catch (final Exception e) {
			logger.error("ERROR CREATING SETTER for: " + fieldName + " "
					+ e.toString());
		}
		return null;
	}

	public FieldSpec createLoggerField(String className) throws ClassNotFoundException {

		final Class<?> cx = Class.forName("org.slf4j.Logger");
		return FieldSpec.builder(cx, "log").addModifiers(Modifier.PRIVATE)
				.initializer("org.slf4j.LoggerFactory\n"
						+ "			.getLogger(" + className
						+ ADD_GEN_CLASS_NAME + ".class)")
				.build();
	}

	// ## MYBATIS INSERT/UPDATE/DELETE/LIST

	public FieldSpec createSQLSessionFactoryField() throws ClassNotFoundException {

		final Class<?> cx = Class
				.forName("org.apache.ibatis.session.SqlSessionFactory");
		return FieldSpec.builder(cx, "sqlSessionFactory")
				.addModifiers(Modifier.PRIVATE)
				.initializer("io.starter.ignite.security.dao.MyBatisConnectionFactory\n"
						+ "			.getSqlSessionFactory()")
				.build();
	}

	/**
	 * create get object mapper method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createGetObjectMapper(String className) {
		String methodText = "return objectMapper";
		try {

			ClassName cx = ClassName
					.get("com.fasterxml.jackson.databind", "ObjectMapper");
			return MethodSpec.methodBuilder("getObjectMapper")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating getObjectMapper method for: "
					+ className + " " + e.toString());
		}
		return null;
	}

	/**
	 * create get object mapper method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createGetAcceptHeader(String className) {
		String methodText = "return httpServletRequest.getHeader(\"accept\")";
		try {

			ClassName cx = ClassName.get("java.lang", "String");
			return MethodSpec.methodBuilder("getAcceptHeader")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating getAcceptHeader method for: "
					+ className + " " + e.toString());
		}
		return null;
	}

	/**
	 * create getHttpServletRequest mapper method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createGetHttpServletRequest(String className) {
		String methodText = "return httpServletRequest";

		try {

			ClassName cx = ClassName
					.get("javax.servlet.http", "HttpServletRequest");
			return MethodSpec.methodBuilder("getHttpServletRequest")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating createGetHttpServletRequest method for: "
					+ className + " " + e.toString());
		}
		return null;
	}

	/**
	 * create setBean method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createSetBean(String className) {

		String bname = getBaseJavaName(className);
		String methodText = bname + "Bean = (" + bname + ")bx";
		try {
			return MethodSpec.methodBuilder("setBean")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.addParameter(Object.class, "bx").build();
		} catch (final Exception e) {
			logger.error("ERROR creating setBean method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	/**
	 * create MyBatis list method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createList(String className) {
		String mapperName = getMyBatisName(className);
		String methodText = "		final org.apache.ibatis.session.SqlSession session = sqlSessionFactory\n"
				+ "				.openSession(true);\n" + "\n" + "		"
				+ mapperName + "Example example = new " + mapperName
				+ "Example();\n" + "		" + mapperName
				+ "Example.Criteria cx = example\n"
				+ "				.createCriteria();\n"
				+ "		cx.andIdEqualTo(getId());\n" + "\n"
				+ "		final java.util.List<" + mapperName
				+ "> rows = session\n" + "				.selectList(\""
				+ mapperName + "Mapper.selectByExample\", example);\n" + "\n"
				+ "		session.close();\n" + "		return rows";
		try {

			ClassName cx = ClassName.get("java.util", "List");
			return MethodSpec.methodBuilder("list")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating list method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	/**
	 * create MyBatis insert method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createInsert(String className) {
		String mapperName = getMyBatisName(className);
		String methodText = "		final org.apache.ibatis.session.SqlSession session = sqlSessionFactory.openSession(true);\n"
				+ "		int rows = -1;\n" + "		try {\n"
				+ "			rows = session.insert(\"" + mapperName
				+ "Mapper.insertSelective\", this);\n"
				+ "		// commit performs the actual insert\n"
				+ "		session.commit();\n" + "		session.close();\n"
				+ "} catch (Exception e) {\n"
				+ "			log.error(\"Could not run INSERT: \" + e.toString());\n"
				+ "		}" + "		return rows";

		try {
			return MethodSpec.methodBuilder("insert")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(TypeName.INT).build();
		} catch (final Exception e) {
			logger.error("ERROR creating insert method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	/**
	 * create MyBatis load method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createLoad(String className) {
		String mapperName = getMyBatisName(className);
		String methodText = "		final org.apache.ibatis.session.SqlSession session = sqlSessionFactory\n"
				+ "				.openSession(true);\n" + "\n" + "		"
				+ mapperName + "Example example = new " + mapperName
				+ "Example();\n" + "		" + mapperName
				+ "Example.Criteria cx = example\n"
				+ "				.createCriteria();\n"
				+ "		cx.andIdEqualTo(getId());\n" + "\n" + "		final "
				+ mapperName + " ret = session\n"
				+ "				.selectOne(\"" + mapperName
				+ "Mapper.selectByExample\", example);\n" + "\n"
				+ "if(ret!=null){ " + "\n" + "this."
				+ getBaseJavaName(className) + "Bean = ret.delegate;} else {\n"
				+ "\n" + " System.err.println(\"no results searching "
				+ className + " field for : \"+getId());" + "\n" + "}" + "\n"
				+ "		session.close();\n" + "		return this";

		try {
			ClassName cx = ClassName
					.get(IGNITE_MODEL_PACKAGE, getJavaServiceName(className));
			return MethodSpec.methodBuilder("load")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating load method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	/**
	 * create MyBatis update method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createUpdate(String className) {
		String mapperName = getMyBatisName(className);

		String methodText = "		final org.apache.ibatis.session.SqlSession session = sqlSessionFactory.openSession(true);\n"
				+ "		int rows = -1;\n" + "		try {\n"
				+ "			rows = session.update(\"" + mapperName

				+ "Mapper.updateByPrimaryKeySelective\", this);\n"
				+ "		// commit performs the actual update\n"
				+ "		session.commit();\n" + "		session.close();\n"
				+ "} catch (Exception e) {\n"
				+ "			log.error(\"Could not run UPDATE: \" + e.toString());\n"
				+ "}" + "\r\n" + "		return rows";

		try {
			return MethodSpec.methodBuilder("update")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(TypeName.INT).build();
		} catch (final Exception e) {
			logger.error("ERROR creating update method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	AnnotationSpec autoWired = null;

	private AnnotationSpec getAutoWiredSpec() {
		if (autoWired == null) {
			autoWired = AnnotationSpec
					.builder(org.springframework.beans.factory.annotation.Autowired.class)
					.build();
		}
		return autoWired;
	}

	/**
	 * create MyBatis delete method
	 * 
	 * @param className
	 * @return
	 */
	public MethodSpec createDelete(String className) {

		String mapperName = getMyBatisName(className);
		String methodText = "		final org.apache.ibatis.session.SqlSession session = sqlSessionFactory.openSession(true);\n"
				+ "		int rows = -1;\n" + "		try {\n"
				+ "			rows = session.delete(\"" + mapperName
				+ "Mapper.deleteByPrimaryKey\", getId());\n"
				+ "		// commit performs the actual delete\n"
				+ "		session.commit();\n" + "		session.close();\n"
				+ "} catch (Exception e) {\n"
				+ "			log.error(\"Could not run DELETE: \" + e.toString());\n"
				+ "		}" + "		return rows";
		try {
			return MethodSpec.methodBuilder("delete")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(TypeName.INT).build();
		} catch (final Exception e) {
			logger.error("ERROR creating delete method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	private MethodSpec createToJSON(String className) {
		String mapperName = getBaseJavaName(className);
		String methodText = "return " + mapperName + "Bean.toJSON()";
		try {
			ClassName cx = ClassName.get("java.lang", "String");
			return MethodSpec.methodBuilder("toJSON")
					.addJavadoc("Starter Ignite Generated Method: "
							+ DATE_FORMAT.format(new Date()))
					.addModifiers(Modifier.PUBLIC).addStatement(methodText)
					.returns(cx).build();
		} catch (final Exception e) {
			logger.error("ERROR creating toJSON method for: " + className + " "
					+ e.toString());
		}
		return null;
	}

	private String getMyBatisName(String className) {
		return MODEL_DAO_PACKAGE + "."
				+ MyBatisGen.getMyBatisModelClassName(className);

	}
	// ## END MYBATIS INSERT/UPDATE/DELETE/LIST

	@Override
	public synchronized void generate(String className, List<FieldSpec> fieldList, List<MethodSpec> getters, List<MethodSpec> setters) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		// TODO: cleanup
		final int dotpos = className.lastIndexOf(".");
		String memberName = className.substring(dotpos + 1);
		String memberType = memberName;
		memberName += "Bean";

		// add the spring mvc fields
		FieldSpec objectMapper = createObjectMapperField();
		FieldSpec httpServletRequest = createHttpServletRequestField();

		// add the builtins
		FieldSpec logr = createLoggerField(className);

		// add the MyBatis persistence methods
		FieldSpec ssf = createSQLSessionFactoryField();

		List<MethodSpec> methodList = new ArrayList<MethodSpec>();
		if (ssf != null) {
			fieldList.add(logr);
			fieldList.add(ssf);
			fieldList.add(objectMapper);
			fieldList.add(httpServletRequest);
			methodList.add(createGetHttpServletRequest(className));
			methodList.add(createGetObjectMapper(className));
			methodList.add(createGetAcceptHeader(className));
			methodList.add(createSetBean(className));
			methodList.add(createList(className));
			methodList.add(createLoad(className));
			methodList.add(createInsert(className));
			methodList.add(createUpdate(className));
			methodList.add(createDelete(className));

			methodList.add(createToJSON(className));

			logger.info("Created " + methodList.size()
					+ " MyBatis persistence methods");
		}
		// classes, this should point to the top of the package
		final URLClassLoader classLoader = new URLClassLoader(
				new URL[] { new File(JAVA_GEN_SRC_FOLDER).toURI().toURL(),
						new File(JAVA_GEN_RESOURCES_FOLDER).toURI().toURL() });

		String delegateInterfaceName = memberType;
		delegateInterfaceName = delegateInterfaceName
				.substring(delegateInterfaceName.lastIndexOf(".") + 1);
		delegateInterfaceName += SPRING_DELEGATE;
		ClassName cDD = ClassName.get(API_PACKAGE, delegateInterfaceName);
		try {
			delegateInterfaceName = API_PACKAGE + "." + delegateInterfaceName;
			Class<?> cx1 = classLoader.loadClass(delegateInterfaceName);
		} catch (Exception x) {
			cDD = null;
		}

		// instantiate the delegate class
		final Class<?> cxt = classLoader.loadClass(className);

		final FieldSpec member = FieldSpec.builder(cxt, memberName)
				.addModifiers(Modifier.PUBLIC)
				.initializer("new " + memberType + "()").build();

		className = className.substring(dotpos + 1);
		className += ADD_GEN_CLASS_NAME;

		AnnotationSpec delegateAnnotation = AnnotationSpec
				.builder(org.springframework.stereotype.Service.class)
				.addMember("value", "$S", getJavaVariableName(className))
				.build();

		// handle edge case bug where reserved name collides with
		// our naming convention based code here
		if (className.startsWith("Model")) {
			logger.warn("Fixing name of delegate for 'Model' edge case. May break mappings that start with 'Model' ");
			delegateAnnotation = AnnotationSpec
					.builder(org.springframework.stereotype.Service.class)
					.addMember("value", "$S", getJavaVariableName(className
							.replace("Model", "")))
					.build();
		}

		// bean constructor
		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC).build();
		methodList.add(constructor);

		// create the Java Class
		com.squareup.javapoet.TypeSpec.Builder builder = TypeSpec
				.classBuilder(className).addModifiers(Modifier.PUBLIC)
				.addField(member)
				// .superclass(null)
				.addJavadoc("Starter Ignite Generated Class: "
						+ DATE_FORMAT.format(new Date()))
				.addFields(fieldList).addMethods(setters).addMethods(getters)
				.addMethods(methodList);

		if (cDD != null) {
			builder.addSuperinterface(cDD);
			builder.addAnnotation(delegateAnnotation);
		}

		JavaFile.builder(IGNITE_MODEL_PACKAGE, builder.build()).build()
				.writeTo(JAVA_GEN_SRC);
		classLoader.close();
	}

	private FieldSpec createObjectMapperField() throws ClassNotFoundException {
		AnnotationSpec ano = this.getAutoWiredSpec();
		final Class<?> cx = Class
				.forName("com.fasterxml.jackson.databind.ObjectMapper");
		return FieldSpec.builder(cx, "objectMapper").addAnnotation(ano).build();
	}

	private FieldSpec createHttpServletRequestField() throws ClassNotFoundException {
		AnnotationSpec ano = this.getAutoWiredSpec();
		final Class<?> cx = Class
				.forName("javax.servlet.http.HttpServletRequest");
		return FieldSpec.builder(cx, "httpServletRequest").addAnnotation(ano)
				.build();
	}

	String getJavaVariableName(String n) {
		if (n.length() < 2)
			return n;
		n = n.replace(ADD_GEN_CLASS_NAME, "");
		String firstChar = n.substring(0, 1).toLowerCase();
		return firstChar + n.substring(1) + SPRING_DELEGATE;
	}

	String getJavaServiceName(String n) {
		if (n.contains(".")) {
			n = n.substring(n.lastIndexOf(".") + 1);
		}
		return n + ADD_GEN_CLASS_NAME;
	}

	String getMyBatisJavaName(String n) {
		return MyBatisGen.getMyBatisModelClassName(n);
	}

	String getBaseJavaName(String n) {
		return MyBatisGen.getBaseJavaName(n);
	}

	@Override
	public String toString() {
		return "Java Generator";
	}

	static void generateClassesFromModelFolder() throws Exception {
		logger.info("Iterate MyBatis Entities and create Wrapper Classes...");

		final String[] modelFiles = Gen.getModelFileNames();
		for (final String mf : modelFiles) {
			String cn = mf.substring(0, mf.indexOf("."));
			// cn = cn + ".class";
			cn = MODEL_PACKAGE + "." + cn;
			logger.warn("Creating Classes from ModelFile: " + cn);

			try {
				// this should point to the top of the package structure!
				final URLClassLoader classLoader = new URLClassLoader(
						new URL[] { new File(JAVA_GEN_SRC_FOLDER).toURI()
								.toURL() });

				final Class<?> loadedClass = classLoader.loadClass(cn);

				createClasses(loadedClass);
				classLoader.close();
			} catch (final ClassNotFoundException e) {
				logger.error("JavaGen.generateClassesFromModelFolder failed: "
						+ cn + ": " + e.toString());
			}
		}
	}

	/**
	 * compile all the files in the generated folder(s)
	 *
	 * thanks
	 * to:https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	static void compile(String packageDir) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// test
		final String sourcepath = JAVA_GEN_SRC_FOLDER + packageDir;

		logger.info("JavaGen Compiling: " + sourcepath);

		// prepare compiler
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final StandardJavaFileManager fileManager = compiler
				.getStandardFileManager(diagnostics, null, null);

		final List<String> optionList = new ArrayList<>();
		optionList.add("-classpath");
		optionList.add(System.getProperty("java.class.path")
				+ System.getProperty("path.separator") + JAVA_GEN_SRC_FOLDER
				+ System.getProperty("path.separator") + SOURCE_MAIN_JAVA);

		final File[] fx = Gen.getJavaFiles(sourcepath);

		final Iterable<? extends JavaFileObject> compilationUnit = fileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(fx));

		final JavaCompiler.CompilationTask compilerTask = compiler
				.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);

		// Compilation Requirements
		logger.info("Compiling: " + sourcepath);
		if (compilerTask.call()) {
			logger.info("Compilation Complete.");

			// load the newly compiled classes this should point to the
			// top of the package structure
			final URLClassLoader classLoader = new URLClassLoader(new URL[] {
					new File(JAVA_GEN_SRC_FOLDER).toURI().toURL(),
					new File(JAVA_GEN_RESOURCES_FOLDER).toURI().toURL() });

			for (final File f : fx) {
				// Load the class from the classloader by name....

				String loadClassName = f.getName().replace(".java", "");
				loadClassName = packageDir.replace('/', '.') + loadClassName;
				loadClassName = loadClassName.substring(1); // strip leading dot
				try {
					final Class<?> loadedClass = classLoader
							.loadClass(loadClassName);
					// Create a new instance...
					if (!loadedClass.isInterface()) {
						final Object obj = loadedClass.newInstance();
						logger.info("Successfully compiled class: "
								+ obj.toString());
					} else {
						logger.info("Successfully compiled interface: "
								+ loadClassName);
					}
				} catch (final ClassNotFoundException e) {
					// normal
				} catch (final Throwable t) {
					logger.warn("Could not verify: " + f.toString() + " "
							+ t.toString());
				}
			}
			classLoader.close();
		} else {
			for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
					.getDiagnostics()) {
				try {
					System.out.format("Error on line %d in %s%n", diagnostic
							.getLineNumber(), diagnostic.getSource().toUri());
				} catch (final Exception x) {
					logger.warn("Problem Generating Diagnostic for Object:"
							+ x);
				}
			}
		}
		fileManager.close();
	}
}
