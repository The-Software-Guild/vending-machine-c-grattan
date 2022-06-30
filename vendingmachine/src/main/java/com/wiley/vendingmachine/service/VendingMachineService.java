package com.wiley.vendingmachine.service;

import java.io.IOException;
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
		audit("Started vending machine service");
	}

	private String audit(String msg)
	{
		try
		{
			dao.logEntry(msg);
		}
		catch (IOException e)
		{
			System.err.println("Warning: could not update log");
		}
		return msg;
	}
	
	public BigDecimal getWallet()
	{
		audit("Fetched wallet");
		return wallet;
	}
	
	public void addMoney(BigDecimal money)
	{
		audit("Added " + money + " to wallet");
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
			audit("Error committing changes to catalog");
			return false;
		}
		audit("Committed changes to catalog");
		return true;
	}
	
	public Set<Item> getItems()
	{
		audit("Fetched catalog");
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
		change.deleteCharAt(change.lastIndexOf("\n"));
		return change.toString();
	}
	
	public String tryPurchase(String item)
	{
		try
		{
			wallet = dao.purchase(item, wallet);
			return audit(item + " purchased!\nYour change is:\n" + convertToChange());
		}
		catch (InsufficientFundsException e)
		{
			return audit("Insufficient funds");
		}
		catch (NoItemInventoryException e)
		{
			return audit("There is no " + item + " left");
		}
		catch (ItemNotFoundException e)
		{
			return audit(item + " not found");
		}
	}
}
