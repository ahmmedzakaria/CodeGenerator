package test;

import java.text.NumberFormat;

public class FormatNumber {
	
	public static void main(String[] args) {
		String names = "98,798,902";
		
		names = names.replaceAll(",", "");
		System.out.println(names);
	}
	
}
