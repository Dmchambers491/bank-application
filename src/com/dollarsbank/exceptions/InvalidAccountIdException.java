package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidAccountIdException extends Exception {

	private static final long serialVersionUID = -7038232941031647519L;
	
	public InvalidAccountIdException(int id) {
		System.out.println(Colors.ANSI_RED.getColor() + "Account with id=" + id + " cannot be found!");
	}

}
