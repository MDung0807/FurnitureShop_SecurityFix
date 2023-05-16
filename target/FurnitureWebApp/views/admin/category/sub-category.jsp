<%@ page import="utils.constants.CATEGORY_STATUS" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="categories" type="java.util.ArrayList<models.view_models.categories.CategoryViewModel>" scope="request"/>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="Ekka - Admin Dashboard">

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
                    <div>
                        <a class="btn btn-outline-info" href="<%=request.getContextPath()%>/admin/categories">Main Category</a>
                        <a class="btn btn-outline-success active" href="<%=request.getContextPath()%>/admin/categories?sub-categories=true">Danh mục con</a>
                    </div>
                    <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                        <span><i class="mdi mdi-chevron-right"></i></span>Sub Category</p>
                </div>
                <div class="row">
                    <div class="col-xl-4 col-lg-12">
                        <div class="ec-cat-list card card-default mb-24px">
                            <div class="card-body">
                                <div class="ec-cat-form">
                                    <h4>Danh mục con</h4>
                                    <form id="form-add"
                                            <c:choose>
                                                <c:when test="${category == null}">
                                                    action="<%=request.getContextPath()%>/admin/category/add"
                                                </c:when>
                                                <c:when test="${category != null}">
                                                    action="<%=request.getContextPath()%>/admin/category/edit?sub-categories=true"
                                                </c:when>
                                            </c:choose>
                                          method="post" enctype="multipart/form-data"
                                    >
                                        <input type="hidden" id="categoryId" name="categoryId" value="${category.categoryId}"/>
                                        <div class="row">
                                            <label for="categoryName" class="col-12 col-form-label">Tên danh mục</label>
                                            <div class="col-12">
                                                <input id="categoryName" name="categoryName" class="form-control here slug-title" type="text" value="${category.name}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label class="col-12 col-form-label" for="description">Mô tả</label>
                                            <div class="col-12">
                                                <input type="text" id="description" name="description" cols="40" rows="4" class="form-control" value="${category.description}" required/>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <label class="col-12 col-form-label" for="status">Trạng thái</label>
                                            <div class="col-12">
                                                <select id="status" name="status" class="form-select" required>
                                                    <c:forEach var="s" items="<%=CATEGORY_STATUS.Status%>">
                                                        <option value="${s.value}" <c:if test="${s.value == category.status}">selected</c:if> >${s.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="parent-category" class="col-12 col-form-label">Danh mục cha</label>
                                            <div class="col-12">
                                                <select id="parent-category" name="parent-category" class="custom-select" required>
                                                    <c:forEach var="parent" items="${parentCategories}">
                                                        <option value="${parent.key}"><c:out value="${parent.value}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row ec-vendor-uploads">
                                            <label class="col-12 col-form-label" for="category-logo">Logo</label>
                                            <div class="ec-vendor-img-upload">
                                                <div class="ec-vendor-main-img">
                                                    <div class="thumb-upload-set col-md-12">
                                                        <div class="thumb-upload">
                                                            <div class="thumb-edit">
                                                                <input type='file' id="category-logo" name="category-logo"
                                                                       class="ec-image-upload"
                                                                       accept=".png, .jpg, .jpeg" <c:if test="${category == null}"> required</c:if>/>
                                                                <label for="category-logo"><img
                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                        class="svg_img header_svg" alt="edit"/>
                                                                </label>
                                                            </div>
                                                            <div class="thumb-preview ec-preview">
                                                                <div class="image-thumb-preview">
                                                                    <img class="image-thumb-preview ec-image-preview clear-img"
                                                                            <c:choose>
                                                                                <c:when test="${category == null}">
                                                                                    src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                                                                </c:when>
                                                                                <c:when test="${category != null}">
                                                                                    src="data:image/png;base64,<c:out value="${category.image}"/>"
                                                                                </c:when>
                                                                            </c:choose>
                                                                         alt="edit" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
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
                                            <th>Thumb</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Main Category</th>
                                            <th>Total Product</th>
                                            <th>Total Sell</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:if test="${categories != null}">
                                            <c:forEach var="category" items="${categories}">
                                                <tr>
                                                    <td><img class="cat-thumb" src="data:image/png;base64,<c:out value="${category.image}"/>" alt="Product Image" /></td>
                                                    <td><c:out value="${category.name}"/></td>
                                                    <td><c:out value="${category.description}"/></td>
                                                    <td>
                                                        <span class="ec-sub-cat-list">
                                                                <span class="ec-sub-cat-tag"><c:out value="${category.parentCategoryName}"/></span>
                                                        </span>
                                                    </td>
                                                    <td><c:out value="${category.totalProduct}"/></td>
                                                    <td><c:out value="${category.totalSell}"/></td>
                                                    <td>${category.statusCode}</td>
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
                                                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/category/edit?categoryId=<c:out value="${category.categoryId}"/>&sub-categories=true">Sửa</a>
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-delete-category" data-id="${category.categoryId}" href="#modal-delete-category">Xoá
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
                <div class="modal fade" id="modal-delete-category" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content">
                            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá danh mục sản phẩm này</h3>
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
        $('#modal-delete-category').on('show.bs.modal', function (event) {
            let id = $(event.relatedTarget).attr('data-id');
            let link = "<%=request.getContextPath()%>/admin/category/delete?categoryId=" + id + "&sub-categories=true";
            document.getElementById('link-delete').href = link
        });
    });

    $(".clear-form").on("click", function(e) {
        e.preventDefault();
        document.getElementById('form-add').reset();
        $("input[type='text']").val('');
        $("input[type='file']").val('');
        document.querySelector('.clear-img').src = "<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg";
    })
</script>
</body>
</html>
