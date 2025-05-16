package SQL_first.SQL_first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {

	Connection con;
	Statement stmt;
	ResultSet rs;
	String Customer_FirstName;
	String Customer_FamilyName;
	WebDriver driver = new ChromeDriver();
	String Email = "@gmail.com";

	@BeforeTest
	public void mysetup() throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "Gameover111");
		driver.get("https://smartbuy-me.com/ar/account/register");
		driver.manage().window().maximize();
	}

	@Test(priority = 1, enabled = true)

	public void Insert_to_DataBase() throws SQLException {

		String query = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, city, country, salesRepEmployeeNumber, creditLimit) VALUES (2222, 'New Corp', 'Smith', 'John', '123456789', '123 Main St', 'Los Angeles', 'USA', 1370, 50000.00)";

		stmt = con.createStatement();
		int roweffected = stmt.executeUpdate(query);
		System.out.println("Inserted rows: " + roweffected);

	}

	@Test(priority = 2)

	public void Update_DataBase() throws SQLException {

		String query = "UPDATE customers SET creditLimit = 75000 WHERE customerNumber = 2222;";

		stmt = con.createStatement();
		int roweffected = stmt.executeUpdate(query);
		System.out.println(roweffected);

	}

	@Test(priority = 3)

	public void Read_DataBase() throws SQLException {

		String query = "SELECT * FROM customers WHERE customerNumber = 2222;";

		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			Customer_FirstName = rs.getString("contactFirstName");
			System.out.println("Customer First Name : " + Customer_FirstName);

			Customer_FamilyName = rs.getString("contactLastName");
			System.out.println("Customer Family Name : " + Customer_FamilyName);

		}

		driver.findElement(By.id("customer[first_name]")).sendKeys(Customer_FirstName);
		driver.findElement(By.id("customer[last_name]")).sendKeys(Customer_FamilyName);
		driver.findElement(By.id("customer[email]")).sendKeys(Customer_FirstName + Customer_FamilyName + Email);
		driver.findElement(By.id("customer[password]")).sendKeys(Customer_FamilyName + "@123");

		System.out.println("Password : " + Customer_FamilyName + "@123");

	}

	@Test(priority = 4)

	public void Delete_from_DataBase() throws SQLException {

		String query = "DELETE FROM customers WHERE customerNumber = 2222;";
		stmt = con.createStatement();
		int rowsAffected = stmt.executeUpdate(query);
		System.out.println("Deleted rows: " + rowsAffected);

	}

	@Test(priority = 5)
	public void confirm_delete() throws SQLException {
		String query = "SELECT * FROM customers WHERE customerNumber = 2222;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		if (!rs.next()) {
			System.out.println("Record deleted successfully.");
		} else {
			System.out.println("Record still exists!");
		}
	}
}
