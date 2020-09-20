package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidPasswordCreationException extends Exception {

	private static final long serialVersionUID = -5848176761294607941L;

	public InvalidPasswordCreationException() {
		System.out.println(Colors.ANSI_RED.getColor() + "Password Format Incorrect!");
	}
}
