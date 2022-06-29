package com.wiley.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import com.wiley.vendingmachine.dao.*;
import com.wiley.vendingmachine.dto.Coin;
import com.wiley.vendingmachine.dto.Item;

public class VendingMachineService {

	BigDecimal wallet = new BigDecimal("0");
	
	VendingMachineDao dao;
	
	public VendingMachineService(VendingMachineDao d)
	{
		wallet = wallet.setScale(2, RoundingMode.FLOOR);
		dao = d;
	}

	public BigDecimal getWallet()
	{
		return wallet;
	}
	
	public void addMoney(BigDecimal money)
	{
		wallet = wallet.add(money);
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
	
	private String convertToChange()
	{
		StringBuilder change = new StringBuilder();
		for(Coin c : Coin.values())
		{
			BigDecimal[] result = wallet.divideAndRemainder(c.value);
			wallet = result[1];
			change.append(c + ": " + result[0] + "\n");
		}
		return change.toString();
	}
	
	public String tryPurchase(String item)
	{
		try
		{
			wallet = dao.purchase(item, wallet);
			return item + " purchased!\nYour change is:\n" + convertToChange();
		}
		catch (InsufficientFundsException e)
		{
			return "Insufficient funds";
		}
		catch (NoItemInventoryException e)
		{
			return "There is no " + item + " left";
		}
		catch (ItemNotFoundException e)
		{
			return item + " not found";
		}
	}
}
