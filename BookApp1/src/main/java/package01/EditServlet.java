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

@WebServlet("/editUrl")
public class EditServlet extends HttpServlet{
    private static final String query = "SELECT BOOKEDITION,BOOKPRICE FROM BOOKLIST WHERE BOOKNAME=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("bookName");
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
			ptst.setString(1, name);
			
			ResultSet rst = ptst.executeQuery();
			
			rst.next();
			prt.println("<form action='Edit?bookName="+name+"' method='post'>");
			prt.println("<table>");
			
			prt.println("<tr>");
			prt.println("<td>BookName</td>");
			prt.println("<td><input type='text' name='bookName' value='"+name+"'></td>");
			prt.println("</tr>");
			
			prt.println("<tr>");
			prt.println("<td>BookEdition</td>");
			prt.println("<td><input type='text' name='bookEdition' value='"+rst.getString(1)+"'></td>");
			prt.println("</tr>");
			
			prt.println("<tr>");
			prt.println("<td>BookPrice</td>");
			prt.println("<td><input type='text' name='bookPrice' value='"+rst.getFloat(2)+"'></td>");
			prt.println("</tr>");
			
			prt.println("<tr>");
			prt.println("<td><input type='submit' value='Edit'></td>");
			prt.println("<td><input type='reset' value='Reset'></td>");
			prt.println("</tr>");
			
			prt.println("</table>");
			prt.println("</form>");
		}
		catch(Exception e) {
			e.printStackTrace();
			prt.println(e.getMessage());
		}
	}
	
	

}
