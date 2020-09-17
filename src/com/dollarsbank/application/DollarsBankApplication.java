package com.dollarsbank.application;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dollarsbank.dao.AccountDAO;
import com.dollarsbank.dao.AccountDAOImp;
import com.dollarsbank.dao.CustomerDAO;
import com.dollarsbank.dao.CustomerDAOImp;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.utility.ColorsUtility.Colors;

public class DollarsBankApplication {
	
	public static final CustomerDAO customerdao = new CustomerDAOImp();
	public static final AccountDAO accountdao = new AccountDAOImp();
	
	public static void welcome() {
		int choice;
		
		System.out.println(Colors.ANSI_BLUE.getColor() + "+---------------------------+\n| DOLLARSBANK Welcomes You! |\n+---------------------------+" + Colors.ANSI_RESET.getColor());
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println("1: Create New Account\n2: Login\n3: Exit");
			System.out.println(Colors.ANSI_GREEN.getColor() + "\nEnter Choice (1, 2, or 3) :" + Colors.ANSI_RESET.getColor());
			try{
				choice = input.nextInt();
				input.nextLine();
				switch (choice) {
				case 1:
					createAccount();
					valid = false;
					break;
				case 2:
					login();
					valid = false;
					break;
				case 3:
					exit();
					valid = false;
					break;
				default:
					System.out.println(Colors.ANSI_RED.getColor() + "Invalid choice!!\n" + Colors.ANSI_RESET.getColor());
					break;
				}
			}catch(IllegalArgumentException e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Please enter a number!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void exit() {
		System.out.println(Colors.ANSI_PURPLE.getColor() + "Thank you for using The Dollars Bank App!! Goodbye!" + Colors.ANSI_RESET.getColor());
	}
	
	public static void signOut() {
		System.out.println(Colors.ANSI_GREEN.getColor() + "Successfully Logged Out!!" + Colors.ANSI_RESET.getColor());
		welcome();
	}
	
	public static void createAccount() {
		String name;
		String address;
		String phone_number;
		String id;
		String password;
		int initial_deposit;
		
		Pattern phonePattern = Pattern.compile("^.?\\d{3}.?(\\s{1}|.)?\\d{3}.?\\d{4}$");
		Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[@#$%!^&])(?=.*[A-Z]).{8}");
		
		System.out.println(Colors.ANSI_BLUE.getColor() + "\n+-------------------------------+\n| Enter Details For New Account |\n+-------------------------------+" + Colors.ANSI_RESET.getColor());
		
		try(Scanner input = new Scanner(System.in)) {
			System.out.println("Customer Name:");
			System.out.print(Colors.ANSI_CYAN.getColor());
			name = input.nextLine();
			System.out.println(Colors.ANSI_RESET.getColor() + "Customer Address:");
			System.out.print(Colors.ANSI_CYAN.getColor());
			address = input.nextLine();
			System.out.println(Colors.ANSI_RESET.getColor() + "Customer Contact Number:");
			System.out.print(Colors.ANSI_CYAN.getColor());
			phone_number = input.nextLine();
			Matcher phoneMatcher = phonePattern.matcher(phone_number);
			if(phoneMatcher.matches() == false) {
				throw new Exception();
			}
			System.out.println(Colors.ANSI_RESET.getColor() + "User Id:");
			System.out.print(Colors.ANSI_CYAN.getColor());
			id = input.nextLine();
			System.out.println(Colors.ANSI_RESET.getColor() + "Password: 8 Characters With Lower, Upper & Special");
			System.out.print(Colors.ANSI_CYAN.getColor());
			password = input.nextLine();
			Matcher passwordMatcher = passwordPattern.matcher(password);
			if(!passwordMatcher.matches()) {
				throw new Exception();
			}
			System.out.println(Colors.ANSI_RESET.getColor() + "Initial Deposit Amount:");
			System.out.print(Colors.ANSI_CYAN.getColor());
			initial_deposit = input.nextInt();
			System.out.print(Colors.ANSI_RESET.getColor());
			
			Customer customer = new Customer(id, name, address, phone_number, password);
			boolean added = customerdao.addCustomer(customer);
			if(added) {
				Account account = new Account();
				account.deposit(initial_deposit);
				account.setCustomer_id(id);
				boolean created = accountdao.addAccount(account);
				if(created) {
					System.out.println(Colors.ANSI_GREEN.getColor() + "Account Created!!" + Colors.ANSI_RESET.getColor());
					login();
				}else {
					throw new Exception();
				}
			}else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			System.out.println(Colors.ANSI_RED.getColor() + "Invalid Input!!" + Colors.ANSI_RESET.getColor());
		}
		
	}
	
	public static void login() {
		String id;
		String password;
		boolean valid = true;
		int counter = 0;
		
		Scanner input = new Scanner(System.in);
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+---------------------+\n| Enter Login Details |\n+---------------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_RESET.getColor() + "User Id:");
			try {
				System.out.print(Colors.ANSI_CYAN.getColor());
				id = input.nextLine();
				Customer found = customerdao.getCustomerById(id);
				if(found != null) {
					System.out.println(Colors.ANSI_RESET.getColor() + "Password: 8 Characters With Lower, Upper & Special");
					System.out.print(Colors.ANSI_CYAN.getColor());
					password = input.nextLine();
					if(password.equals(found.getPassword())) {
						System.out.println(Colors.ANSI_GREEN.getColor() + "Login Successful!!" + Colors.ANSI_RESET.getColor());
						valid = false;
						welcomeCustomer(found);
					}else {
						counter += 1;
						throw new Exception();
					}
				}else {
					counter += 1;
					throw new Exception();
				}
			} catch (Exception e) {
				input.nextLine();
				if(counter == 3) {
					System.out.println(Colors.ANSI_RED.getColor() + "Too many unsuccessful logins!!");
					valid = false;
					exit();
				}else {
					System.out.println(Colors.ANSI_RED.getColor() + "Invalid Input!!");
				}
			}
		}
	}
	
	public static void welcomeCustomer(Customer customer) {
		int choice;
		
		System.out.println(Colors.ANSI_BLUE.getColor() + "\n+---------------------+\n| WELCOME Customer!!! |\n+---------------------+" + Colors.ANSI_RESET.getColor());
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println("1: Deposit Amount\n2: Withdraw Amount\n3: Funds Transfer\n4: View Recent Transaction\n5: Display Customer Information\n6: Sign out");
			System.out.println(Colors.ANSI_GREEN.getColor() + "\nEnter Choice (1,2,3,4,5, or 6) :" + Colors.ANSI_RESET.getColor());
			try{
				choice = input.nextInt();
				input.nextLine();
				switch (choice) {
				case 1:
					makeDeposit(customer);
					valid = false;
					break;
				case 2:
					makeWithdraw(customer);
					valid = false;
					break;
				case 3:
					valid = false;
					break;
				case 4:
					viewTransactions(customer);
					valid = false;
					break;
				case 5:
					displayCustomer(customer);
					valid = false;
					break;
				case 6:
					signOut();
					valid = false;
					break;
				default:
					System.out.println(Colors.ANSI_RED.getColor() + "Invalid choice!!\n" + Colors.ANSI_RESET.getColor());
					break;
				}
				
			}catch(IllegalArgumentException e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Please enter a number!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void makeDeposit(Customer customer) {
		double amount;
		int choice;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+----------------+\n| Deposit Portal |\n+----------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to deposit to?" + Colors.ANSI_RESET.getColor());
			for(Account a : accounts) {
				System.out.println("Account " + a.getId());
			}
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
			choice = input.nextInt();
			for(Account a : accounts) {
				if(a.getId() == choice) {
					acct = a;
					try{
						System.out.println(Colors.ANSI_YELLOW.getColor() + "How much do you wish to deposit?" + Colors.ANSI_RESET.getColor());
						amount = input.nextDouble();
						input.nextLine();
						acct.deposit(amount);
						boolean updated = accountdao.updateAccount(acct);
						
						if(updated) {
							System.out.println(Colors.ANSI_GREEN.getColor() + "Deposit made succesfully!!!" + Colors.ANSI_RESET.getColor());
							System.out.println(Colors.ANSI_GREEN.getColor() + "Your new balance is $" + acct.getBalance() + Colors.ANSI_RESET.getColor());
							continueApp(customer);
							valid = false;
						}else {
							throw new Exception();
						}
						
					}catch(Exception e) {
						input.nextLine();
						System.out.println(Colors.ANSI_RED.getColor() + "Please enter a valid amount!" + Colors.ANSI_RESET.getColor());
					}
				}
			}
			if(acct == null) {
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void makeWithdraw(Customer customer) {
		double amount;
		int choice;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+-------------------+\n| Withdrawal Portal |\n+-------------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to withdraw from?" + Colors.ANSI_RESET.getColor());
			for(Account a : accounts) {
				System.out.println("Account " + a.getId() + " has a balance of " + Colors.ANSI_GREEN.getColor() + "$" + a.getBalance() + Colors.ANSI_RESET.getColor());
			}
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
			choice = input.nextInt();
			for(Account a : accounts) {
				if(a.getId() == choice) {
					acct = a;
					try{
						System.out.println(Colors.ANSI_YELLOW.getColor() + "How much do you wish to withdraw?" + Colors.ANSI_RESET.getColor());
						amount = input.nextDouble();
						input.nextLine();
						acct.withdraw(amount);
						boolean updated = accountdao.updateAccount(acct);
						
						if(updated) {
							System.out.println(Colors.ANSI_GREEN.getColor() + "Withdraw made succesfully!!!" + Colors.ANSI_RESET.getColor());
							System.out.println("Your new balance is " + Colors.ANSI_GREEN.getColor() + "$" + acct.getBalance() + Colors.ANSI_RESET.getColor());
							continueApp(customer);
							valid = false;
						}else {
							throw new Exception();
						}
						
					}catch(Exception e) {
						input.nextLine();
						System.out.println(Colors.ANSI_RED.getColor() + "Please enter a valid amount!" + Colors.ANSI_RESET.getColor());
					}
				}
			}
			if(acct == null) {
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void viewTransactions(Customer customer) {
		int choice;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+--------------------+\n| Recent Transaction |\n+--------------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_YELLOW.getColor() + "For which account do you wish to view the previous transactions?" + Colors.ANSI_RESET.getColor());
			for(Account a : accounts) {
				System.out.println("Account " + a.getId());
			}
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
			choice = input.nextInt();
			for(Account a : accounts) {
				if(a.getId() == choice) {
					acct = a;
					if (acct.getPrevious_amount() > 0) {
						System.out.println("Deposit Amount of " + Colors.ANSI_GREEN.getColor() + "$" + acct.getPrevious_amount() + Colors.ANSI_RESET.getColor() + " was made to Account " + acct.getId() + "\nYour current Balance is " + Colors.ANSI_GREEN.getColor() + "$" + acct.getBalance() + Colors.ANSI_RESET.getColor());
					} else if (acct.getPrevious_amount() < 0) {
						System.out.println("Withdrawal Amount of " + Colors.ANSI_GREEN.getColor() + "$" + Math.abs(acct.getPrevious_amount()) + Colors.ANSI_RESET.getColor() + " was made to Account " + acct.getId() + "\nYour current Balance is " + Colors.ANSI_GREEN.getColor() + "$" + acct.getBalance() + Colors.ANSI_RESET.getColor());
					} else {
						System.out.println(Colors.ANSI_YELLOW.getColor() + "No transaction occured!" + Colors.ANSI_RESET.getColor());
					}
					
					continueApp(customer);
					valid = false;
				}
				
			}
			if(acct == null) {
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void continueApp(Customer customer) {
		int choice;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println(Colors.ANSI_GREEN.getColor() + "\nDo you wish to continue? 1=Yes 2=No");
			System.out.println("Enter Choice (1, or 2) :" + Colors.ANSI_RESET.getColor());
			try{
				choice = input.nextInt();
				input.nextLine();
				switch (choice) {
				case 1:
					welcomeCustomer(customer);
					valid = false;
					break;
				case 2:
					signOut();
					valid = false;
					break;
				default:
					System.out.println(Colors.ANSI_RED.getColor() + "Invalid choice!!\n" + Colors.ANSI_RESET.getColor());
					break;
				}
			}catch(IllegalArgumentException e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Please enter a number!" + Colors.ANSI_RESET.getColor());
			}
		}
	}
	
	public static void displayCustomer(Customer customer) {
		System.out.println(Colors.ANSI_BLUE.getColor() + "\n+----------------------+\n| Customer Information |\n+----------------------+" + Colors.ANSI_RESET.getColor());
		System.out.println("Customer Id: " + Colors.ANSI_YELLOW.getColor() + customer.getId() + Colors.ANSI_RESET.getColor());
		System.out.println("Customer Name: " + Colors.ANSI_YELLOW.getColor() + customer.getName() + Colors.ANSI_RESET.getColor());
		System.out.println("Customer Address: " + Colors.ANSI_YELLOW.getColor() + customer.getAddress() + Colors.ANSI_RESET.getColor());
		System.out.println("Customer Phone Number: " + Colors.ANSI_YELLOW.getColor() + customer.getPhone_number() + Colors.ANSI_RESET.getColor());
		System.out.println("Accounts: ");
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		System.out.println(Colors.ANSI_YELLOW.getColor() + accounts + Colors.ANSI_RESET.getColor());
		
		continueApp(customer);
	}

	public static void main(String[] args) {
		
		welcome();
	}
}
