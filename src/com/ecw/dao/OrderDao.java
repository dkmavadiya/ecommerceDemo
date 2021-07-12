package com.ecw.dao;

import java.util.List;

import com.ecw.beans.OrderBean;
import com.ecw.beans.TransactionBean;

public interface OrderDao {

	/**
	 * 
	 * @param userName
	 * @param paidAmount
	 * @return
	 */
	public String paymentSuccess(String userName, double paidAmount);

	/**
	 * 
	 * @param order
	 * @return
	 */
	public boolean addOrder(OrderBean order);

	/**
	 * 
	 * @param transaction
	 * @return
	 */
	public boolean addTransaction(TransactionBean transaction);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public int countSoldItem(String prodId);

	/**
	 * 
	 * @return
	 */
	public List<OrderBean> getAllOrders();
}
