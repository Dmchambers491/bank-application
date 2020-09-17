package com.dollarsbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dollarsbank.connection.ConnectionManager;
import com.dollarsbank.model.Account;

public class AccountDAOImp implements AccountDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public Account getAccountById(String id) {
		
		Account account = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from accounts where id = ?")) {
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int acct_id = rs.getInt(1);
				double balance = rs.getDouble(2);
				double previous_amount = rs.getDouble(3);
				String customer_id = rs.getString(4);
				
				account = new Account(acct_id, balance, previous_amount, customer_id);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

	@Override
	public boolean addAccount(Account account) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into accounts values(null,?,?,?)");
			
			pstmt.setDouble(1, account.getBalance());
			pstmt.setDouble(2, account.getPrevious_amount());
			pstmt.setString(3, account.getCustomer_id());
			
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
	public boolean updateAccount(Account account) {
		
		try(PreparedStatement pstmt = conn.prepareStatement("update accounts set balance = ?, previous_amount = ? where id = ?");) {
			
			pstmt.setDouble(1, account.getBalance());
			pstmt.setDouble(2, account.getPrevious_amount());
			pstmt.setInt(3, account.getId());
			
			int update = pstmt.executeUpdate();
			
			if(update > 0) {
				return true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Account> getAccountsByCustomerId(String id) {
		
		List<Account> accounts = new ArrayList<Account>();
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from accounts where customer_id = ?")) {
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int acct_id = rs.getInt(1);
				double balance = rs.getDouble(2);
				double previous_amount = rs.getDouble(3);
				String customer_id = rs.getString(4);
				
				Account account = new Account(acct_id, balance, previous_amount, customer_id);
				accounts.add(account);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}

}
