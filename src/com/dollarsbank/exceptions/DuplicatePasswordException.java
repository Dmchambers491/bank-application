package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class DuplicatePasswordException extends Exception {

	private static final long serialVersionUID = 2799733793016465275L;
	
	public DuplicatePasswordException() {
		System.out.println(Colors.ANSI_RED.getColor() + "Password already in use!");
	}

}
