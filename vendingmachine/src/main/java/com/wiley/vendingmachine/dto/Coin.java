package com.wiley.vendingmachine.dto;

import java.math.BigDecimal;

public enum Coin {
	Quarters("0.25"),
	Dimes("0.1"),
	Nickels("0.05"),
	Pennies("0.01");
	
	public final BigDecimal value;
	
	private Coin(String v)
	{
		value = new BigDecimal(v);
	}
};