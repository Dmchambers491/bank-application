package com.dollarsbank.utility;

public class ColorsUtility {
	
	public enum Colors {
		ANSI_RESET("\u001B[0m"),
		ANSI_BLUE("\u001B[34m"),
		ANSI_RED("\u001B[31m"),
		ANSI_GREEN("\u001B[32m"),
		ANSI_YELLOW("\u001B[33m"),
		ANSI_PURPLE("\u001B[35m"),
		ANSI_CYAN("\u001B[36m"),
		ANSI_WHITE("\u001B[37m");
		
		String color;
		
		Colors(String c){
			color = c;
		}
		
		public String getColor() {
			return color;
		}
	}

}
