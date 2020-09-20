package com.dollarsbank.exceptions;

import com.dollarsbank.model.Account;
import com.dollarsbank.utility.ColorsUtility.Colors;

public class AmountOutOfReachException extends Exception {

	private static final long serialVersionUID = 7982588029269078125L;

	public AmountOutOfReachException(Account account, double amount) {
		System.out.println(Colors.ANSI_RED.getColor() + "Account " + account.getId() + " cannot make transfer! Transfer amount ($" + amount + ") is too large!");
	}
}
