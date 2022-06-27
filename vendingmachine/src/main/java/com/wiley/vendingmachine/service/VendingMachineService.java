package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.*;

public class VendingMachineService {

	VendingMachineDao dao;
	
	public VendingMachineService(VendingMachineDao d) {
		dao = d;
	}

}
