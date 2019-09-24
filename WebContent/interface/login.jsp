<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
   <meta charset="utf-8"> 
   <title>登录界面</title>
   <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
    <link rel="stylesheet" href="css/style.css">
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
			}
		</script>
</head>
<body>
<%
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
	%>
		<fieldset id="">
			<legend>${msg}</legend>
		</fieldset>
	<%
		}	
	%>
	
<form action="/Login" method="post">
    <div class="container">
        <div class="form row">
            <div class="form-horizontal col-md-offset-3" id="login_form">
                <h3 class="form-title">用户登录</h3>
                <div class="col-md-9">
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                       <input class="form-control required" type="text" placeholder="请输入用户名"id = "name"  name="name" autofocus="autofocus" maxlength="20"/>
                    </div>
                    <div class="form-group">
                            <i class="fa fa-lock fa-lg"></i>
                          <input class="form-control required" type="password" placeholder="请输入密码" id = "passwd" name="passwd"/>
                    </div>
					
                    <div class="form-group">
						还没用户？请<a href="/Register">注册</a>
						
						
                        <!-- <label class="checkbox">
                            <input type="checkbox" name="remember" value="1"/>记住密码
                        </label> -->
                    </div>
					
                    <div class="form-group col-md-offset-9">
                        <button onclick="refer()" type="submit" class="btn btn-success pull-right" name="submit">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>

