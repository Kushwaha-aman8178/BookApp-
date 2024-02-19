package package01;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet{
    private static final String query = "SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKLIST";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter prt = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(Exception e) {
			e.printStackTrace();
			prt.println(e.getMessage());
		}
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "system";
			String pass = "aman123";
			Connection con = DriverManager.getConnection(url,user,pass);
			
			PreparedStatement ptst = con.prepareStatement(query);
			ResultSet rst = ptst.executeQuery();
			
			prt.println("<table>");
			prt.println("<tr>");
			prt.println("<th>BookName</th>");
			prt.println("<th>BookEdition</th>");
			prt.println("<th>BookPrice</th>");
			prt.println("<th>Edit</th>");
			prt.println("<th>Delete</th>");
			prt.println("</tr>");
			
			while(rst.next()) {
				prt.println("<tr>");
				prt.println("<td>"+rst.getString(1)+"</td>");
				prt.println("<td>"+rst.getString(2)+"</td>");
				prt.println("<td>"+rst.getFloat(3)+"</td>");
				prt.println("<td><a href='editUrl?bookName="+rst.getString(1)+"'>Edit</a></td>");
				prt.println("<td><a href='deleteUrl?bookName="+rst.getString(1)+"'>Delete</a></td>");
				prt.println("</tr>");
			}
			prt.println("</table>");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			prt.println(e.getMessage());
		}
		prt.println("<a href=home.html>Home</a>");
	}

}
