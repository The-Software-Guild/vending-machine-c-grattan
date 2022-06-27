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
	private final String delimiter = ",";
	
	public VendingMachineDao(String f) {
		file = f;
		try
		{
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
		catch (Exception e)
		{
			
		}
	}
	
	public void commit()
	{
		try
		{
			PrintWriter fileOut = new PrintWriter(new FileWriter(file));
			fileOut.close();
		}
		catch (Exception e)
		{
			
		}
	}

	public ArrayList<Item> getItems()
	{
		return items;
	}
}
