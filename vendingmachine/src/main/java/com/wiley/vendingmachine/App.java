package com.wiley.vendingmachine;

import java.util.Scanner;

import com.wiley.vendingmachine.view.*;
import com.wiley.vendingmachine.controller.*;
import com.wiley.vendingmachine.dao.VendingMachineDao;
import com.wiley.vendingmachine.service.VendingMachineService;

public class App 
{
    public static void main( String[] args )
    {
    	Scanner in = new Scanner(System.in);
        UserIO io = new UserIO(in);
        VendingMachineView view = new VendingMachineView(io);
        
        try
        {
        	VendingMachineDao dao = new VendingMachineDao("Catalog.txt", ",");
        	VendingMachineService service = new VendingMachineService(dao);
            
            VendingMachineController controller = new VendingMachineController(view, service);
            controller.run();
        }
        catch (Exception e)
        {
        	System.err.println("Error: Could not open catalog");
        }
    }
    
}
