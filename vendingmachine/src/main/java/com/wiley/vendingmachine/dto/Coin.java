package com.wiley.vendingmachine.dto;

public enum Coin {
	penny(1),
	nickel(5),
	dime(10),
	quarter(25);
	
	public final int value;
	
	private Coin(int v)
	{
		value = v;
	}
};
