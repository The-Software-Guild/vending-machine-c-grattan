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
	
	public void setStock(byte s)
	{
		stock = s;
	}
}
