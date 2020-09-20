package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidUserIdException extends Exception {

	private static final long serialVersionUID = 7248735969448245962L;
	
	public InvalidUserIdException() {
		System.out.println(Colors.ANSI_RED.getColor() + "User Id Incorrect!!");
	}

}
