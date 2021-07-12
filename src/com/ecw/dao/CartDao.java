package com.ecw.dao;

import java.util.List;

import com.ecw.beans.CartBean;

public interface CartDao{
		
	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @param prodQty
	 * @return
	 */
	public String addProductToCart(String userId, String prodId, int prodQty);
	
	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @param prodQty
	 * @return
	 */
	public String updateProductToCart(String userId, String prodId, int prodQty);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<CartBean> getAllCartItems(String userId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public int getCartCount(String userId);
	
	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @return
	 */
	public String removeProductFromCart(String userId,String prodId);
	
	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @return
	 */
	public boolean removeAProduct(String userId,String prodId);
	
}
	
