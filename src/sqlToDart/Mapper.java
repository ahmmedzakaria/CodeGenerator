/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlToDart;

import java.util.HashMap;
import java.util.Map;

import sqlToDart.model.FieldMaps;

/**
 *
 * @author Zakaria
 */
public class Mapper {
    static Map<String, FieldMaps> sqlToJava = new HashMap();
    static {
        sqlToJava.put("integer", new FieldMaps("int"));
        sqlToJava.put("character varying", new FieldMaps("String"));
        sqlToJava.put("character varying(255)", new FieldMaps("String"));
        sqlToJava.put("text", new FieldMaps("String"));
        sqlToJava.put("character", new FieldMaps("String"));
        sqlToJava.put("double precision",  new FieldMaps("double"));
        sqlToJava.put("float",  new FieldMaps("double"));
        sqlToJava.put("timestamp without time zone",  new FieldMaps("dynamic"));
        sqlToJava.put("uuid",  new FieldMaps("String"));
        sqlToJava.put("UUID",  new FieldMaps("String"));
        sqlToJava.put("boolean", new FieldMaps("dynamic"));
        sqlToJava.put("date", new FieldMaps("dynamic"));
        sqlToJava.put("bigint", new FieldMaps("int"));
        sqlToJava.put("real", new FieldMaps("double"));
        sqlToJava.put("geography(Point,4326)", new FieldMaps("String"));
    }
    
    
    public static FieldMaps getJavaDataType(String sqlDataType){   
        //System.out.println("sqlDataType: "+sqlDataType);
        return sqlToJava.get(sqlDataType);
    }
}
