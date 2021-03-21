package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtill {
	public static List<Path> getAllFileOfDirectory(String directoryPath) throws IOException{
		List<Path> files = Files.list(Paths.get(directoryPath)).collect(Collectors.toList());
       return files;
    }
	
	public static List<Path> getAllDirectoryOfDirectory(String directoryPath) throws IOException{
		List<Path> files = Files.list(Paths.get(directoryPath))
				.filter(path->path.toFile().isDirectory())
				.collect(Collectors.toList());
       return files;
    }
	


	public static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; 
	    }
	    return name.substring(lastIndexOf);
	}

	public static boolean createDirectory(String directoryPath) {
		return new File(directoryPath).mkdir();
	}
	
	public static boolean createPackage(String packageName) {
		return new File("src/"+packageName).mkdir();
	}
	
	public static int findAndReplace(String filePath, String searchText, String replaceString) throws IOException {
		Path path = Paths.get(filePath);
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(searchText, replaceString);
		Files.write(path, content.getBytes(charset));
		return 0;
	}
	
}
