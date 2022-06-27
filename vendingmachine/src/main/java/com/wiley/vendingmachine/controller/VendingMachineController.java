package com.wiley.vendingmachine.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.wiley.vendingmachine.view.*;

public class VendingMachineController {

	BigDecimal wallet = new BigDecimal("0");
	
	VendingMachineView view;
	
	public VendingMachineController(VendingMachineView v) {
		wallet = wallet.setScale(2, RoundingMode.FLOOR);
		view = v;
	}
	
	public void run()
	{
		boolean running = true;
		while(running)
		{
			switch(view.displayMenu())
			{
			case 1:
				break;
			case 2:
				break;
			case 3:
				running = false;
				break;
			default:
				view.prompt("Invalid choice");
			}
		}
	}

}
