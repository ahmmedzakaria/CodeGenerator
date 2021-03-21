/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltojava;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Zakaria
 */
public class JavaGenerator {
    
    
    public static void main(String[] args) throws IOException {
    	String path="resource";
        
        final List<Path> files= new Process().loadAllFiles(path);
        files.forEach(p->{
        	try {
        		Process process =new Process();
				process.read(p);
				process.createClassFields();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        //process.read(files.get(0));
        //process.createClassFields();
    }
}
