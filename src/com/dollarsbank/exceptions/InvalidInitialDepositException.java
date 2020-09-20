package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidInitialDepositException extends Exception {

	private static final long serialVersionUID = 436405691059392395L;
	
	public InvalidInitialDepositException() {
		System.out.println(Colors.ANSI_RED.getColor() + "Initial Deposit must be 0 or higher!!");
	}

}
