package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.VendingMachineDao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;


public class VendingMachineServiceTest {
	
	final String testAudit = "TestAudit.txt";
	final String testCatalog = "TestCatalog.txt";
	
	private VendingMachineDao setUpTestDao()
	{
		try
		{
			return new VendingMachineDao(testCatalog, testAudit, ",");
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	private VendingMachineDao dao = setUpTestDao();
	private VendingMachineService service = new VendingMachineService(dao);
	
	private void resetWallet()
	{
		service.getWallet().multiply(new BigDecimal("0"));
	}
	
	private void clearLog()
	{
		try
		{
			PrintWriter pw = new PrintWriter(new FileWriter(testAudit, false));
			pw.close();
		}
		catch (Exception e)
		{
			Assertions.fail();
		}
	}
	
	private final String catalog =	"Cola,1.99,95\n"
									+ "Orange,0.99,98\n"
									+ "Water,0.00,124";
	
	private void restock()
	{
		try
		{
			PrintWriter out = new PrintWriter(new FileWriter("TestCatalog.txt", false));
			out.print(catalog);
			out.close();
			
			dao = setUpTestDao();
			service = new VendingMachineService(dao);
		}
		catch (Exception e)
		{
			Assertions.fail("Could not open catalog");
		}
	}
	
	private String getCatalog()
	{
		try
		{
			Scanner in = new Scanner(new BufferedReader(new FileReader(testCatalog)));
			StringBuilder cat = new StringBuilder();
			while(in.hasNextLine())
			{
				cat.append(in.nextLine());
				cat.append('\n');
			}
			in.close();
			cat.deleteCharAt(cat.lastIndexOf("\n"));
			return cat.toString();
		}
		catch (Exception e)
		{
			Assertions.fail("Could not get catalog: " + e.getMessage());
			return null;
		}
	}
	
	private String getLogEntries()
	{
		StringBuilder log = new StringBuilder();
		try
		{
			Scanner logFile = new Scanner(
									new BufferedReader(
											new FileReader(testAudit)));
			while(logFile.hasNextLine())
			{
				final String line = logFile.nextLine();
				log.append(line.substring(line.lastIndexOf(": ") + 2)); //Trim the time for consistent testing purposes
				log.append('\n');
			}
			log.deleteCharAt(log.lastIndexOf("\n"));
			logFile.close();
		}
		catch (Exception e)
		{
			Assertions.fail("Could not get log entries");
		}
		return log.toString();
	}
	
	@org.junit.jupiter.api.Test
	public void addMoneyTest()
	{
		resetWallet();
		clearLog();
		final String amount = "10.00";
		service.addMoney(new BigDecimal(amount));
		
		final String log = getLogEntries();
		final String expected = "Added " + amount + " to wallet";
		
		Assertions.assertEquals(expected, log);
		Assertions.assertEquals(new BigDecimal(amount), service.getWallet());
	}
	
	@org.junit.jupiter.api.Test
	public void getItemsTest()
	{
		Set<String> names = service.getItems().stream()
												.map((item) -> item.getName())
													.collect(Collectors.toSet());
		
		Set<String> expected = new HashSet<String>();
		expected.add("Cola");
		expected.add("Water");
		expected.add("Orange");
		
		Assertions.assertEquals(expected, names);
	}
	
	@org.junit.jupiter.api.Test
	public void getWalletTest()
	{
		resetWallet();
		clearLog();
		
		final BigDecimal amount = new BigDecimal("10.00");
		service.addMoney(amount);
		
		final BigDecimal actual = service.getWallet();
		
		final String log = getLogEntries();
		
		Assertions.assertEquals("Added 10.00 to wallet\nFetched wallet", log);
		Assertions.assertEquals(amount, actual);
	}
	
	@org.junit.jupiter.api.Test
	public void tryCommitTest()
	{
		restock();
		service.tryCommit();
		final String expected = "Cola,1.99,95\n"
								+ "Orange,0.99,98\n"
								+ "Water,0.00,124";
		//Different to example catalogue because the marshaling process swaps orders around
		
		Assertions.assertEquals(expected, catalog);
	}
	
	@org.junit.jupiter.api.Test
	public void tryPurchaseTest()
	{
		resetWallet();
		restock();
		clearLog();
		
		service.addMoney(new BigDecimal("2.4"));
		
		String expected = "Cola purchased! Your change is:\n"
								+ "Quarters: 1\n"
								+ "Dimes: 1.0\n"
								+ "Nickels: 1\n"
								+ "Pennies: 1";
		//Should demonstrate proper change calculation
		
		Assertions.assertEquals(expected, service.tryPurchase("Cola"));
		
		expected =	"Added 2.4 to wallet\n"
					+ "Cola purchased! Your change is:\n"
					+ "1\n"
					+ "1.0\n"
					+ "1\n"
					+ "1";
		
		Assertions.assertEquals(expected, getLogEntries());
		
		service.tryCommit();
		
		expected =	"Water,0.00,124\n"
					+ "Cola,1.99,94\n"
					+ "Orange,0.99,98";
		
		Assertions.assertEquals(expected, getCatalog());
	}
}
