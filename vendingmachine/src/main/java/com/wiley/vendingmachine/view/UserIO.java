package com.wiley.vendingmachine.view;

import java.util.Scanner;

public class UserIO {

	Scanner in;
	
	public UserIO(Scanner s) {
		in = s;
	}
	
	public void println(String msg)
	{
		System.out.println(msg);
	}
	
	public void print(String msg)
	{
		System.out.print(msg);
	}
	
	public String getString(String msg)
	{
		print(msg);
		return getString();
	}
	
	public String getString()
	{
		String input = "";
		do
		{
			input = in.nextLine();
		} while(input.isBlank());
		return input;
	}
	
	public int getInt()
	{
		return getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public int getInt(String msg)
	{
		return getInt(msg, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public int getInt(String msg, int min, int max)
	{
		print(msg);
		return getInt(min, max);
	}
	
	public int getInt(int min, int max)
	{
		int input = 0;
		
		do
		{
			try
			{
				input = in.nextInt();
			}
			catch (Exception e)
			{
				in.next();
			}
		} while(input < min || input > max);
		
		return input; 
	}
}
