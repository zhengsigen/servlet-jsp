package wangpang1801.zsg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
@WebServlet("/DbankList_a")
public class DbankList_a extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		String path = (String) session.getAttribute("path");
		String number = request.getParameter("i");
	
		
		File file = new File(path);
		List<FilePath>fp = new ArrayList<>();
		
		if(file.isFile()) {
			fp.add(new FilePath(file.getName(),file.length(),"文件",getCreateTime(file), getModifiedTime(path)));
		}else {
			if(file.listFiles() != null) {
				
				File[] fs = file.listFiles();
				
				for (File file1 : fs) {
					if(file1.isFile()) {
						//文件
						int index = file1.getName().lastIndexOf("-");
						String name1 = file1.getName().substring(index+1);
						fp.add(new FilePath(name1, file1.length(),"文件",getCreateTime(file1), getModifiedTime(file1.toString())));
					}else {
						fp.add(new FilePath(file1.getName(), FileUtils.sizeOfDirectory(file1),"目录",getCreateTime(file1), getModifiedTime(file1.toString())));
					}
				}
			}
		}
		
		Comparator<FilePath> nameComparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
				
			
				return +o1.getName().compareToIgnoreCase(o2.getName()); 
			}
		};
		Comparator<FilePath> name1Comparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
				
			
				return -o1.getName().compareToIgnoreCase(o2.getName()); 
			}
		};
		Comparator<FilePath> sizeComparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
				
				return (int) (o1.getSize()-o2.getSize()); 
			}
		};
		Comparator<FilePath> size1Comparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
				//
				return (int) (o2.getSize()-o1.getSize()); 
			}
		};
		
		Comparator<FilePath> update_timeComparator =new Comparator<FilePath>() {

			public int compare(FilePath o1, FilePath o2) {
				
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				
				Date date1 =null;
				try {
					date1 = format1.parse(o1.getUpdate_time());
				} catch (ParseException e) {}
				
				Date date2 =null;
				try {
					date2 = format1.parse(o2.getUpdate_time());
				} catch (ParseException e) {}
		
				return date1.after(date2)? 1:-1;
				
			}
		};
		Comparator<FilePath> update_time1Comparator =new Comparator<FilePath>() {

			public int compare(FilePath o1, FilePath o2) {
				
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				
				Date date1 =null;
				try {
					date1 = format1.parse(o1.getUpdate_time());
				} catch (ParseException e) {}
				
				Date date2 =null;
				try {
					date2 = format1.parse(o2.getUpdate_time());
				} catch (ParseException e) {}
		
				return date1.after(date2)? -1:1;
				
			}
		};
		
		Comparator<FilePath> create_timeComparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
	
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				
				Date date1 =null;
				try {
					date1 = format1.parse(o1.getCreate_time());
				} catch (ParseException e) {}
				
				Date date2 =null;
				try {
					date2 = format1.parse(o2.getCreate_time());
				} catch (ParseException e) {}
				
				return date1.after(date2)? 1:-1;
			}
		};
		Comparator<FilePath> create_time1Comparator =new Comparator<FilePath>() {
			
			public int compare(FilePath o1, FilePath o2) {
	
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				
				Date date1 =null;
				try {
					date1 = format1.parse(o1.getCreate_time());
				} catch (ParseException e) {}
				
				Date date2 =null;
				try {
					date2 = format1.parse(o2.getCreate_time());
				} catch (ParseException e) {}
				
				return date1.after(date2)? -1:1;
			}
		};
		if(number.equals("大小")) {
			int size =  Integer.valueOf(request.getParameter("number"));
			
			if(size%2!=0) {
				Collections.sort(fp,sizeComparator); 
			}else {
				Collections.sort(fp,size1Comparator); 
			}
		}
		if(number.equals("名称")) {
			int name = Integer.valueOf(request.getParameter("number"));
			
			if(name%2!=0) {
				Collections.sort(fp,nameComparator); 
			}else {
				Collections.sort(fp,name1Comparator); 
			}
			
		}
		if(number.equals("新增日期")) {
			int create =  Integer.valueOf(request.getParameter("number"));
			
			if(create%2!=0) {
				Collections.sort(fp,create_timeComparator); 
			}else {
				Collections.sort(fp,create_time1Comparator); 
			}
		}
		if(number.equals("修改日期")) {
			int update = Integer.valueOf(request.getParameter("number"));
			if(update%2!=0) {
				Collections.sort(fp,update_timeComparator); 
			}else {
				Collections.sort(fp,update_time1Comparator); 
			}
		}

		session.setAttribute("path", path);
		request.setAttribute("fp", fp);
		request.getRequestDispatcher("/interface/dbanklist.jsp").forward(request, response);
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
