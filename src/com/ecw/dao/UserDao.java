package com.ecw.dao;

import com.ecw.beans.UserBean;

public interface UserDao {
	
	/**
	 * 
	 * @param userName
	 * @param mobileNo
	 * @param emailId
	 * @param address
	 * @param pinCode
	 * @param password
	 * @param userType
	 * @return
	 */
	public String registerUser(String userName,Long mobileNo,String emailId,String address,int pinCode,String password,String userType);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public String registerUser(UserBean user);
	
	/**
	 * 
	 * @param emailId
	 * @return
	 */
	public boolean isRegistered(String emailId);
		
	/**
	 * 
	 * @param emailId
	 * @param password
	 * @return
	 */
	public String isValidCredential(String emailId, String password,String userType);
	
	/**
	 * 
	 * @param emailId
	 * @param password
	 * @return
	 */
	public UserBean getUserDetails(String emailId,String password);
	
	/**
	 * 
	 * @param emailId
	 * @return
	 */
	public String getFName(String emailId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserAddr(String userId);
	
	
}
