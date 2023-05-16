<%@ page import="utils.HtmlClassUtils" %>
<%@ page import="utils.constants.USER_GENDER" %>
<%@ page import="utils.constants.USER_STATUS" %>
<%@ page import="utils.FileUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="users" scope="request" type="java.util.ArrayList<models.view_models.users.UserViewModel>"/>
<jsp:useBean id="roles" scope="request" type="java.util.ArrayList<models.view_models.roles.RoleViewModel>"/>
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
        <div class="breadcrumb-wrapper breadcrumb-contacts">
          <div>
            <h1>Danh sách tài khoản</h1>
            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
              <span><i class="mdi mdi-chevron-right"></i></span>Quản lý tài khoản
            </p>
          </div>
          <div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                    data-bs-target="#addUser"> Thêm Tài khoản
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="ec-vendor-list card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table">
                    <thead>
                    <tr>
                      <th>Profile</th>
                      <th>Username</th>
                      <th>Họ</th>
                      <th>Tên</th>
                      <th>Ngày sinh</th>
                      <th>Giới tính</th>
                      <th>Email</th>
                      <th>Điện thoại</th>
                      <th>Đã mua</th>
                      <th>Trạng thái</th>
                      <th>Ngày tham gia</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="user" items="${users}">
                      <tr>
                        <td><img class="vendor-thumb" src="data:image/png;base64, ${user.avatar}" alt="user profile" /></td>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.dateOfBirth}</td>
                        <td>${user.genderCode}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.totalBought}</td>
                        <td>${user.statusCode}</td>
                        <td>${user.dateCreated}</td>
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
                              <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/user/detail?userId=${user.id}">Sửa</a>
                              <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                 data-bs-target="#modal-delete-user" data-id="${user.id}" href="#modal-delete-user">Xoá</a>
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
        <!-- Add User Modal  -->
        <div class="modal fade modal-add-contact" id="addUser" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
            <div class="modal-content">
              <form id="form-add" action="<%=request.getContextPath()%>/admin/user/add" method="post" enctype="multipart/form-data" >
                <div class="modal-header px-4">
                  <h5 class="modal-title" id="exampleModalCenterTitle">Thêm tài khoản mới</h5>
                </div>

                <div class="modal-body px-4">

                  <div class="row ec-vendor-uploads">
                    <label class="col-12 col-form-label" for="avatar">Avatar</label>
                    <div class="ec-vendor-img-upload">
                      <div class="ec-vendor-main-img">
                        <div class="thumb-upload-set col-md-12">
                          <div class="thumb-upload">
                            <div class="thumb-edit">
                              <input type='file' id="avatar" name="avatar"
                                     class="ec-image-upload"
                                     accept=".png, .jpg, .jpeg" required title="Vui lòng chọn ảnh"/>
                              <label for="avatar">
                                <img src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                      class="svg_img header_svg" alt="edit"/>
                              </label>
                            </div>
                            <div class="thumb-preview ec-preview">
                              <div class="image-thumb-preview">
                                <img class="image-thumb-preview ec-image-preview clear-img"
                                     src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                     alt="edit" />
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-lg-6">
                      <div class="form-group">
                        <label for="firstName">Họ</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required title="Vui lòng nhập họ">
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group">
                        <label for="lastName">Tên</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required title="Vui lòng nhập tên">
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email"
                               name="email" required title="Vui lòng nhập đúng định dạng email">
                        <p class="mt-3" id='emailValidateMessage'></p>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="phone">Phone</label>
                        <input type="text" class="form-control" id="phone" pattern="[0-9]{10}"
                               name="phone" required title="Vui lòng nhập số điện thoại gồm 10 chữ số">
                        <p class="mt-3" id='phoneValidateMessage'></p>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="dob">Birthday</label>
                        <input type="date" class="form-control" id="dob" name="dob"  required title="Vui lòng chọn ngày sinh">
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="gender">Giới tính</label>
                        <select id="gender" name="gender" class="form-select" required title="Vui lòng chọn giới tính">
                          <c:forEach var="g" items="<%=USER_GENDER.Gender%>">
                            <option value="${g.value}">${g.key}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="address">Địa chỉ</label>
                        <input type="text" class="form-control" id="address" name="address" required title="Vui lòng nhập địa chỉ" />
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="username">User name</label>
                        <input type="text" class="form-control" id="username"
                               name="username" required title="Vui lòng nhập tên tài khoản">
                        <p class="mt-3 text-danger" id="userValidateMessage" ></p>
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password"
                               name="password" required title="Vui lòng nhập mật khẩu">
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword"
                               name="confirmPassword" required>
                        <p class="mt-3" id='confirmPasswordNotMatch'></p>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="status">Trạng thái</label>
                        <select id="status" name="status" class="form-select" required>
                          <c:forEach var="s" items="<%=USER_STATUS.Status%>">
                            <option value="${s.value}">${s.key}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="dropdown button-group">
                          <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-bs-toggle="dropdown">Roles</button>
                          <ul class="role dropdown-menu">
                            <c:forEach var="role" items="${roles}">
                              <li>
                                <a href="#" class="small" data-value="${role.roleId}" tabIndex="-1">
                                  <input type="checkbox" class="roleCheckBox" id="roleCheckBox-${role.roleId}" name="roleCheckBox" value="${role.roleId}"/>&nbsp;${role.roleName}
                                </a>
                              </li>
                            </c:forEach>
                          </ul>
                          <p class="mt-3" id='roleEmpty'></p>
                        </div>
                    </div>
                  </div>
                </div>

                <div class="modal-footer px-4">
                  <button type="button" class="btn btn-secondary btn-pill clear-form"
                          data-bs-dismiss="modal">Huỷ</button>
                  <button type="submit" class="btn btn-primary btn-pill">Xác nhận</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-delete-user" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0">Bạn có muốn cấm tài khoản này hoạt động</h3>
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
  $(document).ready(function () {
    $('#modal-delete-user').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      let link = "<%=request.getContextPath()%>/admin/user/delete?userId=" + id;
      document.getElementById('link-delete').href = link
    });
  });
  $(window).on('load', function() {
    if((new URLSearchParams(window.location.search)).has("error") || ${error != null}){
      $('#modal-error').modal('show');
    }
  });
  $('#form-add').submit(function (e){
    validateForm(e, `<%=request.getContextPath()%>`)
  })
</script>
<script src="<%=request.getContextPath()%>/assets/admin/js/validate/admin/user/user-validation.js"></script>
</body>
</html>
