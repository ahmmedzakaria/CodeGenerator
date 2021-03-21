package sqlToDart.model;

import java.util.ArrayList;
import java.util.List;


public class JavaAnnotation {
	private String annotation;
	private List<String> importStrList= new ArrayList();
	public static List<String> importList = new ArrayList();;
	
	
	public JavaAnnotation() {
		
	}
	public JavaAnnotation(String annotation, List<String> importStrList) {
		this.annotation = annotation;
		this.importStrList = importStrList;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public List<String> getImportStrList() {
		return importStrList;
	}
	public void setImportStrList(List<String> importStrList) {
		this.importStrList = importStrList;
	}

	
	
}
