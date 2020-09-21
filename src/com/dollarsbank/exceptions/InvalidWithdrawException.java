package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidWithdrawException extends Exception {

	private static final long serialVersionUID = -6116380130218452203L;

	public InvalidWithdrawException(double amount) {
		System.out.println(Colors.ANSI_RED.getColor() + "Withdraw amount must be greater than 0!! Amount entered = " + amount);
	}

}
