/**
 * 
 */
package wangpang1801.zsg;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
/**
 * @author Topin@JSC
 *
 */
@WebServlet("/Download")
public class Download extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String header_img = req.getParameter("header_img");
		File file = new File(header_img);
		if(!file.isFile()) {	
			if(FileUtils.sizeOfDirectory(file)==0) {
				resp.sendRedirect("DbankList");
				return;
			}
		}
		doDownload(file,header_img,resp);
	}
	private void doDownload(File file,String header_img,HttpServletResponse resp) throws IOException {
		if(file.isFile()) {		
			String suffixName =header_img.substring(header_img.lastIndexOf("."));
			int index = header_img.lastIndexOf("-");
			int index1 = header_img.lastIndexOf(".");
			String headName = header_img.substring(index+1, index1);	
			
			// 响应头部
			// 设置响应的类型为下载类型
			//	resp.setContentType("text/html");
			resp.setContentType("application/octem-stream");
			// 设置 文件大小
			resp.setContentLength((int) file.length());
			// 设置下载的文件名
			resp.setHeader("Content-Disposition", "attachment; filename=\""+URLEncoder.encode(headName, "UTF-8")+suffixName);
			// 响应文件
			// 用户下载
			ServletOutputStream os = resp.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[1024*1024*4];
			int length = -1;
			while((length = fis.read(bytes)) != -1){
				os.write(bytes, 0, length);
			}
			fis.close();
		}else {
			
			String s1[]= header_img.split("file/");
			
			String	path1 =  s1[s1.length-1];
			
			System.out.println("headerimg:   "+header_img);
			
			File file2 = new File("F:/谷歌下载地址/"+ path1);
			
			System.out.println("file2:   "+file2);
			
			if(!file2.exists()) file2.mkdirs();

			String[] files = file.list();
			
			String temp = header_img;
			
			for(String f : files) {		
				
				String temp1 = temp;
				
				header_img = temp1+"/"+f;
				
				System.out.println("for"+header_img);

				doDownload(new File(header_img),header_img,resp);
				
				
			}
			
		}
		
	}  

}
