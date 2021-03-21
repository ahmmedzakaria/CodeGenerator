/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltojava.model;

/**
 *
 * @author Zakaria
 */
public class FieldMaps {
    private String dataType;
    private String importStr;
    
    public String getDataType() {
        return dataType;
    }

    public FieldMaps(String dataType, String importStr) {
        this.dataType = dataType;
        this.importStr = importStr;
    }

    public FieldMaps(String dataType) {
        this.dataType = dataType;
    }

    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getImportStr() {
        return importStr;
    }

    public void setImportStr(String importStr) {
        this.importStr = importStr;
    }
    
}
