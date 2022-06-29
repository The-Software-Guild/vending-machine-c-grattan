package com.wiley.vendingmachine.service;

import java.math.BigDecimal;
import java.util.Set;

import com.wiley.vendingmachine.dao.*;
import com.wiley.vendingmachine.dto.Item;

public class VendingMachineService {

	VendingMachineDao dao;
	
	public VendingMachineService(VendingMachineDao d)
	{
		dao = d;
	}

	public boolean tryCommit()
	{
		try
		{
			dao.commit();
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	public Set<Item> getItems()
	{
		return dao.getItems();
	}
	
	public BigDecimal tryPurchase(String item, BigDecimal wallet)
	{
		try
		{
			return dao.purchase(item, wallet);
		}
		catch (Exception e)
		{
			return wallet;
		}
	}
}
