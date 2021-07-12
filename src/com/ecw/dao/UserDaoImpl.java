package com.ecw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecw.beans.UserBean;
import com.ecw.constants.IUserConstants;
import com.ecw.utility.DBUtil;
import com.ecw.utility.MailMessage;

public class UserDaoImpl implements UserDao {

	@Override
	public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode,
			String password, String userType) {

		UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password, userType);

		String status = registerUser(user);
		return status;
	}

	@Override
	public String registerUser(UserBean user) {

		String status = "User Registration Failed!";
		String validateUserInfo = validateUserInfo(user);
		if(validateUserInfo == null) {
			return validateUserInfo;
		}
		boolean isRegtd = isRegistered(user.getEmailId());
		if (isRegtd) {
			status = "Email Id Already Registered!";
			return status;
		}
		Connection conn = DBUtil.provideConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			System.out.println("Connected Successfully!");
		}

		try {

			ps = conn.prepareStatement("insert into " + IUserConstants.TABLE_USER + " values(?,?,?,?,?,?,?)");

			ps.setString(1, user.getUserName());
			ps.setLong(2, user.getMobileNo());
			ps.setString(3, user.getEmailId());
			ps.setString(4, user.getAddress());
			ps.setInt(5, user.getPinCode());
			ps.setString(6, user.getPassword());
			ps.setString(7, user.getUserType());

			int k = ps.executeUpdate();

			if (k > 0) {
				status = "User Registered Successfully!";
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(ps);

		return status;
	}

	@Override
	public boolean isRegistered(String emailId) {
		boolean flag = false;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from dbo.[user] where email=?");
			ps.setString(1, emailId);
			rs = ps.executeQuery();

			if (rs.next()) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return flag;
	}

	@Override
	public String isValidCredential(String emailId, String password, String userType) {
		String status = "Login Denied! Incorrect Username or Password";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from dbo.[user] where email=? and password=? and userType=?" );

			ps.setString(1, emailId);
			ps.setString(2, password);
			ps.setString(3, userType);


			rs = ps.executeQuery();

			if (rs.next())
				status = "valid";

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);
		return status;
	}

	@Override
	public UserBean getUserDetails(String emailId, String password) {

		UserBean user = null;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from dbo.[user] where email=? and password=?");
			ps.setString(1, emailId);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new UserBean();
				user.setUserID(rs.getInt("uid"));
				user.setUserName(rs.getString("name"));
				user.setMobileNo(rs.getLong("mobile"));
				user.setEmailId(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setPinCode(rs.getInt("pincode"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getString("userType"));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return user;
	}

	@Override
	public String getFName(String emailId) {
		String fname = "";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select name from dbo.[user] where email=?");
			ps.setString(1, emailId);

			rs = ps.executeQuery();

			if (rs.next()) {
				fname = rs.getString(1);

				fname = fname.split(" ")[0];

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return fname;
	}

	@Override
	public String getUserAddr(String userId) {
		String userAddr = "";

		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select address from dbo.[user] where email=?");

			ps.setString(1, userId);

			rs = ps.executeQuery();

			if (rs.next())
				userAddr = rs.getString(1);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return userAddr;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private String validateUserInfo(UserBean user) {

		StringBuilder status = new StringBuilder();

		if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
			status.append("User name is required");
		}
		if (user.getEmailId() == null || user.getEmailId().trim().isEmpty()) {
			status.append("User Email Id is required");
		}
		if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
			status.append("User Address is required");
		}
		if (user.getMobileNo() == null) {
			status.append("User Mobile no required");
		}
		if (user.getPinCode() == 0) {
			status.append("User Pin code required");
		}
		if (user.getUserType() == null || user.getUserType().trim().isEmpty()) {
			status.append("User Type required");
		}

		return status.toString().trim().isEmpty() ? null : status.toString();
	}
}
