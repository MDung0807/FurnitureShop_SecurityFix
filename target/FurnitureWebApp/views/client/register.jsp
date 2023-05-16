<%@ page import="utils.constants.USER_GENDER" %>
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
                        <h1 class="breadcrumb__content--title text-white mb-10">Login</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Đăng ký</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->
    <!-- Start register section  -->
    <div class="login__section section--padding">
        <div class="container">
            <form id="form-add" action="<%=request.getContextPath()%>/register" enctype="multipart/form-data" method="post">
                <div class="login__section--inner">
                    <div class="row justify-content-center">
                        <div class="col col-8">
                            <div class="account__login register">
                                <div class="account__login--header mb-25">
                                    <h3 class="account__login--header__title mb-10">Tạo tài khoản</h3>
                                    <p class="account__login--header__desc">Đăng kí tài khoản nếu bạn là khách hàng mới</p>
                                </div>
                                <div class="account__login--inner">
                                    <label>
                                        Avatar: <input class="mb-15" placeholder="Avatar" accept=".png, .jpg, .jpeg" name="avatar" id="avatar" required type="file">
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Họ" name="firstName" id="firstName" value="${googleUser.family_name}" required type="text">
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Tên" name="lastName" id="lastName" value="${googleUser.given_name}" required type="text">
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Email" name="email" id="email" value="${googleUser.email}" <c:if test="${googleUser != null}">readonly</c:if> required type="email">
                                        <p id="emailValidateMessage"></p>
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Số điện thoại" id="phone" name="phone" required pattern="[0-9]{10}" type="text">
                                        <p id="phoneValidateMessage"></p>
                                    </label>
                                    <label>
                                        Ngày sinh: <input class="account__login--input" name="dob" id="dob" required type="date">
                                    </label>
                                    <label>Giới tính:
                                        <select class="account__login--input" id="gender" name="gender" required title="Vui lòng chọn giới tính">
                                            <c:forEach var="g" items="<%=USER_GENDER.Gender%>">
                                                <option value="${g.value}">${g.key}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Địa chỉ" name="address" id="address" required type="text">
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Username" id="username" name="username" required type="text">
                                        <p id="userValidateMessage"></p>
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Password" id="password" name="password" required type="password">
                                    </label>
                                    <label>
                                        <input class="account__login--input" placeholder="Confirm Password" id="confirmPassword" name="confirmPassword" required type="password">
                                        <p id="confirmPasswordNotMatch"></p>
                                    </label>
                                    <label>
                                        <button class="account__login--btn primary__btn mb-10" type="submit">Đăng ký</button>
                                    </label>
                                    <p class="account__login--signup__text">Bạn đã có tài khoản? <a class="text__secondary" href="<%=request.getContextPath()%>/signin">Đăng nhập ngay</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- End register section  -->
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script>
    $('#form-add').submit(function (e){
        validateForm(e, `<%=request.getContextPath()%>`)
    })
</script>
<script src="<%=request.getContextPath()%>/assets/admin/js/validate/client/register-validation.js"></script>
</body>
</html>
