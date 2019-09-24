package wangpang1801.zsg;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.getRequestDispatcher("/interface/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// 用户名
		String name = req.getParameter("name");
		// 密码
		String passwd = req.getParameter("passwd");


		// 保留原来的数据
		req.setAttribute("name", name);
		req.setAttribute("passwd", passwd);

		String msg = null;

		if (name == null || name.trim().length() == 0) {
			msg = "请输入用户名";
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/interface/login.jsp").forward(req, resp);
			return;
		}
		if (name != null && name.length() > 50) {
			msg = "用户名最大长度是50";
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/interface/login.jsp").forward(req, resp);
			return;
		}
		if (passwd == null || passwd.trim().length() == 0) {
			msg = "请输入密码";
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/interface/login.jsp").forward(req, resp);
			return;
		}
		if (passwd != null && passwd.length() > 20) {
			msg = "密码最大长度是20";
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/interface/login.jsp").forward(req, resp);
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/2-17wangpan?user=root&password=123&useSSL=false");
			PreparedStatement ps = connection.prepareStatement("select id,name,header_img from user where name = ? and passwd = ?");
			ps.setString(1, name);
			ps.setString(2, passwd);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){ // 说明有记录
				HttpSession session = req.getSession();
				session.setAttribute("USER", name);
				session.setAttribute("USER_IMG", rs.getString("header_img"));
				String path ="D:/JAVA/1801wangpan/WebContent/uploads/file";
				session.setAttribute("path", path);
				resp.sendRedirect("/DbankList");
				
				
			}else{
				msg = "用户名与密码不匹配";
				req.setAttribute("msg", msg);
				req.getRequestDispatcher("/interface/login.jsp").forward(req, resp);
				return;
				
				
			}
		} catch (Exception e) {
			
		}
	}

}
