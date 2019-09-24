package wangpang1801.zsg;

import java.io.File;
import java.io.IOException;
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
import org.apache.commons.io.FileUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DbankList
 */
@WebServlet("/DbankList")
public class DbankList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		String path = (String) session.getAttribute("path");
		

		File file = new File(path);
		List<FilePath>fp = new ArrayList<>();
		
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();

		
			if(file.isFile()) {
				fp.add(new FilePath(file.getName(),file.length(),"文件",getCreateTime(file), getModifiedTime(path)));
			}else {
				if(file.listFiles() != null) {
					
					File[] fs = file.listFiles();
				
					for (File file1 : fs) {
						if(file1.isFile()) {
							//文件
							int index = file1.getName().lastIndexOf("-");
							String name = file1.getName().substring(index+1);
							fp.add(new FilePath(name, file1.length(),"文件",getCreateTime(file1), getModifiedTime(file1.toString())));
						}else {
							fp.add(new FilePath(file1.getName(), FileUtils.sizeOfDirectory(file1),"目录",getCreateTime(file1), getModifiedTime(file1.toString())));
						}
					}
				}
			}
	
		session.setAttribute("path", path);
		request.setAttribute("fp", fp);
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/2-17wangpan?user=root&password=123&useSSL=false");
			PreparedStatement ps = connection.prepareStatement("select id,name,user_name,size,header_img,create_time,update_time from resource");
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
		
			request.getRequestDispatcher("/interface/dbanklist.jsp").forward(request, response);
	
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

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
