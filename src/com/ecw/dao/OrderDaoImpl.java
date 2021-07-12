package com.ecw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecw.beans.CartBean;
import com.ecw.beans.OrderBean;
import com.ecw.beans.TransactionBean;
import com.ecw.utility.DBUtil;
import com.ecw.utility.MailMessage;

public class OrderDaoImpl implements OrderDao {

	@Override
	public String paymentSuccess(String userName, double paidAmount) {
		String status = "Order Placement Failed!";

		List<CartBean> cartItems = new ArrayList<>();
		cartItems = new CartDaoImpl().getAllCartItems(userName);

		if (cartItems.isEmpty())
			return status;

		TransactionBean transaction = new TransactionBean(userName, paidAmount);

		PreparedStatement ps1 = null;
		int p = 0, q = 0, k = 0;
		boolean flag = false;

		String transactionId = transaction.getTransactionId();

		if (transaction != null) {

			for (CartBean item : cartItems) {

				double amount = new ProductDaoImpl().getProductPrice(item.getProdId()) * item.getQuantity();

				OrderBean order = new OrderBean(transactionId, item.getProdId(), item.getQuantity(), amount);

				flag = addOrder(order);
				if (!flag)
					break;
				else {
					flag = new CartDaoImpl().removeAProduct(item.getUserId(), item.getProdId());
				}

				if (!flag)
					break;
				else
					flag = new ProductDaoImpl().sellNProduct(item.getProdId(), item.getQuantity());

				if (!flag)
					break;
			}

			if (flag) {
				flag = new OrderDaoImpl().addTransaction(transaction);
				if (flag) {

//					MailMessage.transactionSuccess(userName, new UserDaoImpl().getFName(userName),
//							transaction.getTransactionId(), transaction.getTransAmount());

					status = "Order Placed Successfully!";
				}
			}

		}

		return status;
	}

	@Override
	public boolean addOrder(OrderBean order) {
		boolean flag = false;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into dbo.[orders] values(?,?,?,?,?)");

			ps.setString(1, order.getTransactionId());
			ps.setString(2, order.getProductId());
			ps.setInt(3, order.getQuantity());
			ps.setDouble(4, order.getAmount());
			ps.setInt(5, 0);

			int k = ps.executeUpdate();

			if (k > 0)
				flag = true;

		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean addTransaction(TransactionBean transaction) {
		boolean flag = false;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into dbo.[transactions] values(?,?,?,?)");

			ps.setString(1, transaction.getTransactionId());
			ps.setString(2, transaction.getUserName());
			ps.setTimestamp(3, transaction.getTransDateTime());
			ps.setDouble(4, transaction.getTransAmount());

			int k = ps.executeUpdate();

			if (k > 0)
				flag = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public int countSoldItem(String prodId) {
		int count = 0;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select sum(quantity) from dbo.[orders] where prodid=?");

			ps.setString(1, prodId);

			rs = ps.executeQuery();

			if (rs.next())
				count = rs.getInt(1);

		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return count;
	}

	@Override
	public List<OrderBean> getAllOrders() {
		List<OrderBean> orderList = new ArrayList<>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from dbo.[orders]");

			rs = ps.executeQuery();

			while (rs.next()) {

				OrderBean order = new OrderBean(rs.getString("transid"), rs.getString("prodid"), rs.getInt("quantity"),
						rs.getDouble("amount"), rs.getInt("shipped"));

				orderList.add(order);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return orderList;
	}

}
