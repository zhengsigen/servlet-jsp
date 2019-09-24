package wangpang1801.zsg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class DbankList
 */
@WebServlet("/MyResource")
@MultipartConfig
public class MyResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("USER");
		String path = "D:/JAVA/1801wangpan/WebContent/uploads/file"+"/"+name;
		
		File file = new File(path);
		List<FilePath>fp = new ArrayList<>();
		
		if(!file.exists()) file.mkdirs();

		if(file.isFile()) {
			fp.add(new FilePath(file.getName(),file.length(),"文件",getCreateTime(file), getModifiedTime(path)));
		}else {
			if(file.listFiles() != null) {
				
				File[] fs = file.listFiles();
			
				for (File file1 : fs) {
					if(file1.isFile()) {
						//文件
						fp.add(new FilePath(file1.getName(), file1.length(),"文件",getCreateTime(file1), getModifiedTime(file1.toString())));
					}else {
						fp.add(new FilePath(file1.getName(), FileUtils.sizeOfDirectory(file1),"目录",getCreateTime(file1), getModifiedTime(file1.toString())));
					}
				}
			}
		}
		session.setAttribute("path", path);
		session.setAttribute("fp1", fp);
		request.setAttribute("fp", fp);
	try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/2-17wangpan?user=root&password=123&useSSL=false");
			PreparedStatement ps = connection.prepareStatement("select id,name,user_name,size,create_time,update_time,header_img from resource where user_name like '"+name+"' ");
			ResultSet rs = ps.executeQuery();
			List<Resource>resource = new ArrayList<>();
			while(rs.next()) {
				Resource re = new Resource(rs.getInt("id"),rs.getString("name"),rs.getString("user_name"),
				rs.getDate("create_time"),rs.getDate("update_time"),rs.getInt("size"),rs.getString("header_img"));
				resource.add(re);
			}
			
			rs.close();
			ps.close();
			connection.close();
			request.setAttribute("resource", resource);
			request.getRequestDispatcher("/interface/myresource.jsp").forward(request, response);

		}catch (Exception e) {	
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getParameter("path");
		int index = path.lastIndexOf("file");

		path = path.substring(index+5);
		System.out.println(path);

		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        Part headerImg = request.getPart("headerImg");
    
        if(headerImg.getSize()==0) {
        	response.sendRedirect("/MyResource");
        	return;
        }

    	save(request,headerImg,path);

		response.sendRedirect("/MyResource");
	}
	private void save(HttpServletRequest req, Part part,String path) throws IOException {
		
		InputStream is = part.getInputStream(); // 流[文件]

		String uplaodsDir = req.getServletContext().getRealPath("/uploads/file"+"/"+path); // 保存上传文件的路径 
		System.out.println(uplaodsDir);
		String contentDisposition = part.getHeader("content-disposition");
		String fileName = contentDisposition.split("; ")[2].split("=")[1].replaceAll("\"", "");
		File file = new File(uplaodsDir, UUID.randomUUID().toString() + "-" + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] bytes = new byte[1024 * 1024 *10]; // 10M
		int length = -1;
		while ((length = is.read(bytes)) != -1) {
			fos.write(bytes, 0, length);
		}
		fos.close();
	}
	private String getCreateTime(File file) throws IOException{  	  
		  BasicFileAttributes bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		  return sdf.format(new Date(bfa.creationTime().toMillis()));  
	}
	public static String getModifiedTime(String path){  
      File f = new File(path);              
      Calendar cal = Calendar.getInstance();  
      long time = f.lastModified();  
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");         
      cal.setTimeInMillis(time);    
      return formatter.format(cal.getTime());
  }
}
