package com.wiley.vendingmachine.controller;

import com.wiley.vendingmachine.view.*;
import com.wiley.vendingmachine.service.*;

public class VendingMachineController {
	
	VendingMachineView view;
	VendingMachineService service;
	
	public VendingMachineController(VendingMachineView v, VendingMachineService s) {
		
		view = v;
		service = s;
		view.loadStatus(s.getItems());
	}
	
	public void addMoney()
	{
		service.addMoney(view.getMoney());
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
			switch(view.displayMenu(service.getWallet()))
			{
			case 1:
				addMoney();
				break;
			case 2:
				displayItems();
				view.prompt(service.tryPurchase(view.getName()));
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
