<%@ page import="utils.constants.DISCOUNT_STATUS" %>
<%@ page import="utils.constants.ROLE_STATUS" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="roles" type="java.util.ArrayList<models.view_models.roles.RoleViewModel>" scope="request"/>
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
            <span><i class="mdi mdi-chevron-right"></i></span>Role</p>
        </div>
        <div class="row">
          <div class="col-xl-4 col-lg-12">
            <div class="ec-cat-list card card-default mb-24px">
              <div class="card-body">
                <div class="ec-cat-form">
                  <h4>Vai trò hệ thống</h4>
                  <form id="form-add"
                          <c:choose>
                            <c:when test="${role == null}">
                              action="<%=request.getContextPath()%>/admin/role/add"
                            </c:when>
                            <c:when test="${role != null}">
                              action="<%=request.getContextPath()%>/admin/role/edit"
                            </c:when>
                          </c:choose>
                        method="post"
                  >
                    <input type="hidden" id="roleId" name="roleId" value="${role.roleId}"/>
                    <div class="row">
                      <label for="roleName" class="col-12 col-form-label">Tên vai trò</label>
                      <div class="col-12">
                        <input id="roleName" name="roleName" class="form-control here slug-title" type="text" value="${role.roleName}" required/>
                      </div>
                    </div>

                    <div class="row">
                      <label class="col-12 col-form-label" for="status">Trạng thái</label>
                      <div class="col-12">
                        <select id="status" name="status" class="form-select" required>
                          <c:forEach var="s" items="<%=ROLE_STATUS.Status%>">
                            <option value="${s.value}" <c:if test="${s.value == role.status}">selected</c:if> >${s.key}</option>
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
                      <th>Mã vai trò</th>
                      <th>Tên vai trò</th>
                      <th>Trạng thái</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${roles != null}">
                      <c:forEach var="r" items="${roles}">
                        <tr>
                          <td>${r.roleId}</td>
                          <td>${r.roleName}</td>
                          <td>${r.statusCode}</td>
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
                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/role/edit?roleId=${r.roleId}">Sửa</a>
                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                   data-bs-target="#modal-delete-role" data-id="${r.roleId}" href="#modal-delete-role">Xoá
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
        <div class="modal fade" id="modal-delete-role" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0">Bạn có muốn xoá vai trò này</h3>
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
    $('#modal-delete-role').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      let link = "<%=request.getContextPath()%>/admin/role/delete?roleId=" + id;
      document.getElementById('link-delete').href = link
    });
  });
</script>
</body>
</html>
