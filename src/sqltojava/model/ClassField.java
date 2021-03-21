/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltojava.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zakaria
 */
public class ClassField {
    private String sqlLine;
	private String fieldName;
    private String sqlFieldName;
    private String dataType;
    private String sqlDataType;
    private List<JavaAnnotation> annotations;
    private String rightComment;
    private String topComment;
    private String importStr;
    public static List<String> importList =new ArrayList<String>();



    public String getSqlLine() {
		return sqlLine;
	}

	public void setSqlLine(String sqlLine) {
		this.sqlLine = sqlLine;
	}

	public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

	public String getSqlDataType() {
		return sqlDataType;
	}

	public void setSqlDataType(String sqlDataType) {
		this.sqlDataType = sqlDataType;
	}


    public List<JavaAnnotation> getAnnotatios() {
        return annotations;
    }

    public void setAnnotations(List<JavaAnnotation> annotatios) {
        this.annotations = annotatios;
    }

    public String getRightComment() {
        return rightComment;
    }

    public void setRightComment(String rightComment) {
        this.rightComment = rightComment;
    }

    public String getTopComment() {
        return topComment;
    }

    public void setTopComment(String topComment) {
        this.topComment = topComment;
    }

    public String getSqlFieldName() {
        return sqlFieldName;
    }

    public void setSqlFieldName(String sqlFieldName) {
        this.sqlFieldName = sqlFieldName;
    }

    @Override
    public String toString() {
        return "ClassField{" + "fieldName=" + fieldName + ", sqlFieldName=" + sqlFieldName + ", dataType=" + dataType + ", annotatios=" + annotations + ", rightComment=" + rightComment + ", topComment=" + topComment + '}';
    }

	public String getImportStr() {
		return importStr;
	}

	public void setImportStr(String importStr) {
		this.importStr = importStr;
	}

    
    
    
}
