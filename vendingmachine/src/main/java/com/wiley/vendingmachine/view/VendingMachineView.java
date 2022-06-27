package com.wiley.vendingmachine.view;

import com.wiley.vendingmachine.view.UserIO;

public class VendingMachineView {

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
}
