package serviceAndDao;
import java.lang.reflect.Field;
import java.util.Arrays;

import sqltojava.model.ClassField;



public class ServiceGenerate2 {
	StringBuffer methodBodyStr = new StringBuffer();
	public static void main(String[] args) {
		ServiceGenerate2 serviceGenerate	=new ServiceGenerate2();
		serviceGenerate.generate();
		System.out.println(serviceGenerate.methodBodyStr.toString());
	}
	
	void generate() {
		Class cls = ClassField.class;
		Field[] fields = cls.getDeclaredFields();
		Arrays.asList(fields).forEach(field->{
			generateStatement(field);
		});
	}
	
	void getDataType() {
		
	}

	private void generateStatement(Field field) {
		methodBodyStr.append("String")
						.append(" ")
						.append(field.getName())
						.append(" = ")
						.append("requestMap.get(\""+getRequestString(field)+"\")[0]")
						.append(";\n");
		
	}


	String getRequestString(Field field){
		StringBuffer sb =new StringBuffer();
		String[] words = field.getName().split("(?=\\p{Upper})");	
		Arrays.asList(words).forEach(word->sb.append(word.toLowerCase()).append("_"));
		
	return sb.substring(0,sb.length()-1).toString();
	}
}

