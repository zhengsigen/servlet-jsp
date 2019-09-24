package wangpang1801.zsg;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserDelete
 */
@WebServlet("/FileDelete")
public class FileDelete extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String name = arg0.getParameter("name");
		HttpSession session = arg0.getSession();
		String path = (String) session.getAttribute("path");
		String addr = path+"/"+name;
		
			File file = new File(addr);

			if(file.exists()) {
				delFile(file);	
			}
			
			if(file.exists()) {
				file.delete();
			}
		
			arg1.sendRedirect("/MyResource");		
		
	}
	public void delFile(File file) {
		if(file.isFile()) {
			file.delete();	
		}else {
			if(!file.delete()) {	
				File[] files = file.listFiles();	
				for(File f : files) {		
					delFile(f);
					f.delete();			
				}
			}
		}	
	}
}
