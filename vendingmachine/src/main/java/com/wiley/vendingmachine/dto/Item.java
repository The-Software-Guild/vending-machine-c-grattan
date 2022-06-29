package com.wiley.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

	private String name;
	private BigDecimal cost;
	private byte stock; //Unlikely that a vending machine will stock 127+ items
	
	public Item(String n, String c, byte s) {
		name = n;
		
		cost = new BigDecimal(c);
		cost = cost.setScale(2, RoundingMode.FLOOR);
		
		stock = s;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		else
		{
			if(o instanceof Item && ((Item) o).getName().equals(name))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public String getName()
	{
		return name;
	}

	public BigDecimal getCost()
	{
		return cost;
	}
	
	public byte getStock()
	{
		return stock;
	}
	
	public void decrementStock()
	{
		stock--;
	}
}
