package com.ecw.dao;

import java.io.InputStream;
import java.util.List;

import com.ecw.beans.ProductBean;

public interface ProductDao {

	/**
	 * 
	 * @param prodName
	 * @param prodType
	 * @param prodInfo
	 * @param prodPrice
	 * @param prodQuantity
	 * @param prodImage
	 * @return
	 */
	public String addProduct(String prodName, String prodType, String prodInfo, double prodPrice, int prodQuantity,
			InputStream prodImage);

	/**
	 * 
	 * @param product
	 * @return
	 */
	public String addProduct(ProductBean product);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public String removeProduct(String prodId);

	/**
	 * 
	 * @param prevProduct
	 * @param updatedProduct
	 * @return
	 */
	public String updateProduct(ProductBean prevProduct, ProductBean updatedProduct);

	/**
	 * 
	 * @param prodId
	 * @param updatedPrice
	 * @return
	 */
	public String updateProductPrice(String prodId, double updatedPrice);

	/**
	 * 
	 * @return
	 */
	public List<ProductBean> getAllProducts();

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public byte[] getImage(String prodId);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public ProductBean getProductDetails(String prodId);

	/**
	 * 
	 * @param prevProductId
	 * @param updatedProduct
	 * @return
	 */
	public String updateProductWithoutImage(String prevProductId, ProductBean updatedProduct);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public double getProductPrice(String prodId);

	/**
	 * 
	 * @param prodId
	 * @param n
	 * @return
	 */
	public boolean sellNProduct(String prodId, int n);

	/**
	 * 
	 * @param prodId
	 * @return
	 */
	public int getProductQuantity(String prodId);
}
