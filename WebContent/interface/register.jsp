<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
   <meta charset="utf-8"> 
   <title>注册</title>
   <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script type="text/javascript">
		function refer(){
				var name = document.getElementById("name").value
				if(name == "" || name == null || name == undefined){
					alert("请输入正确的用户名！");
					return;
				}
				var passwd = document.getElementById("passwd").value
				if(passwd == "" || passwd == null || passwd == undefined){
					alert("请输入密码！");
					return;
				}
				var img = document.getElementById("headerImg").value
				if(img == "" || img == null || img == undefined){
					alert("设置头像");
					return;
				}
			}
		</script>
</head>
<body>
	<%
		String msg = (String) request.getAttribute("answer");
		if (msg != null) {
	%>
		<fieldset id="">
			<legend>${answer}</legend>
		</fieldset>
	<%
		}	
	%>
<form action="/Register" method="post" enctype="multipart/form-data">
    <div class="container">
        <div class="form row">
            <div class="form-horizontal col-md-offset-3" id="login_form">
                <h3 class="form-title">用户注册</h3>
                <div class="col-md-9">
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                       <input value="${name}" class="form-control required" type="text" placeholder="请输入用户名"  id = "name" name="name" autofocus="autofocus" maxlength="20"/>
                    </div>
                    <div class="form-group">
                            <i class="fa fa-lock fa-lg"></i>
                          <input value="${passwd}" class="form-control required" type="password" placeholder="请输入密码"  name="passwd" id = "passwd"/>
                    </div>
					
                    <div class="form-group">
						设置照片
						<input  type="file" name="headerImg" id="headerImg" accept="image/*" value="" onchange="preview(this)"/>
						<img id="pre" width="200" src=""/>
						<a href="/Login">返回登录</a>
                    </div>
					
                    <div class="form-group col-md-offset-9">
                        <button onclick="refer()" type="submit" class="btn btn-success pull-right" name="submit">注册用户</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
		<script>
			var pre = document.getElementById("pre");
			function preview(ele){
				console.dir(ele)
				var file = ele.files[0];
				var fr = new FileReader();
				fr.readAsDataURL(file);
				fr.onload = function(res){
				console.info(res.target.result)
					pre.src = res.target.result;
				}
			
			}
		</script>
		
</body>
</html>

