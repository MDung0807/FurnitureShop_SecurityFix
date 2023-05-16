<%@ page import="utils.constants.USER_GENDER" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="user" type="models.view_models.users.UserViewModel" scope="request"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" >
    <title>Furea - Furniture Shop</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/client/img/favicon.ico">
    <link id="ekka-css" href="<%=request.getContextPath()%>/assets/admin/css/ekka.css" rel="stylesheet" />
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
                        <h1 class="breadcrumb__content--title text-white mb-10">Tài khoản của tôi</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Tài khoản của tôi</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->

    <!-- my account section start -->
    <section class="my__account--section section--padding">
        <div class="container-fluid">
            <div class="my__account--section__inner border-radius-10 d-flex">
                <div class="account__left--sidebar">
                    <h3 class="account__content--title mb-20">Tài khoản của tôi</h3>
                    <ul class="account__menu">
                        <li class="account__menu--list"><a href="<%=request.getContextPath()%>/my-account">Đơn hàng của tôi</a></li>
                        <li class="account__menu--list active"><a href="<%=request.getContextPath()%>/my-account?info=true">Cập nhật thông tin</a></li>
                        <li class="account__menu--list" style="display: block;!important;"><a href="<%=request.getContextPath()%>/wish-list">Danh sách yêu thích</a></li>
                        <li class="account__menu--list"><a href="<%=request.getContextPath()%>/signout">Đăng xuất</a></li>
                    </ul>
                </div>
                <div class="account__wrapper">
                    <div class="account__content">
                        <div class="row">
                            <div class="col-lg-4 col-xl-3">
                                <div class="profile-content-left profile-left-spacing">
                                    <div class="text-center widget-profile px-0 border-0">
                                        <div class="card-img mx-auto rounded-circle">
                                            <img src="data:image/png;base64, ${user.avatar}" alt="user image">
                                        </div>
                                        <div class="card-body">
                                            <h4 class="py-2 text-dark">${user.firstName}<span> ${user.lastName}</span></h4>
                                            <p>${user.email}</p>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-between ">
                                        <div class="text-center pb-4">
                                            <h3 class="text-dark pb-2">${user.totalBought}</h3>
                                            <p>Sản phẩm đã mua</p>
                                        </div>

                                        <div class="text-center pb-4">
                                            <h3 class="text-dark pb-2">${user.totalWishListItem}</h3>
                                            <p>Sản phẩm yêu thích</p>
                                        </div>
                                    </div>

                                    <hr class="w-100">

                                    <div class="contact-info pt-4">
                                        <h3 class="text-dark">Thông tin liên hệ</h3>
                                        <p class="text-dark font-weight-medium pt-24px mb-2">Email</p>
                                        <p>${user.email}</p>
                                        <p class="text-dark font-weight-medium pt-24px mb-2">Điện thoại</p>
                                        <p>${user.phone}</p>
                                        <p class="text-dark font-weight-medium pt-24px mb-2">Ngày sinh</p>
                                        <p>${user.dateOfBirth}</p>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-8 col-xl-9">
                                <div class="profile-content-right profile-right-spacing py-5">
                                    <ul class="nav nav-tabs px-3 px-xl-5 nav-style-border" id="myProfileTab" role="tablist">
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link active" id="profile-tab" data-bs-toggle="tab"
                                                    data-bs-target="#profile" type="button" role="tab"
                                                    aria-controls="profile" aria-selected="true">Profile</button>
                                        </li>
                                    </ul>
                                    <div class="tab-content px-3 px-xl-5" id="myTabContent">
                                        <div class="tab-pane fade show active" id="profile" role="tabpanel"
                                             aria-labelledby="profile-tab">
                                            <div class="tab-widget mt-5">
                                                <form id="form-add" action="<%=request.getContextPath()%>/my-account/edit" enctype="multipart/form-data" method="post">
                                                    <input type="hidden" name="userId" id="userId" value="${user.id}"/>
                                                    <div class="row ec-vendor-uploads mb-4">
                                                        <label class="col-12 col-form-label" for="avatar">Avatar</label>
                                                        <div class="ec-vendor-img-upload">
                                                            <div class="ec-vendor-main-img">
                                                                <div class="thumb-upload-set col-md-12">
                                                                    <div class="thumb-upload">
                                                                        <div class="thumb-edit">
                                                                            <input type='file' id="avatar" name="avatar"
                                                                                   class="ec-image-upload"
                                                                                   accept=".png, .jpg, .jpeg"/>
                                                                            <label for="avatar">
                                                                                <img src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                                     class="svg_img header_svg" alt="edit"/>
                                                                            </label>
                                                                        </div>
                                                                        <div class="thumb-preview ec-preview">
                                                                            <div class="image-thumb-preview">
                                                                                <img class="image-thumb-preview ec-image-preview clear-img"
                                                                                     src="data:image/png;base64, ${user.avatar}"
                                                                                     alt="edit" />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="row mb-2">
                                                        <div class="col-lg-4">
                                                            <div class="form-group">
                                                                <label for="firstName">Họ</label>
                                                                <input type="text" class="form-control" id="firstName"
                                                                       name="firstName" value="${user.firstName}" required>
                                                            </div>
                                                        </div>

                                                        <div class="col-lg-4">
                                                            <div class="form-group">
                                                                <label for="lastName">Tên</label>
                                                                <input type="text" class="form-control" id="lastName"
                                                                       name="lastName" value="${user.lastName}" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4">
                                                            <div class="form-group mb-4">
                                                                <label for="gender">Giới tính</label>
                                                                <select id="gender" name="gender" class="form-select" required>
                                                                    <c:forEach var="g" items="<%=USER_GENDER.Gender%>">
                                                                        <option value="${g.value}" <c:if test="${g.value == user.gender}">selected</c:if>>${g.key}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-2">
                                                        <div class="col-lg-4">
                                                            <div class="form-group mb-4">
                                                                <label for="username">User name</label>
                                                                <input type="text" class="form-control" id="username"
                                                                       name="username" value="${user.username}" required>
                                                                <p class="mt-3" id='userValidateMessage' ></p>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4">
                                                            <div class="form-group mb-4">
                                                                <label for="password">Password</label>
                                                                <input type="password" class="form-control" id="password"
                                                                       name="password" >
                                                                <p class="mt-3" id='passwordValidateMessage'></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-2">
                                                        <div class="col-lg-6">
                                                            <div class="form-group mb-4">
                                                                <label for="email">Email</label>
                                                                <input type="email" class="form-control" id="email"
                                                                       name="email" value="${user.email}" required>
                                                                <p class="mt-3" id='emailValidateMessage'></p>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="form-group">
                                                                <label for="phone">Điện thoại</label>
                                                                <input type="text" class="form-control" id="phone" pattern="[0-9]{10}"
                                                                       name="phone" value="${user.phone}" required>
                                                                <p class="mt-3" id='phoneValidateMessage'></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-2">
                                                        <div class="col-lg-6">
                                                            <div class="form-group mb-4">
                                                                <label for="newPassword">Mật khẩu mới</label>
                                                                <input type="password" class="form-control" id="newPassword"
                                                                       name="newPassword">
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="form-group">
                                                                <label for="confirmPassword">Xác nhận mật khẩu</label>
                                                                <input type="password" class="form-control" id="confirmPassword"
                                                                       name="confirmPassword">
                                                                <p class="mt-3" id='confirmPasswordNotMatch'></p>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="row mb-2">
                                                        <div class="col-lg-6">
                                                            <div class="form-group mb-4">
                                                                <label for="dob">Ngày sinh</label>
                                                                <input type="date" class="form-control" id="dob"
                                                                       name="dob" value="${user.dateOfBirth}" required>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="form-group mb-4">
                                                                <label for="address">Địa chỉ</label>
                                                                <input type="text" class="form-control" id="address" name="address" value="${user.address}" required/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex justify-content-end mt-5">
                                                        <button type="submit" class="btn btn-primary btn-pill">Xác nhận</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="modal" id="modal-error" style="z-index: 100;" data-animation="slideInUp">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal- border-bottom-0">Có lỗi khi cập nhật thông tin, vui lòng thử lại</h3>
        </div>
    </div>
    <!-- my account section end -->
</main>

<jsp:include page="/views/client/common/footer.jsp" />

<jsp:include page="/views/client/common/common_js.jsp"/>

<script src="<%=request.getContextPath()%>/assets/admin/js/ekka.js"></script>
<script>
    $(window).on('load', function() {
        if(${error != null}){
            document.getElementById("modal-error").classList.add('is-visible')
        }
        else if(window.location.href.includes("error")){
            $('#modal-error').modal('show');
        }

    });
    $('#form-add').submit(function (e){
        validateForm(e, `<%=request.getContextPath()%>`)
    })
</script>
<script src="<%=request.getContextPath()%>/assets/admin/js/validate/client/register-validation.js"></script>
</body>
</html>
