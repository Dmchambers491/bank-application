package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidPhoneFormatException extends Exception {

	private static final long serialVersionUID = -4041549742233657976L;

	public InvalidPhoneFormatException() {
		System.out.println(Colors.ANSI_RED.getColor() + "Phone Format Incorrect! Please Try (###) ###-#### ");
	}
	
}
