package com.wiley.vendingmachine.view;

import java.math.BigDecimal;
import java.util.Set;

import com.wiley.vendingmachine.dto.Item;

public class VendingMachineView {

	private final String CURRENCY_REGEX = "^[\\$£¤€₠₱]?(((\\d{1,3})(,?\\d{1,3})*)|(\\d+))(\\.\\d{2})?$";
	
	UserIO io;
	
	public VendingMachineView(UserIO i) {
		io = i;
	}

	public int displayMenu(BigDecimal wallet)
	{
		return io.getInt("\nSelect your vending machine interaction:\n1. Insert money (you have " + wallet + ")\n2. Purchase item\n3. Exit\n", 1, 3);
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
	
	public String getName()
	{
		return io.getString("Which item would you like to purchase?\n");
	}
	
	public void displayItems(Set<Item> items, boolean outOfStock)
	{
		items.stream().forEach((item) -> {
			if(outOfStock || item.getStock() > 0)
			{
				io.println(item.getName() + ": " + item.getCost());
			}
		});
	}
	
	public void loadStatus(Set<Item> items)
	{
		io.println("Loaded " + items.size() + " items:");
		displayItems(items, true);
	}
}
