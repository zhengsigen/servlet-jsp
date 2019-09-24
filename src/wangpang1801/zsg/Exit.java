package wangpang1801.zsg;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Exit
 */
@WebServlet("/Exit")
public class Exit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		HttpSession session = arg0.getSession();
		if (session != null) {  
			session.invalidate();//调用session的invalidate()方法，将保存的session删除  
	    }
		arg1.sendRedirect("/Login");
		 
	}

}
