package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class DuplicateIdException extends Exception {

	private static final long serialVersionUID = -4756667977594224323L;
	
	public DuplicateIdException() {
		System.out.println(Colors.ANSI_RED.getColor() + "User Id already in use!");
	}

}
