package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class DuplicateChoiceException extends Exception {

	private static final long serialVersionUID = 2811928348025911736L;

	public DuplicateChoiceException(int choice) {
		System.out.println(Colors.ANSI_RED.getColor() + "Account with id=" + choice + " has already been chosen!");
	}
}
