package util.model;

import java.util.List;

public class FileModel {
	private StringBuffer fileContent;
	private List<LineModel> lines;
	
	public StringBuffer getFileContent() {
		return fileContent;
	}
	public void setFileContent(StringBuffer fileContent) {
		this.fileContent = fileContent;
	}
	public List<LineModel> getLines() {
		return lines;
	}
	public void setLines(List<LineModel> lines) {
		this.lines = lines;
	}
	
}
