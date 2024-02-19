package package01;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Edit")
public class EditDataServlet extends HttpServlet{
    private static final String query = "UPDATE BOOKLIST SET BOOKEDITION=?, BOOKPRICE=? WHERE BOOKNAME=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter prt = resp.getWriter();
		resp.setContentType("text/html");
		
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		Float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		
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
			ptst.setString(1, bookEdition);
			ptst.setFloat(2, bookPrice);
			ptst.setString(3, bookName);
			
			int count = ptst.executeUpdate();
			if(count==1) {
				prt.println("<h2>Record updated successfully</h2>");
			}
			else {
				prt.println("<h2>Record not updated</h2>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			prt.println(e.getMessage());
		}
		prt.println("<a href='home.html'>Home</a><br>");
		prt.println("<a href='booklist'>Booklist</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	

}
