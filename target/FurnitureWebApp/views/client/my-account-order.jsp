<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="orders" type="java.util.ArrayList<models.view_models.orders.OrderViewModel>" scope="request"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" >
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
                        <li class="account__menu--list active"><a href="<%=request.getContextPath()%>/my-account">Đơn hàng của tôi</a></li>
                        <li class="account__menu--list"><a href="<%=request.getContextPath()%>/my-account?info=true">Cập nhật thông tin</a></li>
                        <li class="account__menu--list"><a href="<%=request.getContextPath()%>/wish-list">Danh sách yêu thích</a></li>
                        <li class="account__menu--list"><a href="<%=request.getContextPath()%>/signout">Đăng xuất</a></li>
                    </ul>
                </div>
                <div class="account__wrapper">
                    <div class="account__content">
                        <h3 class="account__content--title mb-20">Đơn hàng của tôi</h3>
                        <div class="account__table--area">
                            <table class="account__table">
                                <thead class="account__table--header">
                                <tr class="account__table--header__child">
                                    <th class="account__table--header__child--items">Đơn hàng</th>
                                    <th class="account__table--header__child--items">Ngày đặt</th>
                                    <th class="account__table--header__child--items">Ngày thanh toán</th>
                                    <th class="account__table--header__child--items">Trạng thái thanh toán</th>
                                    <th class="account__table--header__child--items">Trạng thái đơn hàng</th>
                                    <th class="account__table--header__child--items">Tổng sản phẩm</th>
                                    <th class="account__table--header__child--items">Tổng tiền</th>
                                    <th class="account__table--header__child--items">Action</th>
                                </tr>
                                </thead>
                                <tbody class="account__table--body mobile__none">
                                <c:forEach var="o" items="${orders}">
                                    <tr class="account__table--body__child">
                                        <td class="account__table--body__child--items">#${o.orderId}</td>
                                        <td class="account__table--body__child--items">${o.dateCreated}</td>
                                        <td class="account__table--body__child--items">${o.dateDone}</td>
                                        <td class="account__table--body__child--items">${o.paymentMethod}</td>
                                        <td class="account__table--body__child--items ${o.statusClass}">${o.statusCode}</td>
                                        <td class="account__table--body__child--items">${o.totalItem}</td>
                                        <td class="account__table--body__child--items">${o.totalPrice}</td>
                                        <td class="account__table--body__child--items"><a href="<%=request.getContextPath()%>/my-account/order/detail?orderId=${o.orderId}">Xem chi tiết</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tbody class="account__table--body mobile__block">
                                <c:forEach var="o" items="${orders}">
                                    <tr class="account__table--body__child">
                                        <td class="account__table--body__child--items">
                                            <strong>Đơn hàng</strong>
                                            <span>#${o.orderId}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Ngày đặt</strong>
                                            <span>${o.dateCreated}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Ngày thanh toán</strong>
                                            <span>${o.dateDone}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Trạng thái thanh toán</strong>
                                            <span>${o.paymentMethod}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Trạng thái đơn hàng</strong>
                                            <span class="${o.statusClass}">${o.statusCode}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Tổng sản phẩm</strong>
                                            <span>${o.totalItem}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Tổng tiền</strong>
                                            <span>${o.totalPrice}</span>
                                        </td>
                                        <td class="account__table--body__child--items">
                                            <strong>Chi tiết</strong>
                                            <a href="<%=request.getContextPath()%>/my-account/order/detail?orderId=${o.orderId}">Xem chi tiết</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- my account section end -->
</main>

<jsp:include page="/views/client/common/footer.jsp" />

<jsp:include page="/views/client/common/common_js.jsp"/>
</body>
</html>
