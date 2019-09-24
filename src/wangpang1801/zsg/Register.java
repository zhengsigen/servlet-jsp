package wangpang1801.zsg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
@MultipartConfig
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

		String answer =null;
	
		String name = request.getParameter("name");
		String passwd = request.getParameter("passwd");
		Part headerImg = request.getPart("headerImg");
		
		request.setAttribute("name", name);
		request.setAttribute("passwd", passwd);
		request.setAttribute("headerImg", headerImg);
		
		
		if (name == null || name.trim().length() == 0) {
			answer = "请输入用户名";
			// 错误信息传递到页面
			request.setAttribute("answer", answer);
			// 跳转回注册的页面
			request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
			return;
		}
		if (name != null && name.length() > 50) {
			answer = "用户名最大长度是50";
			// 错误信息传递到页面
			request.setAttribute("answer", answer);
			// 跳转回注册的页面
			request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
			return;
		}
		if (passwd == null || passwd.trim().length() == 0) {
			answer = "请输入密码";
			// 错误信息传递到页面
			request.setAttribute("answer", answer);
			// 跳转回注册的页面
			request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
			return;
		}
		if (passwd != null && passwd.length() > 20) {
			answer = "密码最大长度是20";
			request.setAttribute("answer", answer);
			// 跳转回注册的页面
			request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
			return;
		}

		if (headerImg == null || headerImg.getSize() == 0) {
			answer = "头像未上传";
			// 错误信息传递到页面
			request.setAttribute("answer", answer);
			// 跳转回注册的页面
			request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
			return;
		}
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/2-17wangpan?user=root&password=123&useSSL=false");
			PreparedStatement ps = connection.prepareStatement("select count(*) from user where name = ?");
			ps.setString(1, name);
			ResultSet rs =ps.executeQuery();
			rs.next();
			long count = rs.getLong(1);
			if(count>0) {
				answer = "用户名已经被注册";
				request.setAttribute("answer", answer);
				request.getRequestDispatcher("/interface/register.jsp").forward(request, response);
				return;
			}
			ps = connection.prepareStatement("insert into user(name, passwd, header_img) values(?, ?, ?)");
			ps.setString(1, name);
			ps.setString(2, passwd);
			ps.setString(3, save(request, headerImg));
			ps.executeUpdate();
			request.getRequestDispatcher("/interface/reg_success.jsp").forward(request, response);
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	private String save(HttpServletRequest req, Part part) throws IOException {
		InputStream is = part.getInputStream(); // 流[文件]
		String uplaodsDir = req.getServletContext().getRealPath("/uploads/imgs"); // 保存上传文件的路径 
		String contentDisposition = part.getHeader("content-disposition");
		String fileName = contentDisposition.split("; ")[2].split("=")[1].replaceAll("\"", "");
		File file = new File(uplaodsDir, UUID.randomUUID().toString() + "-" + fileName);
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		String path = "/uploads/imgs/"+file.getName(); // uploads/imgs/2749027lk92783490.png
		FileOutputStream fos = new FileOutputStream(file);
		byte[] bytes = new byte[1024 * 1024]; // 1M
		int length = -1;
		while ((length = is.read(bytes)) != -1) {
			fos.write(bytes, 0, length);
		}
		fos.close();
		return path;
	}
}
