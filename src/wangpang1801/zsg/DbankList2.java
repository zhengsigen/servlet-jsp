package wangpang1801.zsg;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DbankList
 */
@WebServlet("/DbankList2")
public class DbankList2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path1 = request.getParameter("path");
		
		int index = path1.lastIndexOf("/");

		String path = path1.substring(0,index);
		
		HttpSession session = request.getSession();
		
		session.setAttribute("path", path);
		
		response.sendRedirect("/DbankList");
		
	}
}
