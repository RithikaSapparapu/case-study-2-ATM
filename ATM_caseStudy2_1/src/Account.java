import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Account {

	private String firstname;
	
	private String lastname;
	
	//private Integer balance;
	
	Account(String firstname, String lastname){
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public boolean register() throws Exception{
		try {
			Connection c = DataBase.connection();
			Statement stmt = c.createStatement();
			String sql = "insert into account values(null, ' " + this.firstname + " ' , ' "  + this.lastname + " ');"; 
			
			stmt.executeUpdate(sql);
			
			Statement stmt2 = c.createStatement();
			
			String sql2 = "select last_insert_id()";
			ResultSet rs2 =  stmt.executeQuery(sql2);
			
			int last_account_id = 0;
			
			while(rs2.next()) {
				last_account_id = rs2.getInt(1);
			}
			
			String cardNumber = this.generateCardNumber();
			
			String code = this.generatePincode();
			
			Statement stmt3 = c.createStatement();
			
			String sql3 = "insert into card values ( " + last_account_id + " , " + cardNumber + " , " + code + "  ); "; 
			
			stmt3.executeUpdate(sql3);
			
			Statement stmt4 = c.createStatement();
			
			String sql4 = "insert into balance values ( " + cardNumber + " , '0' ); ";
			
			stmt4.executeUpdate(sql4);
			
			c.close();
			
			System.out.println("Account is successfully created!");
			
			System.out.println("card number: " + cardNumber);
			
			System.out.println("pin number: " + code);
			
			return true;	
		}catch (Exception e) {
			System.out.println(e);
			
			return false;
		}
	}
	
	public String generateCardNumber() {
		int length = 5;
		String card ="";
		while(true) {
		String characterSet = "1234567890";
		
		char[] cardNumber = new char[length];
		
		for(int i=0; i<length; i++)
		{
			int rand = (int) (Math.random()*characterSet.length());
			cardNumber[i] = characterSet.charAt(rand); 
		}
		
		card =  new String(cardNumber);
		
		if(isValidCard(new String(cardNumber))) {
			break;
		}
		}
		return card;
	}
	
	
	public String generatePincode() {
		int length = 5;
		String card ="";
		while(true) {
		String characterSet = "1234567890";
		
		char[] cardNumber = new char[length];
		
		for(int i=0; i<length; i++)
		{
			int rand = (int) (Math.random()*characterSet.length());
			cardNumber[i] = characterSet.charAt(rand); 
		}
		
		card =  new String(cardNumber);
		
		if(isValidpin(new String(cardNumber))) {
			break;
		}
		}
		return card;
	}

	

	public boolean isValidCard(String card) {
	    String validCardPattern = "^[1-9][0-9]{4}$";
	    Pattern pattern = Pattern.compile(validCardPattern);
	    if (pattern.matcher(card).matches()) {
	        return true;
	    }
	    return false;
	}

	
	public boolean isValidpin(String card) {
	    String validCardPattern = "^[1-9][0-9]{4}$";
	    Pattern pattern = Pattern.compile(validCardPattern);
	    if (pattern.matcher(card).matches()) {
	        return true;
	    }
	    return false;
	}

	
	
}
