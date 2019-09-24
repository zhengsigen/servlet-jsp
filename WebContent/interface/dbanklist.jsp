<%@page import="wangpang1801.zsg.FilePath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="wangpang1801.zsg.Resource"%>
<%@page import="wangpang1801.zsg.FilePath"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.io.File"%>
<%@page import="javax.servlet.http.HttpSession"%>


<html>
<head>
   <meta charset="utf-8"> 
   <title>1801网盘</title>
   <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<style type="text/css">
		
		.user{
			float:right 
		}

	</style>
</head>

<body>
	<div class="user">
	<img width="60px" src="${USER_IMG}"  alt="我的照片" />
	<a href="/MyResource">${USER} 的网盘</a>&nbsp|&nbsp
	<a href="/Exit">退出</a>&nbsp&nbsp&nbsp&nbsp
	</div>
	<br>
	<br>
	<br>
	<br>
	

	<%
		HttpSession session1 = request.getSession();
		String path = (String) session1.getAttribute("path");
		
		
	%>
	
		<h4><%=path%></h4>
		
	<%
	if(!path.equals("D:/JAVA/1801wangpan/WebContent/uploads/file")){
		
		%>
		<a href="/DbankList2?path=<%=path%>">返回上一层</a>
		<%
	}
		
	%>	
				<%!
				  int choose =0;
				%>
				<%
					choose++;
				%>
		<table class="table table-dark table-hover">
		<tr>
			<th><a href = "/DbankList_a?i=<%="名称"%>&number=<%=choose%>">名称</th>
			<th>path</th>
	
			<th><a href = "/DbankList_a?i=<%="大小"%>&number=<%=choose%>">大小</th>
			<th><a href = "/DbankList_a?i=<%="新增日期"%>&number=<%=choose%>">新增日期</th>
			<th><a href = "/DbankList_a?i=<%="修改日期"%>&number=<%=choose%>">修改日期</th>
			<th>操作</th>
			
		</tr>
		<%
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat(",###");
			
			List<FilePath> fp = (List<FilePath>) request.getAttribute("fp");
			for (FilePath f : fp) {
				
				if(f.getType().equals("目录")){
		%>	
				<tr>	
				
				<th><a href="/DbankList3?path=<%=path%>/<%=f.getName()%>"><img class="icon" src="/uploads/pricate/folder.gif"><%=f.getName()%></a></th>		
				<th><%=path%></th>
				<th><%=df.format(f.getSize()/1024)+" KB"%></th>
				<th><%=f.getCreate_time()%></th>
				<th><%=f.getUpdate_time()%></th>
				<th><a class="btn btn-success btn-xs" href="/Download?header_img=<%=path+"/"+f.getName()%>">下载</a></th>
		<%
				}else{
		%>			
				<tr>
				<th><%=f.getName()%></th>
				<th><%=path+f.getName()%></th>
				<th><%=df.format(f.getSize()/1024)+" KB"%></th>
				<th><%=f.getCreate_time()%></th>
				<th><%=f.getCreate_time()%></th>
				<th><a class="btn btn-success btn-xs" href="/Download?header_img=<%=path+"/"+f.getName()%>">下载</a></th>
			</tr>
		<%		
				}
		%>
			
		<%
			}
		%>
		</table>
		

</body>

</html>

