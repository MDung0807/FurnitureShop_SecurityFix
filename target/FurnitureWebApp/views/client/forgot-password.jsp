<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Furea - Furniture Shop</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/client/img/favicon.ico">
    <jsp:include page="/views/client/common/common_css.jsp"/>
</head>
<body>
<jsp:include page="/views/client/common/header.jsp"/>
<main class="main__content_wrapper">
    <!-- Start breadcrumb section -->
    <section class="breadcrumb__section breadcrumb__bg">
        <div class="container">
            <div class="row row-cols-1">
                <div class="col">
                    <div class="breadcrumb__content">
                        <h1 class="breadcrumb__content--title text-white mb-10">Quên mật khẩu</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Quên mật khẩu</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->
    <!-- Start login section  -->
    <div class="login__section section--padding">
        <div class="container">
            <form id="form-forgot-password" action="<%=request.getContextPath()%>/forgot-password" method="post">
                <div class="login__section--inner">
                    <div class="row justify-content-center">
                        <div class="col col-8">
                            <div class="account__login">
                                <div class="account__login--header mb-25">
                                    <h3 class="account__login--header__title mb-10">Quên mật khẩu</h3>
                                    <p class="account__login--header__desc">Vui lòng nhập email đăng ký tài khoản</p>
                                </div>
                                <div class="account__login--inner">
                                    <label>
                                        <input class="account__login--input" placeholder="Email" type="email" id="email" name="email" required>
                                    </label>
                                    <p id="authenticationValidateMessage" class="mt-3 mb-3"></p>
                                    <button class="account__login--btn primary__btn" type="submit">Xác nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- End login section  -->
    <div class="modal" id="modal-error" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Thao tác bị lỗi, vui lòng thực hiện lại</h3>
        </div>
    </div>
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script>
    <%--$(window).on('load', function() {--%>
    <%--    if(${error != null}){--%>
    <%--        document.getElementById("modal-error").classList.add('is-visible')--%>
    <%--    }--%>
    <%--    else if(window.location.href.includes("error")){--%>
    <%--        document.getElementById("modal-error").classList.add('is-visible')--%>
    <%--    }--%>
    <%--    else if(window.location.href.includes("banned")){--%>
    <%--        document.getElementById("modal-banned").classList.add('is-visible')--%>
    <%--    }else if(window.location.href.includes("register")){--%>
    <%--        document.getElementById("modal-register").classList.add('is-visible')--%>
    <%--    }--%>
    <%--});--%>
    $('#form-forgot-password').submit(function (e){
        validateForm(e)
    })
    function validateForm(e){
        let noError = true;
        e.preventDefault()
        let url = `<%=request.getContextPath()%>` + `/email-check`
        $.ajax({
            url: url,
            method: "GET",
            data: {
                'email': $('#email').val()
            },
            async: false,
            success: function (data){
                console.log(data)
                let str = data.toString()
                if(str.includes('error') && str.length <=10){
                    document.getElementById("modal-error").classList.add('is-visible')
                    noError = false;
                }
                else if(str.includes('non-exist') && str.length <= 15){
                    $('#authenticationValidateMessage').html('Email không tồn tại').css('color','red')
                    noError = false;
                }
            },
            error: function (error){
                noError = false;
            }
        })
        if(noError){
            $('#form-forgot-password').unbind('submit').submit();
        }
    }
</script>
</body>
</html>
