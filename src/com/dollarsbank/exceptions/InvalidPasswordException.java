package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = 1389219541356381377L;
	
	public InvalidPasswordException() {
		System.out.println(Colors.ANSI_RED.getColor() + "Password Incorrect!!");
	}

}
