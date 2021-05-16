/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltojava;


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

import sqltojava.model.ClassField;
import sqltojava.model.FieldMaps;
import sqltojava.model.JavaAnnotation;
import sqltojava.model.JavaCls;
import util.StringUtill;

/**
 *
 * @author Zakaria
 */
public class Process {
    
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
    	
    	ClassField.importList.clear();
    	JavaAnnotation.importList.clear();
        fieldList.forEach(field->{
            if(field.getFieldName()!=null && !field.getFieldName().contentEquals("")) {
            	if(field.getTopComment()!=null && field.getTopComment().length()>0) {
            		classContent
                	.append("\t").append(field.getTopComment()).append("\n");
            	}
            	
            	field.getAnnotatios().forEach(e->{
            		classContent
                	.append("\t").append(e.getAnnotation()).append("\n");
            	});
            	
            	classContent
            	.append("\tprivate ").append(field.getDataType()).append(" ")
            	.append(field.getFieldName()).append(";");
            	
                if(field.getRightComment() !=null) {
                	classContent.append(field.getRightComment());
                }
            	
                classContent.append("\n\n");    
                
                int size=0;	
    				size = ClassField.importList.stream()
                    .filter(e->e.equals(field.getDataType()))
                    .collect(Collectors.toList()).size();		
                      
                if(size == 0 && field.getImportStr() != null) {
                	imports.append("import ").append(field.getImportStr()).append(";\n");
                	
                	ClassField.importList.add(field.getDataType());
                }
                
                if(field.getAnnotatios().size() > 0) {
                	field.getAnnotatios().forEach(annotation->{
                		int size2=0;
                		List<String> importStrList =annotation.getImportStrList();
                		
                		for(String importStr : importStrList) {
                			size2=JavaAnnotation.importList.stream()
           				 	.filter(a->a.equals(importStr))
           				 	.collect(Collectors.toList()).size();
                			if(size2 ==0) {
                    			imports.append("import ").append(importStr).append(";\n");
                    			JavaAnnotation.importList.add(importStr);
                    		}
                		}
        				
                		
                	});                	
                }
                
            }
        });
        
        javaCls.getAnnotations().forEach(anno->{
        	List<String> list = anno.getImportStrList();
        	list.forEach(imp->{
        		imports.append("import ").append(imp).append(";\n");
        	});        	
        });
        imports.append("\n");
        
        javaCls.getAnnotations().forEach(anno->{
        	imports.append(anno.getAnnotation()).append("\n");
        });
        
        //System.out.println(classContent);
        //System.out.println(imports);
        String fileName = className;
        String ext = ".java";
       // String path = "src" +File.separator+"products";
        String path = "javaclasses";
        String fileNameWithPath =path +File.separator+ fileName +ext;
        System.out.println(fileNameWithPath);
        String content = 
        		 imports
        		+"public class "+fileName+"{\n\n "
		        +classContent.toString()
		        +"\n"+getGeneratedGatterSetters().toString()
		        +"\n\n}";
        writeToFile(fileNameWithPath,content);
        
        
        
    }   
    
    StringBuilder getGeneratedGatterSetters() {
    	StringBuilder str = new StringBuilder();
    	fieldList.forEach(field->{
    		str.append(getGeneratedGatter(field))
    		.append("\n\n")
    		.append(getGeneratedSetter(field))
    		.append("\n\n");
    	});
    	
    	return str;
    	
    }
    
    
    StringBuilder getGeneratedGatter(ClassField model) {
    	
    	StringBuilder str = new StringBuilder();
    	str.append("\tpublic ")
    	.append(model.getDataType()).append(" ");
    	if(model.getDataType()!=null && model.getDataType().equals("boolean")) {
        	str.append(model.getFieldName());
    		
    	}else {
        	str.append("get")
        	.append(getPascalCase(model.getFieldName()));
    	}  	
    	
    	str.append("()")
    	.append("{\n")
    	.append("\t\treturn ")
    	.append("this.")
    	.append(model.getFieldName())
    	.append(";")
    	.append("\n\t}");
    	return str;
    }
    
    StringBuilder getGeneratedSetter(ClassField model) {
    	StringBuilder str = new StringBuilder();
    	str.append("\tpublic void ");
    	if(model.getDataType()!=null && model.getDataType().equals("boolean")) {
    		if(model.getFieldName()!=null && model.getFieldName().substring(0, 2).equals("is")) {
    			str.append(" set")
            	.append(getPascalCase(model.getFieldName().substring(2)));
    		}
    		
    		
    	}else {
    		str.append(" set")
        	.append(getPascalCase(model.getFieldName()));
    	} 
    	
    	str.append("(")
    	.append(model.getDataType())
    	.append(" ")
    	.append(model.getFieldName())
    	.append(")")
    	.append("{\n")
    	.append("\t\tthis.")
    	.append(model.getFieldName())
    	.append(" = ")
    	.append(model.getFieldName())
    	.append(";")
    	.append("\n\t}");
    	return str;
    	
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
                field.setSqlLine(s);
            	field.setFieldName(getCamaleCase(words.get(0).trim()));
                field.setSqlFieldName(words.get(0).trim());   
                generateAnnotationsForField(field);
                field.setSqlDataType(getSqlDataType(words));
                FieldMaps fieldMaps =Mapper.getJavaDataType(field.getSqlDataType());
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
        
        field.setAnnotations(annotationList); 
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
        return StringUtill.decapitalize(getPascalCase(str));
    }
    
     public String getPascalCase(String str){
        StringBuffer sb = new StringBuffer();
        List<String> words = Arrays.asList(str.split("_"));
        if(words.size()>0){
            words.forEach(word->{
            sb.append(StringUtill.capitalize(word));
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
