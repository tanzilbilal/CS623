package project;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Projectdbms {

	public static void main(String[] args) throws SQLException, IOException, 
	ClassNotFoundException {
		
		// Load the MySQL driver
				Class.forName("org.postgresql.Driver");
				
				// Connect to the default database with credentials
				
				Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","786780");
		
				// For atomicity
				conn.setAutoCommit(false);
				
				// For isolation
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				
				Statement stmt1 = null;
				try {
					// Create statement object
					stmt1 = conn.createStatement();
					
					stmt1.executeUpdate("ALTER TABLE Stock DROP CONSTRAINT fk_prod_Stock,ADD CONSTRAINT fk_prod_Stock FOREIGN KEY(Prod)REFERENCES Product(Prod)ON DELETE CASCADE ON UPDATE CASCADE;");
					
					
                    
                    //5.We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.

				    stmt1.executeUpdate("INSERT INTO Product VALUES ('p100','cd',5)");
				    stmt1.executeUpdate("INSERT INTO Stock VALUES ('p100','d2',50)");
				    
				  //3.The product p1 changes its name to pp1 in Product and Stock.
					
					stmt1.executeUpdate("UPDATE Product SET prod='PP1' WHERE prod ='P1'");
					
					//1.The product p1 is deleted from Product and Stock.
					
					stmt1.executeUpdate("DELETE FROM Product WHERE prod ='PP1'");
				}
				catch(SQLException e) {
					System.out.println("An Exception was thrown"+e);
					//e.printStackTree();
					
					// for atomicity
					
					conn.rollback();
					stmt1.close();
					conn.close();
					return;
				}
				conn.commit();
				stmt1.close();
				conn.close();
				
	}
}

				
