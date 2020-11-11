import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ATM {

	public static boolean isLogin = false;
	
	private static int max_tranfer = 400000;
	
	private static int limit = 10000000; //max amount that the atm can store at a particular point of time. 
	
	public static void main(String[] args) throws Exception {
		int option = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the ATM !!");
		
		while(option==0) {
			
			System.out.println("select an option ");
			System.out.println("1. create new account");
			System.out.println("2. sign in");
			
			while(option<1 || option>2) {
				System.out.println("Type your choice, you must choose 1 or 2: ");
				option = scanner.nextInt();
			}
		}
		
		switch (option) {
		case 1:
			System.out.println("To create new account, please enter your details: ");
			
			System.out.println("Enter first name: ");
			String firstName = scanner.next().trim();
			
			System.out.println("Enter last name: ");
			String lastName = scanner.next().trim();
			
			Account account = new Account(firstName, lastName);
			account.register();
			System.out.println("Have a great day!");
			break;
		case 2:
			while(isLogin == false) {
				System.out.println("SIGN IN");
				
				System.out.println("Enter your card number: ");
				String cardnumber = scanner.next();
				
				System.out.println("Enter your pin: ");
				String pincode = scanner.next();
				
				Operation operation = new Operation(cardnumber, pincode);
				
				try {
					Connection c = DataBase.connection();
					
					Statement stmt4 = c.createStatement();
					
					String sql4 = "select * from Card where card_number = " + cardnumber + " and pincode = " + pincode + " ; ";
					
					ResultSet rs4 = stmt4.executeQuery(sql4);
					
					if(rs4.next()) {
						isLogin = true;
						
						System.out.println("LOGIN SUCCESSFUL !!");
						
						while(true) {
						
						System.out.println("What do you wanna do now? Choose from the following: ");
						
						System.out.println("1. Check Balance ");
						System.out.println("2. Deposit amount in your account ");
						System.out.println("3. withdrawal ");
						System.out.println("4. Transfer amount to another account ");
						System.out.println("5. change pin");
						System.out.println("6. Quit");
						
						
						int option_user = 0;
						
						while(option_user < 1 || option_user > 6) {
							System.out.println("Type your choice: ");
							
							option_user = scanner.nextInt();
						}
						
						int balance = 0;
						
						switch (option_user) {
						case 1:
							System.out.print("The balance in your account is: ");
							
							balance = operation.showBalance(cardnumber);
							
							System.out.println("Rs. " + balance);
							
							break;
						case 2:
							int amount=0;
							while(true) {

								System.out.println("Enter a positive amount that you wanna deposit: ");
								amount = scanner.nextInt();
									
								if(amount>=0 && amount<=limit) {
									
									operation.deposit(amount, cardnumber);
									
									balance = operation.showBalance(cardnumber);
									
									System.out.println("The amount has been deposited in your account successfully!");
									System.out.println("Your current balance is Rs. " + balance);
									break;
								}
								else
								{
									System.out.println("Action cannot be performed. Please make sure to enter an amount not more than the limit!");
								}
								}
							break;
						case 3:
							int amount1 = 0;
							int balance_amount=0;
									
							try {
								Connection con = DataBase.connection();
								
								Statement stmt0 = con.createStatement();
								
								String sql0 = "select * from balance where card_number =" + cardnumber + ";";
								
								ResultSet rs0 = stmt0.executeQuery(sql0);
								
								while(rs0.next()) {
									balance_amount = rs0.getInt(2);
								}
								
								while(true) {

								System.out.println("Enter a positive amount you wanna withdraw: ");
								amount1 = scanner.nextInt();
									
								if(amount1>=0 && amount1<=balance_amount) {
									
									operation.withdraw(amount1, cardnumber);
									
									balance = operation.showBalance(cardnumber);
									
									System.out.println("Withdrawal succesful!");
									System.out.println("Your current balance is Rs. " + balance);
									break;
								}
								else
								{
									System.out.println("Action cannot be performed. Try checking your account balance!");
								}
								}
								
								
							}
							catch (Exception e) {
								System.out.println(e);
							}
							break;
						case 4:
							
							System.out.println("Enter the card number of the account you wanna transfer amount to: ");
							
							String number_other = scanner.next();
							
							int amount_other = 0;
							balance = operation.showBalance(cardnumber);
							int balance_other = operation.showBalance(number_other);
							
							
							while(true) {
								//to transfer amount from one account to the other.
								System.out.println("Enter the amount you wanna transfer: ");
								amount_other = scanner.nextInt();
									
								if(amount_other >= 0 && amount_other<=max_tranfer && amount_other<=balance ) {
									
									operation.sendMoneyToOthers(amount_other, number_other, cardnumber);
									
									System.out.println("The amount has successfully been transferred!");
									break;
								}
								else
								{
									System.out.println("Action cannot be performed. Please make sure to enter an amount not more than the maximum or try checking your balance!");
								}
								}

							break;
						case 5:
							String newPin="";
							String newPin1="";
							while(true) {

								System.out.println("Enter a new five digit pin: ");
								newPin = scanner.next();
									
								System.out.println("Re-Enter the pin: ");
								newPin1 = scanner.next();
								
								if(newPin.equals(newPin1) && operation.isValidpin(newPin)) {
									
									operation.changePin(cardnumber, newPin);
									
									System.out.println("The PIN has been changed successfully!");
									break;
								}
								else
								{
									System.out.println("Make sure you enter valid pin");
								}
								}
							break;
						case 6:
							System.out.println("Have a great day!");   
			    			System.exit(0);
			    			break;
						default:
							break;
						}						
					}						
					}else {
							System.out.println("Login fail");
						}
						
					}catch(Exception e){
						System.out.println(e);
					}
				}
				break;
			default:
				break;
		}			
	}
}
