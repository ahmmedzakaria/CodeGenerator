/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltojava;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import sqltojava.model.FieldMaps;

/**
 *
 * @author Zakaria
 */
public class Mapper {
    static Map<String, FieldMaps> sqlToJava = new HashMap();
    static {
        sqlToJava.put("integer", new FieldMaps("int"));
        sqlToJava.put("character varying", new FieldMaps("String"));
        sqlToJava.put("text", new FieldMaps("String"));
        sqlToJava.put("double precision",  new FieldMaps("double"));
        sqlToJava.put("float",  new FieldMaps("double"));
        sqlToJava.put("timestamp without time zone",  new FieldMaps("Timestamp","java.sql.Timestamp"));
        sqlToJava.put("uuid",  new FieldMaps("UUID", "java.util.UUID"));
        sqlToJava.put("boolean", new FieldMaps("boolean"));
        sqlToJava.put("date", new FieldMaps("Date","java.sql.Date"));
        sqlToJava.put("bigint", new FieldMaps("long"));
    }
    
    
    public static FieldMaps getJavaDataType(String sqlDataType){   
        //System.out.println("sqlDataType: "+sqlDataType);
        return sqlToJava.get(sqlDataType);
    }
}
