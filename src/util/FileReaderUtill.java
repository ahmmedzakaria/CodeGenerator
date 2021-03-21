package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import util.model.FileModel;
import util.model.LineModel;

public class FileReaderUtill {
	
    public FileModel getLines(Path path) throws FileNotFoundException, IOException{
    	FileModel fileModel = new FileModel();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()));
        String lineString = null;
        StringBuffer lineBuffer = null;
        StringBuffer content = new StringBuffer();
        int lineNo = 1;
        List<LineModel> lines = new ArrayList();
        while ((lineString = bufferedReader.readLine()) != null) {
        	LineModel lineModel = new LineModel();
            lineBuffer = new StringBuffer(lineString);
        	content.append(lineBuffer).append("\n");
            lineModel.setLineContent(lineBuffer);
            lineModel.setLineNo(lineNo);
            lines.add(lineModel);
            lineNo++;
        }
        fileModel.setLines(lines);
        fileModel.setFileContent(content);
        bufferedReader.close();
        return fileModel;
    }
    
    
}
