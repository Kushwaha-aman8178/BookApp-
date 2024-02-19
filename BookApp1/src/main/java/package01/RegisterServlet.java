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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	private static final String query = "INSERT INTO BOOKLIST(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter prt = resp.getWriter();
		resp.setContentType("text/html");
		
		String BookName = req.getParameter("bookName");
		String BookEdition = req.getParameter("bookEdition");
		float BookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e) {
		     e.printStackTrace();
		}
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "system";
			String pass = "aman123";
			Connection con = DriverManager.getConnection(url,user,pass);
			
			PreparedStatement ptst = con.prepareStatement(query);
			ptst.setString(1, BookName);
			ptst.setString(2, BookEdition);
			ptst.setFloat(3, BookPrice);
			
			int cnt = ptst.executeUpdate();
			if(cnt==1) {
				prt.println("<h2>Book Registered Successfully</h2>");
			}
			else {
				prt.println("<h2>Book not registered</h2>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			prt.println("SQLException is found");
			prt.println(e.getMessage());
		}
		
		prt.println("<a href=home.html>Home</a><br>");
		prt.println("<a href=booklist>Booklist</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	

}
