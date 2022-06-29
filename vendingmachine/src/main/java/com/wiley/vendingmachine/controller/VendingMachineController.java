package com.wiley.vendingmachine.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.wiley.vendingmachine.view.*;
import com.wiley.vendingmachine.service.*;

public class VendingMachineController {

	BigDecimal wallet = new BigDecimal("0");
	
	VendingMachineView view;
	VendingMachineService service;
	
	public VendingMachineController(VendingMachineView v, VendingMachineService s) {
		wallet = wallet.setScale(2, RoundingMode.FLOOR);
		view = v;
		service = s;
		view.loadStatus(s.getItems());
	}
	
	private void addMoney()
	{
		wallet = wallet.add(view.getMoney());
	}
	
	private void displayItems()
	{
		view.displayItems(service.getItems(), false);
	}
	
	public void run()
	{
		boolean running = true;
		while(running)
		{
			switch(view.displayMenu(wallet))
			{
			case 1:
				addMoney();
				break;
			case 2:
				displayItems();
				wallet = service.tryPurchase(view.getName(), wallet);
				break;
			case 3:
				if(running = !service.tryCommit())
				{
					view.prompt("Could not commit catalog, are you sure you want to exit?");
				}
				break;
			default:
				view.prompt("Invalid choice");
			}
		}
	}

}
