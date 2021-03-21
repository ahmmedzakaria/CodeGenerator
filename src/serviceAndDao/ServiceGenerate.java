package serviceAndDao;



import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

public class ServiceGenerate {

	Class cls = TestModel.class;
	StringBuffer methodBodyStr = new StringBuffer();
	StringBuffer methodBodyFieldStr = new StringBuffer();
	StringBuffer setModel = new StringBuffer();
	
	public static void main(String[] args) {
		ServiceGenerate serviceGenerate	=new ServiceGenerate();
		serviceGenerate.generate();
		System.out.println(serviceGenerate.methodBodyStr.toString());
		
	}
	
	void generate() {
		String [] words = cls.getName().split("\\.");
		String className=words[words.length-1];
		setModel.append(className).append(" entity = new ")
		.append(className).append("();\n\n");
		
		List<Field> fieldList = Arrays.asList(cls.getDeclaredFields());
		methodBodyStr.append(generateMothodBodyFields(fieldList))
		.append("\n");
		generateMothodBodyFields(fieldList);
		fieldList.forEach(field->{
			methodBodyStr.append(getGeneratedStatement(field));
		});
		
		methodBodyStr.append(setModel);
	}
	
	String getDataType(Field field) {
		String[] words= field.getType().getName().split("\\.");
		if(words.length >0) return words[words.length-1];
		return "";
	}

	private StringBuffer getGeneratedStatement(Field field) {
		StringBuffer sb = new StringBuffer();
		StringBuffer modelStetment = new StringBuffer();
		String comment="";
		String dataType=getDataType(field);
		if(dataType.equals("String")) {
			sb.append("String").append(" ").append(field.getName());
			comment="";
		}else if(dataType.equals("UUID")) {
			sb.append("UUID").append(" ").append(field.getName());
			comment="";
		}else {
			String fieldName = field.getName()+"Str";
			sb.append("String").append(" ").append(fieldName);
			comment=dataType;
			
		}
		
		
		if(!dataType.equals("UUID")) {
			sb.append(" = ").append("requestMap.get(\""+getRequestString(field)+"\")[0];");
		}else {
			sb.append(" = ").append("UUID.randomUUID();");
		}
						
		
		if(comment.length()>1) {
			sb.append("\t").append("//"+comment);
		}
		sb.append("\n");
		if(!dataType.equals("String") && !dataType.equals("UUID")) {
			StringBuffer sb2= new StringBuffer();
			sb2.append(getDataType(field)).append(" ")
				.append(field.getName()).append(" = ").append(getDefaultValue(dataType)).append(";\n");
			
			parseToDataType(dataType, field, sb);
			
			sb = sb2.append(getStrWithTryCatch(sb, field));
			}
		modelStetment.append("entity").append(".")
		//.append("set"+StringUtils.capitalize(field.getName()))
		.append(getSetterMethodName(field))
		.append("(")
		.append(field.getName())
		.append(");\n");
		setModel.append(modelStetment);
		return sb;
	}
	
	String getSetterMethodName(Field field) {
		StringBuffer sb = new StringBuffer();
		if(getDataType(field).equals("boolean") && field.getName().substring(0, 2).equals("is")) {
			String[] words= splitCamelCase(field.getName());
			
			for(int i=1;i<words.length; i++) {
				sb.append(words[i]);
			}
		}else {
			sb.append(field.getName());
		}
		return "set"+StringUtils.capitalize(sb.toString());
	}
	
	void parseToDataType(String dataType, Field field, StringBuffer sb) {
		if(dataType.equals("double")) {
			sb.append("\t")
			.append(field.getName()).append(" = ")
			.append("Double.parseDouble(")
			.append(field.getName()+"Str")
			.append(".")
			.append("replace(\",\", \"\")")
			.append(");\n");
		}else if(dataType.equals("long")) {
			sb.append("\t")
			.append(field.getName()).append(" = ")
			.append("Long.parseLong(")
			.append(field.getName()+"Str")
			.append(".")
			.append("replace(\",\", \"\")")
			.append(");\n");
		}else if(dataType.equals("int")) {
			sb.append("\t")
			.append(field.getName()).append(" = ")
			.append("Integer.parseInt(")
			.append(field.getName()+"Str")
			.append(".")
			.append("replace(\",\", \"\")")
			.append(");\n");
		}else if(dataType.equals("boolean")) {
			sb.append("\t")
			.append(field.getName()).append(" = ")
			.append("isTrue.test(")
			.append(field.getName()+"Str")
			.append(");\n");
		}
	}
	
	StringBuffer generateMothodBodyFields(List<Field> fieldList) {
		StringBuffer sb = new StringBuffer();
		boolean flag = fieldList.stream().filter(field -> getDataType(field).equals("boolean")).findFirst().isPresent();
		if(flag) {
			sb.append("Predicate<String> isTrue = str->str.equals(\"true\");\n");
		}
		return sb;
	}
	String getGeneratedFieldName(Field field) {
		if(!getDataType(field).equals("String")) {
			return field.getName()+"Str";
		}
		return field.getName();
	}

	String[] splitCamelCase(String s) {
		String[] words = s.split("(?=\\p{Upper})");
		return words;
	}
	String getRequestString(Field field){
		StringBuffer sb =new StringBuffer();
		String[] words = field.getName().split("(?=\\p{Upper})");	
		Arrays.asList(words).forEach(word->sb.append(word.toLowerCase()).append("_"));
		
	return sb.substring(0,sb.length()-1).toString();
	}
	
	StringBuffer getStrWithTryCatch(StringBuffer str, Field field){
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb.append("try {\n\t");
		
		sb2.append("\n} catch (Exception e) {\n\t")
		.append("System.err.println(").append("\"").append(field.getName()).append(": \"").append(" + ").append("e.getMessage());\n")
		.append("}\n");
		
		return sb.append(str).append(sb2).append("\n");	

	}
	
	
	String getDefaultValue(String dataType) {
		if(dataType.equals("boolean"))return "false";
		else if(dataType.equals("double")) return "0.0";
		else if(dataType.equals("int")) return "0";
		return null;
	}
	
	
	
}
