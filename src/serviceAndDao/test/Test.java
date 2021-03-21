package serviceAndDao.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		Class cls = Common.class;
		System.out.println(Arrays.asList(cls.getTypeParameters()));
		new Test().generate(cls);
		
	}
	
	void generate(Class cls) {
		
		List<Method> methodList = Arrays.asList(cls.getDeclaredMethods());
		methodList.forEach(method->{
			StringBuffer methodStr = new StringBuffer();
			int modifiersInt = method.getModifiers();
			String modifiers = Modifier.toString(modifiersInt); 
			String methodName =method.getName();
			String returnType = getMethodReturnType(method);
			String parameters = getMethodPerameters(method);
			String methodBody = getMethodBody(method);

			methodStr.append("public").append(" ")
			.append(returnType).append(" ")
			.append(methodName)
			.append("(")
			.append(parameters)
			.append(")")
			.append("{\n")
			.append(methodBody)
			.append("\n}\n");
			System.out.println(methodStr);
			
		});
		
	}
	

	private String getMethodBody(Method method) {
		
		return "";
	}

	String getMethodReturnType(Method method) {
		String returnType = method.getReturnType().getSimpleName();
		System.out.println(method.getGenericReturnType());
		return returnType;
	}
	
	String getMethodPerameters(Method method) {
		Parameter[] parameters = method.getParameters();
		StringBuffer parametersStr = new StringBuffer();
		for(Parameter param : parameters) {
			String type = param.getType().getSimpleName();
			String name = param.getName();
			//System.out.println(param.isNamePresent());
			parametersStr.append(type).append(" ")
			.append(name).append(", ");
		}
		if(parametersStr.length()>2) {
			parametersStr = new StringBuffer(parametersStr.
					substring(0,parametersStr.length()-2));
		}
		return parametersStr.toString();
	}
	
	
	class JavaCls {
		List<JavaField> fieldList = new ArrayList();
		List<JavaMethod> methodList = new ArrayList();
		List<JavaAnnotation> annotationList = new ArrayList();
		List<String> genericTypeList = new ArrayList();
		String packageName;
		
		
		void generateClass(Class modelClass, Field[] classFieldArr, String generationType) {
			List<Field> classFieldList = Arrays.asList(classFieldArr);
			generateService( modelClass, classFieldList);
			
		}
		
		void generateService(Class modelClass, List<Field> classFieldList) {
			List<JavaMethod> javaMethodList=new ArrayList();
			List<Method> methodList = Arrays.asList(modelClass.getDeclaredMethods());
			methodList.forEach(method->{
				JavaMethod javaMethod =new JavaMethod();
				javaMethod.method = method;
				javaMethod.name = method.getName();
				
				StringBuffer methodStr = new StringBuffer();
				int modifiersInt = method.getModifiers();
				String modifiers = Modifier.toString(modifiersInt); 
				String methodName =method.getName();
				String returnType = getMethodReturnType(method);
				String parameters = getMethodPerameters(method);
				String methodBody = getMethodBody(method);

				methodStr.append("public").append(" ")
				.append(returnType).append(" ")
				.append(methodName)
				.append("(")
				.append(parameters)
				.append(")")
				.append("{\n")
				.append(methodBody)
				.append("\n}\n");
				System.out.println(methodStr);
				javaMethodList.add(javaMethod);
				
			});
			
		}
		
	}
	
	class JavaMethod{
		Method method;
	    String name;
	    String returnType;
	    List<JavaField> parameterList = new ArrayList();
	    List<JavaException> exceptionList = new ArrayList();
	    List<JavaAnnotation> annotationList = new ArrayList();
	    List<String> modifiers = new ArrayList();
		StringBuffer methodBody = new StringBuffer();
	    

		String getMethodReturnType() {
			String returnType = method.getReturnType().getSimpleName();
			//System.out.println(method.getGenericReturnType());
			return returnType;
		}
	}

	class JavaField{
		String name;
		String dataType;
	    String importStr;
	}
	
	class JavaAnnotation{
		String annotation;
		List<String> importStrList = new ArrayList();
	}
	
	class JavaException{
		
	}
	
	class Util{
			
	}
}
