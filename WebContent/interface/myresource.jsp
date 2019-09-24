
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="wangpang1801.zsg.Resource"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="wangpang1801.zsg.FilePath"%>
<html>
<head>
   <meta charset="utf-8"> 
   <title>My网盘</title>
   <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<style type="text/css">
		
		.user{
			float:right 
		}

	</style>
	<script type="text/javascript">
		function up_file(){
				var file1 = document.getElementById("file1").value
				if(file1 == "" || file1 == null || file1 == undefined){
					alert("请选择文件");
					return;
				}
				
			}
		function del(){
				alert("删除成功");
		}
		function check(fp){
			var file1 = document.getElementById("file_name").value
			if(file1.trim() == "" || file1 == null || file1 == undefined){
				alert("请输入文件昵称");
				return;
			}
			
		}
		</script>
</head>
<body>
	<%
	
	String path = (String) session.getAttribute("path");
	String user = (String) session.getAttribute("USER");
	String anse = (String) request.getAttribute("anse");
	
	%>
	<div class="user">
	
	<img width="60px" src="${USER_IMG}"  alt="我的照片" />
	<a href="/DbankList5">返回网盘</a>
	
	</div>
	<br>
	<br>
	<br>
	<br>

	
		<h4><%=path%></h4>
	<%
	if(!path.equals("D:/JAVA/1801wangpan/WebContent/uploads/file"+"/"+user)){
		%>
		<a href="/DbankList6?path=<%=path%>">返回上一层</a>
		<%
	}
	%>

	<form action="/MyResource" method="post" enctype="multipart/form-data">
	
	<input type="hidden" name="path" value="<%=path%>">
	<input  type="file" name="headerImg" accept="uploads/file" value="" id ="file1"/>
	<input onclick="up_file()" type ="submit" value="上传" />
	
	</form>
						
	<table class="table table-dark table-hover">
		<tr>
			<th>名称</th>
			<th>path</th>
			<th>大小</th>
			<th>新增日期</th>
			<th>修改日期</th>
			<th>操作</th>
		</tr>
		<%
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat(",###");
			List<Resource> resource = (List<Resource>) request.getAttribute("resource");
			List<FilePath> fp = (List<FilePath>) request.getAttribute("fp");
		
			for (FilePath f : fp) {
				
				if(f.getType().equals("目录")){
		%>	
				<tr>	
				
				<th><a href="/DbankList4?path=<%=path%>/<%=f.getName()%>"><img class="icon" src="/uploads/pricate/folder.gif"><%=f.getName()%></a></th>		
				<th><%=path%></th>
				<th><%=df.format(f.getSize()/1024)+" KB"%></th>
				<th><%=f.getCreate_time()%></th>
				<th><%=f.getUpdate_time()%></th>
				<th><a onclick="del()" class="btn btn-warning btn-xs" href="/FileDelete?name=<%=f.getName()%>">删除</a></th>
		<%
				}else{
					int index = f.getName().lastIndexOf("-");
					String name = f.getName().substring(index+1);
		%>			
				<tr>
				<th><%=name%></th>
				<th><%=path+f.getName()%></th>
				<th><%=df.format(f.getSize()/1024)+" KB"%></th>
				<th><%=f.getCreate_time()%></th>
				<th><%=f.getCreate_time()%></th>
				<th><a onclick="del()" class="btn btn-warning btn-xs" href="/FileDelete?name=<%=f.getName()%>">删除</a></th>
			</tr>
		<%		
				}
		%>
			
		<%
			}
		%>
		</table>
		
		<form action="/AddFile" method="post">
			
			<input type = "text" value ="请输入文件昵称" id="file_name" name = "filename"></input>
			<input type="hidden" name="path" value="<%=path%>">
		
			
			<input type ="submit" value ="新建目录 " onclick="check(<%=fp%>)"></input>
			
		</form>
		
	
</body>
</html>

