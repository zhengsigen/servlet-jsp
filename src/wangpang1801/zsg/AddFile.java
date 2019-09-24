package wangpang1801.zsg;


import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DbankList
 */
@WebServlet("/AddFile")
public class AddFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("filename");
		
		String path1 = request.getParameter("path");
		
		//List<FilePath> fp = (List<FilePath>) request.getSession().getAttribute("fp1");
		
		if(name.trim() == "" || name == null){
			
			response.sendRedirect("/MyResource");
			
			return;
		}

		/*String anse = null;
		for(FilePath f:fp) {
	
			if(f.getName().equals("name")) {
				
				anse ="此文件名已存在";
				request.setAttribute("anse", anse);
				
				request.getRequestDispatcher("/MyResource").forward(request, response);
				
				return;
			}
		}*/
		
		File file =new File(path1+"/"+name);
		
		file.mkdir();

		response.sendRedirect("/MyResource");
	}

}
