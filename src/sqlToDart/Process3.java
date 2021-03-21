/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlToDart;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import sqlToDart.model.ClassField;
import sqlToDart.model.FieldMaps;
import sqlToDart.model.JavaAnnotation;
import sqlToDart.model.JavaCls;

/**
 *
 * @author Zakaria
 */
public class Process3 {
    
    List<Path> files ;
    List<ClassField> fieldList =new ArrayList();
    StringBuffer stringBuffer = null;
    StringBuffer classContent = new StringBuffer();
    StringBuffer imports = new StringBuffer();
    String line=null;
    String className;
    String tableName;
    StringBuffer topComment= new StringBuffer();
    String[] skipFilter = {
    		"WITH",
    		"OIDS",
    		"TABLESPACE",
    		"CONSTRAINT",
    		"(",
    		")",
    		"ALTER",
    		"OWNER",
    		"DROP"
    };
    int pos=0; 
    JavaCls javaCls = new JavaCls();
    
    public void createClassFields() throws IOException{
    	System.out.println("Generating Table: "+tableName+ ", Field Size: "+fieldList.size());
    	
        fieldList.forEach(field->{
            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
            	classContent
            	.append("\t").append(field.getDataType()).append(" ")
            	.append(field.getFieldName()).append(";");
            	
                classContent.append("\n");    
                
            }
        });
        
        generateToMap();
        generateFromMap();
        generateToJSon();
        generateFromJSon();
        

        String fileName = className;
        String ext = ".dart";
       // String path = "src" +File.separator+"products";
        String path = "javaclasses";
        String fileNameWithPath =path +File.separator+ fileName +ext;
        System.out.println(fileNameWithPath);
        String content = 
        		 imports
        		+" class "+fileName+"{\n\n "
		        +classContent.toString()+"\n\n}";
        writeToFile(fileNameWithPath,content);
        
        
        
    }   
    
    public void  generateToMap(){
		System.out.println("generateToMap: ");
		classContent.append("\n\n");
		classContent.append("Map<String, dynamic> toMap(){")
		.append("\n")
		.append("\tvar map = <String, dynamic>{")
		.append("\n");
		
		        fieldList.forEach(field->{
		            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
		            	classContent
		            	.append("\t'").append(field.getSqlFieldName()).append("' : ")
		            	.append(field.getFieldName()).append(",");
		            	
		                classContent.append("\n");    
		                
		            }
		        });
		 classContent.append("\t};\n")
		 .append("return map;\n")
		 .append("}");
    }
    
    public void  generateFromMap(){
 		System.out.println("generateFromMap: ");
 		classContent.append("\n\n")
  		.append("")
  		.append(className)
 		.append(".fromMap( Map<String, dynamic> map){\n");
 		        fieldList.forEach(field->{
 		            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
 		            	classContent
 		            	.append("\t")
 		            	.append(field.getFieldName())
 		            	.append(" = map['").append(field.getSqlFieldName()).append("']")
 		            	.append(";");
 		            	
 		                classContent.append("\n");    
 		                
 		            }
 		        });
 		        
 		classContent.append("}\n");
     }
    
    
    public void  generateToJSon(){
		System.out.println("generateToJSon: ");
		classContent.append("\n\n");
		classContent.append("String toJson(){")
		.append("\n")
		.append("\tString str ='{';")
		.append("\n");
		
		        fieldList.forEach(field->{
		            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
		            	classContent
		            	.append("\tstr += '").append("\"").append(field.getFieldName()).append("\" : \"$")
		            	.append(field.getFieldName()).append("\",';");
		            	
		                classContent.append("\n");    
		                
		            }
		        });
		 classContent.append("\tstr+='}';\n")
		 .append("return str;\n")
		 .append("}");
    }
    
    public void  generateFromJSon(){
  		System.out.println("generateFromJSon: ");
  		classContent.append("\n\n")
  		.append("")
  		.append(className)
  		.append(".fromJson(Map<String, dynamic> map){\n");
  		        fieldList.forEach(field->{
  		            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
  		            	classContent
  		            	.append("\t")
  		            	.append(field.getFieldName())
  		            	.append(" = map['").append(field.getFieldName()).append("']")
  		            	.append(";\n");
  		                
  		            }
  		        });

	            	
	    classContent.append("\n}\n");   
     }
    
    
    static void writeToFile(String fileName, String content) throws IOException{
        File file = new File(fileName);

        if (file.createNewFile()) {
        	FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.newLine();
            bw.close();
            System.out.println("File is created!\n");
        } else {
            System.out.println("File already exists.\n");
        }
        
    }
    
    
 
    public List<String> getImportStrList(String ...strings){
    	List<String> list = new ArrayList();
    	Arrays.asList(strings).forEach(e->list.add(e));
    	return list;
    }
    public void read(Path path) throws FileNotFoundException, IOException{
        BufferedReader bufferedReader = new BufferedReader
                    (new FileReader(path.toString()));

        
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer = new StringBuffer(line);
                ClassField field = new ClassField();
                
                String line = stringBuffer.toString().trim().replaceFirst(",", "");
                String s=new String(line);
                List<String> strList = Arrays.asList(s.split("--"));
                if(strList.size() == 2) {
                	field.setRightComment("\t// "+strList.get(1).trim());
                	s=strList.get(0);
                }
                List<String> words = Arrays.asList(s.split(" "));
                if(skipUnwantedLines(words)) continue;
                
                if(words.get(0).substring(0, 2).equals("--")) {
                	topComment.append("// "+line+"\n");
                   continue;
                }
                  if(words.get(0).toUpperCase().equals("CREATE")) {
                	  tableName = words.get(2).replace("public.", "");
                      className = getPascalCase(tableName);  
                   continue;
                  } 
                
                try {
            	field.setFieldName(getCamaleCase(words.get(0).trim()));
                field.setSqlFieldName(words.get(0).trim());   
                generateAnnotationsForField(field);
                
                FieldMaps fieldMaps =Mapper.getJavaDataType(getSqlDataType(words));
                field.setDataType(fieldMaps.getDataType());
                field.setImportStr(fieldMaps.getImportStr());
                generateTopAndRightComment(field,words);
                
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception: "+field);
                }
                fieldList.add(field);
            }
            bufferedReader.close();
            
            generateClassStr();
   
    }
    
    boolean skipUnwantedLines(List<String> words) {
    	int size= Arrays.asList(skipFilter).stream()
              .filter(e->e.equals(words.get(0).toUpperCase()))
              .collect(Collectors.toList()).size();
        if(size>0) {
        	return true;
        } 
        if(words.size() <2) {
        	return true;
        }
        return false;
    }
    
    void generateAnnotationsForField(ClassField field) {
        List<JavaAnnotation> annotationList =new ArrayList();               
        if(field.getFieldName().equals("id")) {
        	JavaAnnotation ann1 = new JavaAnnotation("@Id",getImportStrList("javax.persistence.Id"));
        	annotationList.add(ann1);
        	JavaAnnotation ann2 = new JavaAnnotation("@Type(type = \"org.hibernate.type.PostgresUUIDType\")",
        			getImportStrList("org.hibernate.annotations.Type"));
        	annotationList.add(ann2);
        	
        	JavaAnnotation ann3 = new JavaAnnotation("@GeneratedValue(strategy = GenerationType.AUTO)",
        			getImportStrList("javax.persistence.GeneratedValue","javax.persistence.GenerationType"));
        	annotationList.add(ann3);
        }
        
        String annotation ="@Column(name = \""+field.getSqlFieldName()+"\")";
        JavaAnnotation javaAnnotation = new JavaAnnotation();
        javaAnnotation.setAnnotation(annotation);
        javaAnnotation.setImportStrList(getImportStrList("javax.persistence.Column"));                
        annotationList.add(javaAnnotation);
        
        //field.setAnnotations(annotationList); 
    }
    
    void generateTopAndRightComment(ClassField field,List<String> words) {

        if(field.getRightComment()!=null) {
        	field.setRightComment(getDefault(pos, words)+field.getRightComment());
        }else {
        	field.setRightComment(getDefault(pos, words));
        }
        
        if(topComment.length()>2) {
        	field.setTopComment(topComment.toString());
        	topComment=new StringBuffer();
        }
    }
    
    void generateClassStr() {
    	javaCls = new JavaCls();
        javaCls.setClassName(className);
        List<JavaAnnotation> annotationList =new ArrayList();    
    	JavaAnnotation ann1 = new JavaAnnotation("@Entity",getImportStrList("javax.persistence.Entity"));
    	annotationList.add(ann1);
    	String anno= "@Table(name = \""+tableName+"\")";
    	JavaAnnotation ann2 = new JavaAnnotation(anno,
    			getImportStrList("javax.persistence.Table"));
    	annotationList.add(ann2);
    	javaCls.setAnnotations(annotationList);
    }
    
    
    public List<Path> loadAllFiles(String path) throws IOException{
       files = Files.list(Paths.get(path)).collect(Collectors.toList()); 
       System.out.println("Total "+files.size()+" files, files are:\n");
       files.forEach(System.out::println);  
       System.out.println("\n");
       return files;
    }
    public String getCamaleCase(String str){
        return StringUtils.decapitalize(getPascalCase(str));
    }
    
     public String getPascalCase(String str){
        StringBuffer sb = new StringBuffer();
        List<String> words = Arrays.asList(str.split("_"));
        if(words.size()>0){
            words.forEach(word->{
            sb.append(StringUtils.capitalize(word));
        });
        }
        return sb.toString();
    }
 
    public String getSqlDataType(List<String> lineWords){
        List<String> words = new ArrayList(4);
        if(lineWords.size() > 2){
            words.add(lineWords.get(1).trim());
            words.add(lineWords.get(2).trim());
            if(words.get(0).equals("character") && words.get(1).equals("varying")){
            	pos=2;
                return new StringBuffer().append(words.get(0))
                    .append(" ").append(words.get(1))
                    .toString();
            }
            if(words.get(0).equals("double") && words.get(1).equals("precision")){
            	pos=2;
                return new StringBuffer().append(words.get(0))
                    .append(" ").append(words.get(1))
                    .toString();
            }
            
        }
        if (lineWords.size() > 4){
        	pos=4;
            words.add(lineWords.get(3).trim());
            words.add(lineWords.get(4).trim());
            if(words.get(0).equals("timestamp") && words.get(1).equals("without")){
                return new StringBuffer().append(words.get(0))
                    .append(" ").append(words.get(1))
                    .append(" ").append(words.get(2))
                    .append(" ").append(words.get(3))
                    .toString();
            }
            
        }
        pos=1;
        return lineWords.get(1);
    }
    
    String getDefault(int pos, List<String> lineWords) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("\t//");
    	if(lineWords.size()>pos) {
    		for (int i=pos+1; i<lineWords.size(); i++) {
    			sb.append(lineWords.get(i)).append(" ");
    		}
    	}
    	if(sb.length()>3) return sb.toString();
    	return "";
    }
      
}
