package com.wiley.vendingmachine;

import java.util.Scanner;

import com.wiley.vendingmachine.view.*;
import com.wiley.vendingmachine.controller.*;

public class App 
{
    public static void main( String[] args )
    {
    	Scanner in = new Scanner(System.in);
        UserIO io = new UserIO(in);
        VendingMachineView view = new VendingMachineView(io);
        
        VendingMachineController controller = new VendingMachineController(view);
        controller.run();
    }
    
}
