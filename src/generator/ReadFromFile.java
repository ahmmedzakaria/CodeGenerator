package generator;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Zakakaria
 */
public class ReadFromFile {
    

	public static void whenReadWithBufferedReader()
	  throws IOException {
	    // String file ="src/test/resources/test_read.txt";
	      String file = "country.text";
	     BufferedReader reader = new BufferedReader(new FileReader(file));
	     
	     while(reader.readLine() != null) {
	    	 String currentLine = reader.readLine();		 
		    System.out.println(currentLine); 
	     }

	     reader.close();	     
	}
    public static void main(String[] args) throws IOException {
    	whenReadWithBufferedReader();
        String s1=null;
		/*
		 * try { RandomAccessFile file = new RandomAccessFile("country.text", "r");
		 * while((s1=file.readUTF())!=null){ System.out.println(s1); } } catch
		 * (FileNotFoundException ex) { ex.printStackTrace(); } catch(EOFException ex) {
		 * ex.printStackTrace(); } catch (IOException ex) { ex.printStackTrace(); }
		 */
    }
}
