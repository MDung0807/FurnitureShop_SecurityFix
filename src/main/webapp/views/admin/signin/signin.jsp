<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="Ekka - Admin Dashboard">

    <title>Ekka - Admin Dashboard eCommerce</title>
    <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="sign-inup" id="body">
<div class="container d-flex align-items-center justify-content-center form-height-login pt-24px pb-24px">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-10">
            <div class="card">
                <div class="card-header bg-primary">
                    <div class="ec-brand">
                        <a href="<%=request.getContextPath()%>/admin/home" title="Ekka">
                            <img class="ec-brand-icon" src="<%=request.getContextPath()%>/assets/admin/img/logo/logo-login.png" alt="" />
                        </a>
                    </div>
                </div>
                <div class="card-body p-5">
                    <h4 class="text-dark mb-5">Sign In</h4>

                    <form action="<%=request.getContextPath()%>/admin/login" method="post" id="form-login">
                        <div class="row">
                            <div class="form-group col-md-12 mb-4">
                                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                            </div>

                            <div class="form-group col-md-12 ">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                            </div>

                            <div class="col-md-12">
                                <p id="authenticationValidateMessage" class="mt-3 mb-3"></p>
                                <button type="submit" class="btn btn-primary btn-block mb-4">Sign In</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
    $('#form-login').submit(function (e){
        validateForm(e)
    })
    function validateForm(e){
        let noError = true;
        e.preventDefault()
        let url = `<%=request.getContextPath()%>` + `/admin/login`
        $.ajax({
            url: url,
            method: "POST",
            data: {
                'username': $('#username').val(),
                'password': $('#password').val()
            },
            async: false,
            success: function (data){
                console.log(data)
                let str = data.toString()
                if(str.length <= 20) {
                    if (str.includes('error')) {
                        $('#authenticationValidateMessage').html('Username/password không chính xác').css('color', 'red')
                        noError = false;
                    } else if (str.includes("banned")) {
                        $('#authenticationValidateMessage').html('Tài khoản bị cấm hoạt động').css('color', 'red')
                        noError = false;
                    } else if (str.includes("unauthorize")) {
                        $('#authenticationValidateMessage').html('Tài khoản không có quyền truy cập').css('color', 'red')
                        noError = false;
                    } else if (str.includes("unconfirm")) {
                        $('#authenticationValidateMessage').html('Tài khoản chưa xác nhận mail').css('color', 'red')
                        noError = false;
                    }
                }
            },
            error: function (error){
                noError = false;
            }
        })
        if(noError){
            $('#form-login').unbind('submit').submit();
        }
    }
</script>
</body>
</html>
