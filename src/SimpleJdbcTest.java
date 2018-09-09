import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/SimpleJdbcTest")
public class SimpleJdbcTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.print("<html><body><h1>hello</h1>");

		try {

			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyJdbcThing");

			out.print("<h3>Database Info</h3> ");

			con = ds.getConnection();

			out.print("<pre>");
			out.print(".getDriverName()             : " + con.getMetaData().getDriverName()+"</br>");
			out.print(".getDriverVersion()          : " + con.getMetaData().getDriverVersion() + "</br>");
			out.print(".getURL()                    : " + con.getMetaData().getURL() + "</br>");
			out.print(".getDatabaseProductVersion() : " + con.getMetaData().getDatabaseProductVersion() + "</br>" );
			out.print(".getUserName()               : " + con.getMetaData().getUserName() + "</br>");
			out.print("</pre>");

			out.print("<h3>Table Content</h3> ");

			stmt = con.createStatement();
			rslt = stmt.executeQuery("select id, name, note from TblTest");

			out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
			out.print("<th>ID</th>");
			out.print("<th>Name</th>");
			out.print("<th>Note</th>");

			while(rslt.next())
			{
				out.print("<tr>");
				out.print("<td>" + rslt.getInt("id") + "</td>");
				out.print("<td>" + rslt.getString("name") + "</td>");
				out.print("<td>" + rslt.getString("note") + "</td>");
				out.print("</tr>");
			}
			out.print("</table>");
			out.print("</body><br/></html>");

		} catch(Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			out.print("<pre>" + errors.toString() + "</pre>");
		} finally {	
			try {
				rslt.close();
				stmt.close();
				con.close();
				ctx.close();
			} catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				out.print("<pre>" + errors.toString() + "</pre>");
			}
		}
	}
}
