package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.*;

public class VendingMachineService {

	VendingMachineDao dao;
	
	public VendingMachineService(VendingMachineDao d)
	{
		dao = d;
	}

	public boolean tryCommit()
	{
		try
		{
			dao.commit();
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
}
