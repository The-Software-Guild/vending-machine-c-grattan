package com.wiley.vendingmachine.view;

import java.math.BigDecimal;

import com.wiley.vendingmachine.view.UserIO;

public class VendingMachineView {

	private final String CURRENCY_REGEX = "^[\\$£¤€₠₱]?(((\\d{1,3})(,?\\d{1,3})*)|(\\d+))(\\.\\d{2})?$";
	
	UserIO io;
	
	public VendingMachineView(UserIO i) {
		io = i;
	}

	public int displayMenu()
	{
		return io.getInt("Select your vending machine interaction:\n1. Insert money\n2. Purchase item\n3. Exit", 1, 3);
	}
	
	public void prompt(String msg)
	{
		io.println(msg);
	}
	
	public BigDecimal getMoney()
	{
		io.print("Enter an amount of money: ");
		return new BigDecimal(io.getRegexString(CURRENCY_REGEX));
	}
}
