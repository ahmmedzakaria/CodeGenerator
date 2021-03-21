/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgToSQLite;

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
    static Map<String, String> pgToSQLite = new HashMap();
    static {
    	pgToSQLite.put("timestamp without time zone", "date");
    	pgToSQLite.put("uuid", "text");
    	pgToSQLite.put("boolean", "integer");
    	pgToSQLite.put("date", "integer");
    	pgToSQLite.put("bigint", "integer");
    	pgToSQLite.put("geography(Point,4326)", "text");
    }
    
    
    public static String getSQLiteType(String sqlDataType){   
        //System.out.println("sqlDataType: "+sqlDataType);
        return pgToSQLite.get(sqlDataType);
    }
}
