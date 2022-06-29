package com.wiley.vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;

import com.wiley.vendingmachine.dto.*;

public class VendingMachineDao {

	private HashSet<Item> items = new HashSet<Item>();
	
	private final String file;
	private final String delimiter;
	
	public VendingMachineDao(String f, String d) throws Exception
	{
		file = f;
		delimiter = d;
		
		Scanner fileInput = new Scanner(
								new BufferedReader(
										new FileReader(file)));
		while(fileInput.hasNextLine())
		{
			String line = fileInput.nextLine();
			String[] item = line.split(delimiter);
			items.add(new Item(item[0], item[1], Byte.parseByte(item[2])));
		}
		fileInput.close();
	}
	
	public void commit() throws Exception
	{
		PrintWriter fileOut = new PrintWriter(new FileWriter(file));
		for(Item item : items)
		{
			fileOut.println(item.getName() + delimiter + item.getCost() + delimiter + item.getStock());
		}
		fileOut.close();
	}

	public HashSet<Item> getItems()
	{
		return items;
	}
	
	public BigDecimal purchase(String itemName, BigDecimal wallet) throws 	InsufficientFundsException,
																			NoItemInventoryException,
																			ItemNotFoundException
	{
		for(Item item : items)
		{
			if(item.getName().toLowerCase().equals(itemName.toLowerCase()))
			{
				if(item.getStock() == 0)
				{
					throw new NoItemInventoryException();
				}
				else if(item.getCost().compareTo(wallet) > 0)
				{
					throw new InsufficientFundsException();
				}
				else
				{
					item.decrementStock();
					return wallet.subtract(item.getCost());
				}
			}
		}
		throw new ItemNotFoundException();
	}
}
