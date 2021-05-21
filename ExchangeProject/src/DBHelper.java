import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	
	public static Connection conn = null;
	public static MyModel model = null;
	public static PreparedStatement state = null;
	public static ResultSet result = null;
	
	public static Connection getConnection() {
		
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:C:\\\\JavaDB./test;AUTO_SERVER=TRUE", "ilis08", "ilis08");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}//end method
	
	public static MyModel getAllData(String tableName){
		conn = getConnection();
		try {
			state = conn.prepareStatement("SELECT * FROM" + " " +tableName);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
}
