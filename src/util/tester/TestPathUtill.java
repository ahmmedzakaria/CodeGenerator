package util.tester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import util.FileUtill;

public class TestPathUtill {
	static String path = "/home/zakaria/Desktop";
	static String newDir = "/home/zakaria/Desktop/NewDir";
	static String newDirWindows = "C:\\Directory1";
			
	public static void main(String[] args) throws IOException {
		//List<Path> files = PathUtil.getAllFileOfDirectory(path);
		//files.forEach(System.out::println);
		
		List<Path> directories = FileUtill.getAllDirectoryOfDirectory(path);
		directories.forEach(System.out::println);
		
		//new File(newDir).mkdir();
		//boolean packageCreateionStatus = PathUtil.createPackage("mypkg2");
		//System.out.println(packageCreateionStatus);
	}
}

