package com.wiley.vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;

import com.wiley.vendingmachine.dto.*;

public class VendingMachineDao {

	private HashSet<Item> items = new HashSet<Item>();
	
	private final String FILE;
	private final String AUDIT;
	private final String DELIMITER;
	
	private PrintWriter audit;
	
	public VendingMachineDao(String f, String a, String d) throws Exception
	{
		FILE = f;
		DELIMITER = d;
		AUDIT = a;
		
		Scanner fileInput = new Scanner(
								new BufferedReader(
										new FileReader(FILE)));
		while(fileInput.hasNextLine())
		{
			String line = fileInput.nextLine();
			String[] item = line.split(DELIMITER);
			items.add(new Item(item[0], item[1], Byte.parseByte(item[2])));
		}
		fileInput.close();
	}
	
	public void logEntry(String s) throws IOException
	{
		audit = new PrintWriter(new FileWriter(AUDIT, true));
		audit.println(LocalDateTime.now() + ": " + s);
		audit.close();
	}
	
	public void commit() throws Exception
	{
		PrintWriter fileOut = new PrintWriter(new FileWriter(FILE));
		for(Item item : items)
		{
			fileOut.println(item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getStock());
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
