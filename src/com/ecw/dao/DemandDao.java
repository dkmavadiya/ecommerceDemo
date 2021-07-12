package com.ecw.dao;

import java.util.List;

import com.ecw.beans.DemandBean;

public interface DemandDao {

	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @param demandQty
	 * @return
	 */
	public boolean addProduct(String userId, String prodId, int demandQty);

	/**
	 * 
	 * @param userDemandBean
	 * @return
	 */
	public boolean addProduct(DemandBean userDemandBean);

	/**
	 * 
	 * @param userId
	 * @param prodId
	 * @return
	 */
	public boolean removeProduct(String userId, String prodId);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public List<DemandBean> haveDemanded(String prodId);

}
