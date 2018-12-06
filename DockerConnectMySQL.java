import java.util.Scanner;
import java.sql.*;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://db:3306/myDockerDB";
   static final String USER = "ifalchuk";
   static final String PASS = "password";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "DROP TABLE IF EXISTS Restaurants";
      stmt.executeUpdate(sql);
      sql = "CREATE TABLE Restaurants (Restaurant_id int, RestaurantName varchar(45), Description varchar(255) );";
      stmt.executeUpdate(sql);
      sql = "INSERT INTO Restaurants(Restaurant_id, RestaurantName, Description) VALUES (1, 'Stylowa' , 'Very comfortable restaurant'),(2, 'Progress', 'Cozy restaurant'),(3, 'Stary dom', 'Like in grandmoms house');";
      stmt.executeUpdate(sql);

      Scanner choises = new Scanner(System.in);
      String t;
            do {
                System.out.println("");
                System.out.println("Select what you want to do:");
                System.out.println("Add new restaurant (1)");
                System.out.println("Show restaurants (2)");
                System.out.println("Exit (0)");
                t = (String) menu.next();
                switch (t) {
                    case "1": {
                        Scanner insert = new Scanner(System.in);
			//selecting the last record 
                        sql = "SELECT Restaurant_id FROM Restaurants ORDER BY Restaurant_id DESC LIMIT 1;";
                        ResultSet rs = stmt.executeQuery(sql);
                        int last_id = 0;
                        if (rs.next()) {
                            last_id = rs.getInt("PersonID");
                        }
                        rs.close();
                        last_id++;
                        sql = "INSERT INTO Persons (PersonID, LastName, FirstName, City) VALUES (" + last_id + ",'";
                        System.out.println("Restaurant name:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Description:");
                        sql += insert.nextLine();
                        sql += "', '";
                        stmt.executeUpdate(sql);
                        break;
                    }
                    case "2": {
                        sql = "SELECT Restaurant_id, RestaurantName, Description FROM Persons";
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.printf("|%5s|%15s|%15s|\n", "ID: ", "Restaurant: ", "Description: ");
                        while (rs.next()) {
                            int id = rs.getInt("Restaurant_id");
                            String restName = rs.getString("RestaurantName");
                            String description = rs.getString("Description");
                            System.out.printf("|%4d |%14s |%14s |\n", id, restName, description);
                        }
                        rs.close();
                        break;
                    }
                    case "0": {
                        t = "0";
                        break;
                    }
                    default: {
			System.out.println("");
                        System.out.println("Select 1 or 2 or 0");
                        break;
                    }
                }
            } while (t != "0");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
