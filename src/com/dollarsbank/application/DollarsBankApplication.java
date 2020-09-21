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
import com.dollarsbank.exceptions.*;

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
		System.out.println(Colors.ANSI_GREEN.getColor() + "\nSuccessfully Logged Out!!" + Colors.ANSI_RESET.getColor());
		welcome();
	}
	
	public static boolean verifyPattern(Pattern p, String s) {
		Matcher matcher = p.matcher(s);
		if(matcher.matches() == true) {
			return true;
		}
		return false;
	}
	
	public static void createAccount() {
		String name;
		String address;
		String phone_number;
		String id;
		String password;
		int initial_deposit;
		boolean valid = true;
		Scanner input = new Scanner(System.in);
		
		Pattern namePattern = Pattern.compile("^(([a-z]+|[a-zA-Z]+))|([a-zA-Z]+\\s{1}[a-zA-Z]+)$");
		Pattern phonePattern = Pattern.compile("^.?\\d{3}.?(\\s{1}|.)?\\d{3}.?\\d{4}$");
		Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[@#$%!^&])(?=.*[A-Z]).{8}");
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+-------------------------------+\n| Enter Details For New Account |\n+-------------------------------+" + Colors.ANSI_RESET.getColor());
			
			try {
				System.out.println("Customer Name:");
				System.out.print(Colors.ANSI_CYAN.getColor());
				name = input.nextLine();
				if(verifyPattern(namePattern, name) == false) {
					throw new InvalidNameException(name);
				}
				System.out.println(Colors.ANSI_RESET.getColor() + "Customer Address:");
				System.out.print(Colors.ANSI_CYAN.getColor());
				address = input.nextLine();
				System.out.println(Colors.ANSI_RESET.getColor() + "Customer Contact Number:");
				System.out.print(Colors.ANSI_CYAN.getColor());
				phone_number = input.nextLine();
				if(verifyPattern(phonePattern, phone_number) == false) {
					throw new InvalidPhoneFormatException();
				}
				System.out.println(Colors.ANSI_RESET.getColor() + "User Id:");
				System.out.print(Colors.ANSI_CYAN.getColor());
				id = input.nextLine();
				Customer idCheck = customerdao.getCustomerById(id);
				if(idCheck == null) {
					System.out.println(Colors.ANSI_RESET.getColor() + "Password: 8 Characters With Lower, Upper & Special");
					System.out.print(Colors.ANSI_CYAN.getColor());
					password = input.nextLine();
					if(verifyPattern(passwordPattern, password)) {
						Customer passwordCheck = customerdao.getCustomerByPassword(password);
						if(passwordCheck == null) {
							System.out.println(Colors.ANSI_RESET.getColor() + "Initial Deposit Amount:");
							System.out.print(Colors.ANSI_CYAN.getColor());
							initial_deposit = input.nextInt();
							input.nextLine();
							System.out.print(Colors.ANSI_RESET.getColor());
							if(initial_deposit >= 25) {
								Customer customer = new Customer(id, name, address, phone_number, password);
								customerdao.addCustomer(customer);
								Account account = new Account();
								account.deposit(initial_deposit);
								account.setCustomer_id(id);
								accountdao.addAccount(account);
								System.out.println(Colors.ANSI_GREEN.getColor() + "Account Created!!" + Colors.ANSI_RESET.getColor());
								System.out.println(Colors.ANSI_GREEN.getColor() + "Please Login to continue :)" + Colors.ANSI_RESET.getColor());
								login();
								valid = false;
							}else {
								throw new InvalidInitialDepositException();
							}
						}else {
							throw new DuplicatePasswordException();
						}
					}else {
						throw new InvalidPasswordCreationException();
					}
				}else {
					throw new DuplicateIdException();
				}
				
			}catch(InvalidNameException e) {
				
			}catch(DuplicateIdException e) {
			
			}catch(DuplicatePasswordException e) {
				
			}catch (InvalidInitialDepositException e) {
				
			}catch (InvalidPhoneFormatException e) {
				
			}catch (InvalidPasswordCreationException e) {
				
			}catch(Exception e) {
				System.out.println(Colors.ANSI_RED.getColor() + "Invalid Input!");
			}
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
						throw new InvalidPasswordException();
					}
				}else {
					counter += 1;
					throw new InvalidUserIdException();
				}
			} catch (InvalidUserIdException e) {
				valid = checkLoginSuccess(counter);
			} catch (InvalidPasswordException e) {
				valid = checkLoginSuccess(counter);
			}
		}
	}
	
	public static boolean checkLoginSuccess(int num) {
		if(num == 3) {
			System.out.println(Colors.ANSI_RED.getColor() + "Too many unsuccessful logins!!");
			exit();
			return false;
		}
		return true;
	}
	
	public static void welcomeCustomer(Customer customer) {
		int choice;
		
		System.out.println(Colors.ANSI_BLUE.getColor() + "\n+---------------------+\n| WELCOME Customer!!! |\n+---------------------+" + Colors.ANSI_RESET.getColor());
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println("1: Make Deposit\n2: Make Withdrawal\n3: Create New Bank Account\n4: Transfer Funds\n5: View Recent Transaction\n6: Display Customer Information\n7: Sign out");
			System.out.println(Colors.ANSI_GREEN.getColor() + "\nEnter Choice (1,2,3,4,5, or 6) :" + Colors.ANSI_RESET.getColor());
			try{
				choice = input.nextInt();
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
					newAccount(customer);
					valid = false;
					break;
				case 4:
					transferFunds(customer);
					valid = false;
					break;
				case 5:
					viewTransactions(customer);
					valid = false;
					break;
				case 6:
					displayCustomer(customer);
					valid = false;
					break;
				case 7:
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
	
	public static void transferFunds(Customer customer) {
		double amount;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct1 = null;
		Account acct2 = null;
		
		while(valid) {
			int choice1;
			int choice2;
			try {
				System.out.println(Colors.ANSI_BLUE.getColor() + "\n+----------------+\n| Transfer Funds |\n+----------------+" + Colors.ANSI_RESET.getColor());
				System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to transfer from?" + Colors.ANSI_RESET.getColor());
				for(Account a : accounts) {
					System.out.println("Account " + a.getId() + " has a balance of " + Colors.ANSI_GREEN.getColor() + "$" + a.getBalance() + Colors.ANSI_RESET.getColor());
				}
				System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
				choice1 = input.nextInt();
				
				acct1 = accounts.stream()
						.filter(account -> choice1 == account.getId())
						.findFirst()
						.orElse(null);
				
				if(acct1 != null) {
					System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to transfer to?" + Colors.ANSI_RESET.getColor());
					for(Account a : accounts) {
						if(a.getId() == acct1.getId()) {
							continue;
						}
						System.out.println("Account " + a.getId() + " has a balance of " + Colors.ANSI_GREEN.getColor() + "$" + a.getBalance() + Colors.ANSI_RESET.getColor());
					}
					
					System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
					choice2 = input.nextInt();
					if(choice2 == acct1.getId()) {
						throw new DuplicateChoiceException(choice2);
					}
					
					acct2 = accounts.stream()
							.filter(account -> choice2 == account.getId())
							.findFirst()
							.orElse(null);
					
					if(acct2 != null) {
						System.out.println(Colors.ANSI_YELLOW.getColor() + "How much do you wish to transfer? " + Colors.ANSI_RESET.getColor());
						amount = input.nextDouble();
						if(amount <= acct1.getBalance()) {
							acct2.deposit(amount);
							acct1.withdraw(amount);
							accountdao.updateAccount(acct2);
							accountdao.updateAccount(acct1);
							
							System.out.println(Colors.ANSI_GREEN.getColor() + "Transfer made succesfully!!!" + Colors.ANSI_RESET.getColor());
							System.out.println(Colors.ANSI_GREEN.getColor() + "Your new balance for Account " + acct1.getId() + "is $" + acct1.getBalance() + Colors.ANSI_RESET.getColor());
							System.out.println(Colors.ANSI_GREEN.getColor() + "Your new balance for Account " + acct2.getId() + "is $" + acct2.getBalance() + Colors.ANSI_RESET.getColor());
							continueApp(customer);
							valid = false;
						}else {
							throw new AmountOutOfReachException(acct1, amount);
						}
					}else {
						throw new InvalidAccountIdException(choice2);
					}
				}else {
					throw new InvalidAccountIdException(choice1);
				}
			}catch(InvalidAccountIdException e) {
				
			}catch(DuplicateChoiceException e) {
				
			}catch(AmountOutOfReachException e) {
				
			}
		}
	}
	
	public static void newAccount(Customer customer) {
		double initial_deposit;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+-------------------------+\n| Create New Bank Account |\n+-------------------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_GREEN.getColor() + "Enter Initial Deposit Amount:" + Colors.ANSI_RESET.getColor());
			try {
				initial_deposit = input.nextInt();
				if(initial_deposit < 25) {
					throw new InvalidInitialDepositException();
				}
				
				Account account = new Account();
				account.deposit(initial_deposit);
				account.setCustomer_id(customer.getId());
				boolean created = accountdao.addAccount(account);
				System.out.println(Colors.ANSI_GREEN.getColor() + "Bank Account created Successfully!!" + Colors.ANSI_RESET.getColor());
				welcomeCustomer(customer);
				valid = false;
			}catch(InvalidInitialDepositException e) {
				
			}
		}
	}
	
	public static void makeDeposit(Customer customer) {
		double amount;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			int choice;
			try {
				System.out.println(Colors.ANSI_BLUE.getColor() + "\n+----------------+\n| Deposit Portal |\n+----------------+" + Colors.ANSI_RESET.getColor());
				System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to deposit to?" + Colors.ANSI_RESET.getColor());
				for(Account a : accounts) {
					System.out.println("Account " + a.getId());
				}
				System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
				choice = input.nextInt();
				
				acct = accounts.stream()
						.filter(account -> choice == account.getId())
						.findFirst()
						.orElse(null);
				
				if(acct != null) {
					valid = false;
				}else {
					System.out.println(Colors.ANSI_RED.getColor() + "Please enter correct Account Id!!" + Colors.ANSI_RESET.getColor());
				}
			}catch(Exception e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
		
		valid = true;
		while(valid) {
			try{
				System.out.println(Colors.ANSI_YELLOW.getColor() + "How much do you wish to deposit?" + Colors.ANSI_RESET.getColor());
				amount = input.nextDouble();
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
	
	public static void makeWithdraw(Customer customer) {
		double amount;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			int choice;
			try {
			System.out.println(Colors.ANSI_BLUE.getColor() + "\n+-------------------+\n| Withdrawal Portal |\n+-------------------+" + Colors.ANSI_RESET.getColor());
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Which account do you wish to withdraw from?" + Colors.ANSI_RESET.getColor());
			for(Account a : accounts) {
				System.out.println("Account " + a.getId() + " has a balance of " + Colors.ANSI_GREEN.getColor() + "$" + a.getBalance() + Colors.ANSI_RESET.getColor());
			}
			System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
			choice = input.nextInt();
			
			acct = accounts.stream()
					.filter(account -> choice == account.getId())
					.findFirst()
					.orElse(null);
			
			if(acct != null) {
				valid = false;
			}else {
				System.out.println(Colors.ANSI_RED.getColor() + "Please enter correct Account Id!!" + Colors.ANSI_RESET.getColor());
			}
			
			}catch(Exception e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
			
		valid = true;
		while(valid) {
			try{
				System.out.println(Colors.ANSI_YELLOW.getColor() + "How much do you wish to withdraw?" + Colors.ANSI_RESET.getColor());
				amount = input.nextDouble();
				input.nextLine();
				if(acct.withdraw(amount)) {
					accountdao.updateAccount(acct);
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
	
	public static void viewTransactions(Customer customer) {
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		List<Account> accounts = accountdao.getAccountsByCustomerId(customer.getId());
		Account acct = null;
		
		while(valid) {
			int choice;
			try {
				System.out.println(Colors.ANSI_BLUE.getColor() + "\n+--------------------+\n| Recent Transaction |\n+--------------------+" + Colors.ANSI_RESET.getColor());
				System.out.println(Colors.ANSI_YELLOW.getColor() + "For which account do you wish to view the previous transactions?" + Colors.ANSI_RESET.getColor());
				for(Account a : accounts) {
					System.out.println("Account " + a.getId());
				}
				System.out.println(Colors.ANSI_YELLOW.getColor() + "Enter Account Id: " + Colors.ANSI_RESET.getColor());
				choice = input.nextInt();
				
				acct = accounts.stream()
						.filter(account -> choice == account.getId())
						.findFirst()
						.orElse(null);
				
				if(acct != null) {
					valid = false;
				}else {
					System.out.println(Colors.ANSI_RED.getColor() + "Please enter correct Account Id!!" + Colors.ANSI_RESET.getColor());
				}
				
			}catch(Exception e) {
				input.nextLine();
				System.out.println(Colors.ANSI_RED.getColor() + "Not a valid choice!!" + Colors.ANSI_RESET.getColor());
			}
		}
		
		if (acct.getPrevious_amount() > 0) {
			System.out.println("Deposit Amount of " + Colors.ANSI_GREEN.getColor() + "$" + acct.getPrevious_amount() + Colors.ANSI_RESET.getColor() + " was made to Account " + acct.getId() + "\nYour current Balance is " + Colors.ANSI_GREEN.getColor() + "$" + acct.getBalance() + Colors.ANSI_RESET.getColor());
		} else if (acct.getPrevious_amount() < 0) {
			System.out.println("Withdrawal Amount of " + Colors.ANSI_GREEN.getColor() + "$" + Math.abs(acct.getPrevious_amount()) + Colors.ANSI_RESET.getColor() + " was made to Account " + acct.getId() + "\nYour current Balance is " + Colors.ANSI_GREEN.getColor() + "$" + acct.getBalance() + Colors.ANSI_RESET.getColor());
		} else {
			System.out.println(Colors.ANSI_YELLOW.getColor() + "No transaction occured!" + Colors.ANSI_RESET.getColor());
		}
		
		continueApp(customer);
	}
	
	public static void continueApp(Customer customer) {
		int choice;
		Scanner input = new Scanner(System.in);
		boolean valid = true;
		
		while(valid) {
			System.out.println(Colors.ANSI_GREEN.getColor() + "\nDo you wish to continue or signout? 1=continue 2=signout");
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
		System.out.print(Colors.ANSI_YELLOW.getColor());
		for(Account a : accounts) {
			System.out.println("Account " + a.getId() + " has a balance of $" + a.getBalance());
		}
		System.out.println(Colors.ANSI_RESET.getColor());
		
		continueApp(customer);
	}

	public static void main(String[] args) {
		
		welcome();
	}
}
