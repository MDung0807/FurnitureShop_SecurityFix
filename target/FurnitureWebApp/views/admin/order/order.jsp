<%@ page import="utils.HtmlClassUtils" %>
<%@ page import="utils.constants.ORDER_STATUS" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="orders" scope="request" type="java.util.ArrayList<models.view_models.orders.OrderViewModel>"/>
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
          <h1>Danh sách đơn hàng</h1>
          <div class="d-flex justify-content-between">
            <a href="<%=request.getContextPath()%>/admin/orders?new=true" class="btn btn-outline-info mr-4">Đơn hàng mới</a>
            <a href="<%=request.getContextPath()%>/admin/orders" class="btn btn-outline-info">Tất cả đơn hàng</a>
            <a href="<%=request.getContextPath()%>/admin/orders?delivered=true" class="btn btn-outline-info ml-4">Đơn hàng hoàn thành</a>
          </div>
          <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
            <span><i class="mdi mdi-chevron-right"></i></span>Orders
          </p>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table" style="width:100%">
                    <thead>
                    <tr>
                      <th>ID</th>
                      <th>Tên khách hàng</th>
                      <th>Email</th>
                      <th>Tổng sản phẩm</th>
                      <th>Tổng tiền</th>
                      <th>Thanh toán</th>
                      <th>Trạng thái</th>
                      <th>Ngày đặt</th>
                      <th>Ngày thanh toán</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="order" items="${orders}">
                      <tr>
                        <td>${order.orderId}</td>
                        <td><strong>${order.userName}</strong></td>
                        <td>${order.email}</td>
                        <td>${order.totalItem}</td>
                        <td>${order.totalPrice}</td>
                        <td>${order.paymentMethod}</td>
                        <td><span class="mb-2 mr-2 ${order.statusClass}">${order.statusCode}</span>
                        </td>
                        <td>${order.dateCreated}</td>
                        <td>${order.dateDone}</td>
                        <td>
                          <div class="btn-group mb-1">
                            <button type="button"
                                    class="btn btn-outline-success">Info</button>
                            <button type="button"
                                    class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false" data-display="static">
                              <span class="sr-only">Info</span>
                            </button>

                            <div class="dropdown-menu">
                              <a class="dropdown-item" href="<%=request.getContextPath()%>/admin/order/detail?orderId=${order.orderId}">Chi tiết đơn hàng</a>
                              <a class="dropdown-item"
                                data-orderId="${order.orderId}"
                                data-bs-toggle="modal"
                                data-bs-target="#modal-change-order-status"
                                href="#modal-change-order-status"
                              >Cập nhật trạng thái</a>
                            </div>
                          </div>
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
        <div class="modal fade" id="modal-change-order-status" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0 d-flex justify-content-center">Trạng thái đơn hàng</h3>
              <div class="modal-body p-4 d-flex justify-content-center" >
                <form action="<%=request.getContextPath()%>/admin/order/editStatus" method="post">
                  <input type="hidden" name="editOrderId" id="editOrderId">
                  <label for="orderStatus">Trạng thái đơn hàng</label>
                    <select name="orderStatus" id="orderStatus" class="form-select" required>
                      <c:forEach var="s" items="<%=ORDER_STATUS.Status%>">
                        <option value="${s.value}">${s.key}</option>
                      </c:forEach>
                    </select>

                    <div class="row mt-4">
                      <div class="col col-6">
                        <input type="button" class="btn btn-secondary btn-pill clear-form"
                               data-bs-dismiss="modal" value="Huỷ">
                      </div>
                      <div class="col col-6">
                        <input type="submit" class="btn btn-primary btn-pill" value="Xác nhận"/>
                      </div>
                    </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-error" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0 d-flex justify-content-center">Thao tác bị lỗi, đơn hàng đã giao thành công chỉ có thể hoàn trả</h3>
              <div class="modal-footer px-4">
                <button type="button" class="btn btn-secondary btn-pill d-flex justify-content-center"
                        data-bs-dismiss="modal">Thoát</button>
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
  $(window).on('load', function() {
    if(window.location.href.includes("error")){
      $('#modal-error').modal('show');
    }
  });
  $(document).ready(function () {
    $('#modal-change-order-status').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-orderId');
      document.getElementById('editOrderId').value = id
    });
  });
</script>
</body>
</html>
