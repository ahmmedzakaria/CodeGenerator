package util.model;

import java.util.List;

public class LineModel {
	private int lineNo;
	private StringBuffer lineContent;
	private List<String> words;
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public StringBuffer getLineContent() {
		return lineContent;
	}
	public void setLineContent(StringBuffer lineContent) {
		this.lineContent = lineContent;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	
}
