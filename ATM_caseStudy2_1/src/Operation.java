import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Operation {
	
	private String cardnumber;
	
	private String pincode;
	
	private Integer balance;
	
	Operation (String cardnumber, String pincode){
		this.cardnumber = cardnumber;
		this.pincode = pincode;
	}
	
	public Integer showBalance(String cardnumber) {
		try {
			Connection c = DataBase.connection();

			Statement stmt5 = c.createStatement();
			
			String sql5 = "select * from balance where card_number =" + cardnumber + ";";
			
			ResultSet rs5 = stmt5.executeQuery(sql5);
			
			while(rs5.next()) {
				this.balance = rs5.getInt(2);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return this.balance;
	}
	
	public void deposit (Integer amount, String cardnumber) {
		try {
			Connection c = DataBase.connection();
			
			Statement stmt6 = c.createStatement();
			
			String sql6 = "update balance set balance = balance + " + amount + " where card_number = " + cardnumber + " ;";
			
			stmt6.executeUpdate(sql6);
			} catch (Exception e) {
				System.out.println(e);
			}
	}
	
	
	public void withdraw (Integer amount, String cardnumber) {
		try {
			Connection c = DataBase.connection();
			
			Statement stmt6 = c.createStatement();
			
			String sql6 = "update balance set balance = balance - " + amount + " where card_number = " + cardnumber + " ;";
			
			stmt6.executeUpdate(sql6);
			} catch (Exception e) {
				System.out.println(e);
			}
	}
	
	
	public void sendMoneyToOthers(Integer amount_other, String number_other, String card_number) {
		try {
			Connection c = DataBase.connection();
			
			Statement stmt8 = c.createStatement();
			
			String sql8 = "update balance set balance = balance + " + amount_other + " where card_number = " + number_other + ";";
			
			stmt8.executeUpdate(sql8);
			
			Statement stmt9 = c.createStatement();
			
			String sql9 = "update balance set balance = balance - " + amount_other + " where card_number = " + cardnumber + ";";
			
			stmt9.executeUpdate(sql9);
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	
	
	public void changePin(String cardnumber, String newPin) {
		try {
			Connection c = DataBase.connection();
			
			Statement stmt10 = c.createStatement();
			
			String sql10 = "update card set pincode = " + newPin + " where card_number = " + cardnumber + ";";
			
			stmt10.executeUpdate(sql10);
			
			
			} catch (Exception e) {
				System.out.println(e);
			}
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
