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
                        <h1 class="breadcrumb__content--title text-white mb-10">Đăng nhập</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Đăng nhập</span></li>
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
            <form id="form-login" action="<%=request.getContextPath()%>/signin" method="post">
                <div class="login__section--inner">
                    <div class="row justify-content-center">
                        <div class="col col-8">
                            <div class="account__login">
                                <div class="account__login--header mb-25">
                                    <h3 class="account__login--header__title mb-10">Đăng nhập</h3>
                                    <p class="account__login--header__desc">Đăng nhập nếu ban đã có tài khoản</p>
                                </div>
                                <div class="account__login--inner">
                                    <label>
                                        <input class="account__login--input" placeholder="Username" type="text" id="username" name="username" required>
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Password" type="password" id="password" name="password" required>
                                    </label>

                                    <p id="authenticationValidateMessage" class="mt-3 mb-3"></p>
                                    <button class="account__login--btn primary__btn" type="submit">Đăng nhập</button>
                                    <div class="account__login--divide">
                                        <span class="account__login--divide__text">Hoặc</span>
                                    </div>
                                    <div class="account__social d-flex justify-content-center mb-15">
                                        <a class="account__social--link primary__btn" href="${urlGoogleLogin}">Đăng nhập với Google</a>
                                    </div>
                                    <div class="account__login--divide">
                                        <span class="account__login--divide__text">Hoặc</span>
                                    </div>
                                    <div class="account__social d-flex justify-content-center mb-15">
                                        <a class="account__social--link twitter" href="<%=request.getContextPath()%>/admin/login">Đăng nhập vào trang quản trị</a>
                                    </div>

                                    <div class="account__login--divide">
                                        <span class="account__login--divide__text">Hoặc</span>
                                    </div>
                                    <p class="account__login--signup__text">Bạn chưa có tài khoản? <a class="text__secondary" href="<%=request.getContextPath()%>/register">Đăng ký ngay</a></p>
                                    <p class="account__login--signup__text">Bạn không đăng nhập được? <a class="text__secondary" href="<%=request.getContextPath()%>/forgot-password">Quên mật khẩu</a></p>
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
            <h3 class="modal-header border-bottom-0" id="error-message">Thao tác bị lỗi, vui lòng thực hiện lại</h3>
        </div>
    </div>
    <div class="modal" id="modal-register" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Đăng kí tài khoản thành công</h3>
        </div>
    </div>
    <div class="modal" id="modal-forgot-password" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Vui lòng kiểm tra mail để biết thông tin chi tiết về mật khẩu mới</h3>
        </div>
    </div>
    <div class="modal" id="modal-banned" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Tài khoản của bạn bị cấm hoạt động, vui lòng liên hệ quản trị viên</h3>
        </div>
    </div>
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script>
    $(window).on('load', function() {
        let error_modal = document.getElementById("error-message");
        let show = true;
        if(${error != null}){
            error_modal.innerText = "Thao tác bị lỗi, vui lòng thực hiện lại"
        }
        else if(window.location.href.includes("error")){
            error_modal.innerText = "Thao tác bị lỗi, vui lòng thực hiện lại"
        }
        else if(window.location.href.includes("banned")){
            error_modal.innerText = "Tài khoản của bạn bị cấm hoạt động, vui lòng liên hệ quản trị viên"
        }
        else if(window.location.href.includes("register")){
            error_modal.innerText = "Đăng kí tài khoản thành công, vui lòng kiểm tra Email để xác nhận"
        }
        else if(window.location.href.includes("forgot-password")){
            error_modal.innerText = "Vui lòng kiểm tra mail để biết thông tin chi tiết về mật khẩu mới"
        }
        else if(window.location.href.includes("unconfirm")){
            error_modal.innerText = "Tài khoản chưa được xác nhận, vui lòng kiểm tra email"
        }
        else if(window.location.href.includes("token-expired")){
            error_modal.innerText = "Token hết hạn. Một token mới đã được gửi lại đến mail của bạn. Vui lòng kiểm tra"
        }
        else if(window.location.href.includes("token-error")){
            error_modal.innerText = "Lỗi khi xác nhận tài khoản, vui lòng thử lại"
        }
        else if(window.location.href.includes("token-verify-success")){
            error_modal.innerText = "Xác nhận tài khoản thành công"
        }
        else{
            show = false
        }
        if(show){
            document.getElementById("modal-error").classList.add('is-visible')
        }
    });
    $('#form-login').submit(function (e){
        validateForm(e)
    })
    function validateForm(e){
        let noError = true;
        e.preventDefault()
        let url = `<%=request.getContextPath()%>` + `/signin`
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
