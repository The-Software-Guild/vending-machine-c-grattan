package com.wiley.vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.wiley.vendingmachine.dto.*;

public class VendingMachineDao {

	private ArrayList<Item> items = new ArrayList<Item>();
	
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
		System.out.println("Loaded " + items.size() + " items from " + file);
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

	public ArrayList<Item> getItems()
	{
		return items;
	}
}
