<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>表具管理系统</title>
        <link rel="stylesheet" href="assets/materialize/fonts/material-icons.min.css"/>
        <link type="text/css" rel="stylesheet" href="assets/materialize/css/materialize.min.css" media="screen"/>
        <link type="text/css" rel="stylesheet" href="assets/fonts/ionicons.min.css"/>
        <link type="text/css" rel="stylesheet" href="assets/custom/css/common.css"/>
        <link type="text/css" rel="stylesheet" href="assets/custom/css/login.css"/>
        <script type="text/javascript" src="assets/materialize/js/jquery.min.js"></script>
        <script type="text/javascript" src="assets/materialize/js/materialize.min.js"></script>
        <script type="text/javascript" src="assets/custom/js/common.js"></script>
        <script type="text/javascript" src="assets/custom/js/login.js"></script>
    </head>
    <body>
        <form class="login-container" action="login" method="post">
            <!--<h1 class="login-title">登录</h1>-->
            <div class="illustration">
                <i class="ion-ios-navigate"></i>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <i class="material-icons prefix">account_circle</i>
                    <input type="text" id="username" name="username" class="validate" required pattern="^[a-zA-Z]\w{3,15}$" autocomplete="off"/> 
                    <label for="username" class="white-text">用户名</label>
                    <span class="helper-text white-text" data-error="字段长度为4-16位，首位必须为字母" data-success="验证通过"></span>
                </div>           
                <div class="input-field col s12">
                    <i class="material-icons prefix">locker</i>
                    <input type="password" id="password" name="password" class="validate" required pattern="\w{4,16}" autocomplete="off"/>
                    <label for="password" class="white-text">密码</label>
                    <span class="helper-text white-text" data-error="字段长度为4-16位" data-success="验证通过"></span>
                </div>
                <!--<a class="btn waves-effect waves-light login-btn">登录</a>-->
            </div>
            <input type="submit" class="btn waves-effect waves-light login-btn" value="登录"/>
        </form>
    </body>
</html>