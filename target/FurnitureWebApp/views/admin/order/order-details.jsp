<%@ page import="utils.HtmlClassUtils" %>
<%@ page import="utils.constants.ORDER_STATUS" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="order" scope="request" class="models.view_models.orders.OrderViewModel"/>
<jsp:useBean id="orderItems" scope="request" type="java.util.ArrayList<models.view_models.order_items.OrderItemViewModel>"/>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="Ekka - Admin Dashboard">

    <title>Ekka - Admin Dashboard eCommerce</title>
    <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-dark ec-header-light" id="body">
<div class="wrapper">
    <jsp:include page="/views/admin/common/sidebar.jsp"/>
    <div class="ec-page-wrapper" >
        <jsp:include page="/views/admin/common/header.jsp"/>
        <div class="ec-content-wrapper">
            <div class="content">
                <div class="breadcrumb-wrapper breadcrumb-wrapper-2">
                    <h1>Chi tiết đơn hàng</h1>
                    <p class="breadcrumbs">
                        <span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                        <span><i class="mdi mdi-chevron-right"></i></span>
                        <a href="<%=request.getContextPath()%>/admin/orders">Orders</a>
                        <span><i class="mdi mdi-chevron-right"></i></span>Order details
                    </p>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="ec-odr-dtl card card-default">
                            <div class="card-header card-header-border-bottom d-flex justify-content-between">
                                <h2 class="ec-odr">
                                    <span class="small">Order ID: ${order.orderId}</span>
                                </h2>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-xl-3 col-lg-6">
                                        <address class="info-grid">
                                            <div class="info-title"><strong>Người đặt</strong></div><br>
                                            <div class="info-content">
                                                Tên tài khoản: ${order.userName}<br>
                                                Địa chỉ: ${order.userAddress}<br>
                                                SĐT: ${order.userPhone}<br>
                                                Loại thanh toán: ${order.paymentMethod}
                                            </div>
                                        </address>
                                    </div>
                                    <div class="col-xl-3 col-lg-6">
                                        <address class="info-grid">
                                            <div class="info-title"><strong>Người nhận</strong></div><br>
                                            <div class="info-content">
                                                Tên: ${order.name}<br>
                                                Địa chỉ: ${order.address}<br>
                                                Email: ${order.email}<br>
                                                SĐT: ${order.phone}
                                            </div>
                                        </address>
                                    </div>
                                    <div class="col-xl-3 col-lg-6">
                                        <address class="info-grid">
                                            <div class="info-title"><strong>Ngày tạo hoá đơn</strong></div><br>
                                            <div class="info-content">
                                                ${order.dateCreated}<br>
                                            </div>
                                        </address>
                                    </div>
                                    <div class="col-xl-3 col-lg-6">
                                        <address class="info-grid">
                                            <div class="info-title"><strong>Ngày thanh toán</strong></div><br>
                                            <div class="info-content">
                                                ${order.dateDone}<br>
                                            </div>
                                        </address>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3 class="tbl-title">PRODUCT SUMMARY</h3>
                                        <div class="table-responsive">
                                            <table class="table table-striped o-tbl">
                                                <thead>
                                                    <tr class="line">
                                                        <td><strong>#</strong></td>
                                                        <td class="text-center"><strong>Ảnh</strong></td>
                                                        <td class="text-center"><strong>Tên sản phẩm</strong></td>
                                                        <td class="text-center"><strong>Đơn giá</strong></td>
                                                        <td class="text-center"><strong>Số lượng</strong></td>
                                                        <td class="text-right"><strong>Tổng tiền</strong></td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="i" begin="0" end="${orderItems.size() - 1}">
                                                        <tr class="<c:if test="${i == orderItems.size() - 1}">line</c:if>">
                                                            <td class="text-center">${orderItems.get(i).orderItemId}</td>
                                                            <td class="text-center"><img class="product-img"
                                                                     src="data:image/png;base64, ${orderItems.get(i).productImage}" alt="" /></td>
                                                            <td class="text-center"><strong>${orderItems.get(i).productName}</strong></td>
                                                            <td class="text-center">${orderItems.get(i).unitPrice}</td>
                                                            <td class="text-center">${orderItems.get(i).quantity}</td>
                                                            <td class="text-right">${orderItems.get(i).totalPrice}</td>
                                                        </tr>
                                                    </c:forEach>
                                                    <tr>
                                                        <td colspan="4">
                                                        </td>
                                                        <td class="text-right"><strong>Tổng tiền sản phẩm</strong></td>
                                                        <td class="text-right"><strong>${order.totalItemPrice}</strong> VND</td>
                                                    </tr>
                                                <tr>
                                                    <td colspan="4">
                                                    </td>
                                                    <td class="text-right"><strong>Phí vận chuyển</strong></td>
                                                    <td class="text-right"><strong>${order.shipping}</strong> VND</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="4">
                                                    </td>
                                                    <td class="text-right"><strong>Thành tiền</strong></td>
                                                    <td class="text-right"><strong>${order.totalPrice}</strong> VND</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Tracking Detail -->
                        <div class="card mt-4 trk-order">
                            <div class="p-4 text-center text-white text-lg bg-dark rounded-top">
                                <span class="text-uppercase">Trạng thái đơn hàng - </span>
                                <span class="text-medium">#${order.orderId}</span>
                            </div>
                            <div class="card-body">
                                <div
                                        class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
                                    <div class="step <%=ORDER_STATUS.isCompleted(order.getStatus(), ORDER_STATUS.PENDING)%>">
                                        <div class="step-icon-wrap">
                                            <div class="step-icon"><i class="mdi mdi-cart"></i></div>
                                        </div>
                                        <h4 class="step-title">Đang đợi</h4>
                                    </div>
                                    <div class="step <%=ORDER_STATUS.isCompleted(order.getStatus(), ORDER_STATUS.READY_TO_SHIP)%>">
                                        <div class="step-icon-wrap">
                                            <div class="step-icon"><i class="mdi mdi-gift"></i></div>
                                        </div>
                                        <h4 class="step-title">Sẵn sàng chuyển đi</h4>
                                    </div>
                                    <div class="step <%=ORDER_STATUS.isCompleted(order.getStatus(), ORDER_STATUS.ON_THE_WAY)%>">
                                        <div class="step-icon-wrap">
                                            <div class="step-icon"><i class="mdi mdi-truck-delivery"></i></div>
                                        </div>
                                        <h4 class="step-title">Đang giao</h4>
                                    </div>
                                    <div class="step <%=ORDER_STATUS.isCompleted(order.getStatus(), ORDER_STATUS.DELIVERED)%>">
                                        <div class="step-icon-wrap">
                                            <div class="step-icon"><i class="mdi mdi-hail"></i></div>
                                        </div>
                                        <h4 class="step-title">Đã giao</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/views/admin/common/footer.jsp"/>
    </div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
</script>
</body>
</html>
