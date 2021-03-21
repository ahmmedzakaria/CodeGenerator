package sqltojava.model;

import java.util.ArrayList;
import java.util.List;


public class JavaCls {
	private String className;
	private List<JavaAnnotation> annotations;
	
	
	public JavaCls() {
		
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<JavaAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<JavaAnnotation> annotations) {
		this.annotations = annotations;
	}



	
	
}
