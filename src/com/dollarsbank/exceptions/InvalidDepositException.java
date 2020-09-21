package com.dollarsbank.exceptions;

import com.dollarsbank.utility.ColorsUtility.Colors;

public class InvalidDepositException extends Exception {
	
	private static final long serialVersionUID = -6715304777269432373L;

	public InvalidDepositException(double amount) {
		System.out.println(Colors.ANSI_RED.getColor() + "Deposit must be greater than 0!! Amount entered = " + amount);
	}

}
