<%@ page import="utils.constants.DISCOUNT_STATUS" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="discounts" type="java.util.ArrayList<models.view_models.discounts.DiscountViewModel>" scope="request"/>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta name="discountValue" content="Ekka - Admin Dashboard">

  <title>Ekka - Admin Dashboard eCommerce</title>
  <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-light ec-header-light" id="body">
<div class="wrapper">
  <jsp:include page="/views/admin/common/sidebar.jsp"/>
  <div class="ec-page-wrapper">
    <jsp:include page="/views/admin/common/header.jsp"/>
    <div class="ec-content-wrapper">
      <div class="content">
        <div class="breadcrumb-wrapper breadcrumb-wrapper-2 breadcrumb-contacts">
          <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
            <span><i class="mdi mdi-chevron-right"></i></span>Discount</p>
        </div>
        <div class="row">
          <div class="col-xl-4 col-lg-12">
            <div class="ec-cat-list card card-default mb-24px">
              <div class="card-body">
                <div class="ec-cat-form">
                  <h4>Mã khuyến mãi</h4>
                  <form id="form-add"
                          <c:choose>
                            <c:when test="${discount == null}">
                              action="<%=request.getContextPath()%>/admin/discount/add"
                            </c:when>
                            <c:when test="${discount != null}">
                              action="<%=request.getContextPath()%>/admin/discount/edit"
                            </c:when>
                          </c:choose>
                        method="post"
                  >
                    <input type="hidden" id="discountId" name="discountId" value="${discount.discountId}"/>
                    <div class="row">
                      <label for="discountCode" class="col-12 col-form-label">Mã khuyến mãi</label>
                      <div class="col-12">
                        <input id="discountCode" name="discountCode" class="form-control here slug-title" type="text" value="${discount.discountCode}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label class="col-12 col-form-label" for="discountValue">Giá trị (0.1 -> 1.0)</label>
                      <div class="col-12">
                        <input type="text" id="discountValue" name="discountValue" cols="40" rows="4" class="form-control" value="${discount.discountValue}" required/>
                      </div>
                      <p class="mt-3" id="discountValueValidate"></p>
                    </div>
                    <div class="row">
                      <label class="col-12 col-form-label" for="startDate">Ngày bắt đầu</label>
                      <div class="col-12">
                        <input type="datetime-local" id="startDate" name="startDate" cols="40" rows="4" class="form-control" value="${discount.startDate}" required/>
                      </div>
                      <p class="mt-3" id="startDateValidate"></p>
                    </div>
                    <div class="row">
                      <label class="col-12 col-form-label" for="endDate">Ngày kết thúc</label>
                      <div class="col-12">
                        <input type="datetime-local" id="endDate" name="endDate" cols="40" rows="4" class="form-control" value="${discount.endDate}" required/>
                      </div>
                      <p class="mt-3" id="endDateValidate"></p>
                    </div>
                    <div class="row">
                      <label class="col-12 col-form-label" for="quantity">Số lượng</label>
                      <div class="col-12">
                        <input type="number" id="quantity" name="quantity" cols="40" rows="4" class="form-control" value="${discount.quantity}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label class="col-12 col-form-label" for="status">Trạng thái</label>
                      <div class="col-12">
                        <select id="status" name="status" class="form-select" required>
                          <c:forEach var="s" items="<%=DISCOUNT_STATUS.Status%>">
                            <option value="${s.value}" <c:if test="${s.value == discount.status}">selected</c:if> >${s.key}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="row mt-4">
                      <div class="col col-6">
                        <input type="button" class="btn btn-danger clear-form" value="Huỷ">
                      </div>
                      <div class="col col-6">
                        <input name="submit" type="submit" class="btn btn-primary" value="Xác nhận"/>
                      </div>
                    </div>
                  </form>

                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-8 col-lg-12">
            <div class="ec-cat-list card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table">
                    <thead>
                      <tr>
                        <th>Mã khuyến mãi</th>
                        <th>Giá trị</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Trạng thái</th>
                        <th>Số lượng</th>
                        <th>Action</th>
                      </tr>
                    </thead>

                    <tbody>
                      <c:if test="${discounts != null}">
                      <c:forEach var="discount" items="${discounts}">
                        <tr>
                          <td>${discount.discountCode}</td>
                          <td>${discount.discountValue}</td>
                          <td>${discount.startDate}</td>
                          <td>${discount.endDate}</td>
                          <td>${discount.statusCode}</td>
                          <td>${discount.quantity}</td>
                          <td>
                            <div class="btn-group">
                              <button type="button"
                                      class="btn btn-outline-success">Info</button>
                              <button type="button"
                                      class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                      data-bs-toggle="dropdown" aria-haspopup="true"
                                      aria-expanded="false" data-display="static">
                                <span class="sr-only">Info</span>
                              </button>

                              <div class="dropdown-menu">
                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/discount/edit?discountId=${discount.discountId}">Sửa</a>
                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                   data-bs-target="#modal-delete-discount" data-id="${discount.discountId}" href="#modal-delete-discount">Xoá
                                </a>
                              </div>
                            </div>
                          </td>
                        </tr>
                      </c:forEach>
                    </c:if>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-delete-discount" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0">Bạn có muốn xoá mã khuyến mãi này</h3>
              <div class="modal-footer px-4">
                <button type="button" class="btn btn-secondary btn-pill"
                        data-bs-dismiss="modal">Huỷ</button>
                <a id="link-delete" class="btn btn-danger btn-pill" >Xoá</a>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-error" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0 d-flex justify-content-center">Thao tác bị lỗi, vui lòng thực hiện lại</h3>
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
    if(${error != null}){
      $('#modal-error').modal('show');
    }
    else if(window.location.href.includes("error")){
      $('#modal-error').modal('show');
    }
  });
  $(document).ready(function () {
    $('#modal-delete-discount').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      let link = "<%=request.getContextPath()%>/admin/discount/delete?discountId=" + id;
      document.getElementById('link-delete').href = link
    });
  });
</script>
<script src="<%=request.getContextPath()%>/assets/admin/js/validate/admin/discount/discount-validation.js"></script>
</body>
</html>
