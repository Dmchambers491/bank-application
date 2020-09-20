package com.dollarsbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dollarsbank.connection.ConnectionManager;
import com.dollarsbank.model.Customer;

public class CustomerDAOImp implements CustomerDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		
		try(Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from customers"); ){
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone_number = rs.getString(4);
				String password = rs.getString(5);
				
				// add to list
				Customer customer = new Customer(id, name, address, phone_number, password);
				customers.add(customer);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return customers;
	}

	@Override
	public Customer getCustomerById(String id) {
		
		Customer customer = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from customers where id = ?")) {
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String cust_id = rs.getString(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone_number = rs.getString(4);
				String password = rs.getString(5);
				
				customer = new Customer(cust_id, name, address, phone_number, password);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return customer;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into customers values(?,?,?,?,?)");
			
			pstmt.setString(1, customer.getId());
			pstmt.setString(2, customer.getName());
			pstmt.setString(3, customer.getAddress());
			pstmt.setString(4, customer.getPhone_number());
			pstmt.setString(5, customer.getPassword());
			
			int insert = pstmt.executeUpdate();
			
			if(insert > 0) {
				return true;
			}
			
			pstmt.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Customer getCustomerByPassword(String password) {
		
		Customer customer = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from customers where password = ?")) {
			
			pstmt.setString(1, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone_number = rs.getString(4);
				String cust_password = rs.getString(5);
				
				customer = new Customer(id, name, address, phone_number, cust_password);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}

	
}
