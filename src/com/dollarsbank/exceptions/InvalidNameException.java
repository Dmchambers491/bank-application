package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidNameException extends Exception {

	private static final long serialVersionUID = -6890326501054175820L;
	
	public InvalidNameException(String name) {
		System.out.println(Colors.ANSI_RED.getColor() + "Name must contain only characters! Name entered: " + name);
	}

}
