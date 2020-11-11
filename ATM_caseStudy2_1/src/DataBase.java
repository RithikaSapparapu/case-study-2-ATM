import java.sql.DriverManager;

public class DataBase {
	
	private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	
	private static final String connection = "jdbc:mysql://localhost:3306/atm";
	
	private static final String user  = "root";
	
	private static final String password  ="mysql@123456789";
	
	public static java.sql.Connection connection() throws Exception {
		
		Class.forName(dbClassName);
		
		return DriverManager.getConnection(connection, user, password);
	}
}
