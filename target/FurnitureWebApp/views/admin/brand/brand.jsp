<%@ page import="com.google.gson.Gson" %>
<%@ page import="utils.constants.BRAND_STATUS" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="brands" scope="request" type="java.util.ArrayList<models.view_models.brands.BrandViewModel>"/>
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
                    <div class="breadcrumb-wrapper d-flex align-items-center justify-content-between">
                        <div>
                            <h1>Thương hiệu sản phẩm</h1>
                            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                                <span><i class="mdi mdi-chevron-right"></i></span> Brand</p>
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${brand == null}">
                                    <a type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-add-brand" href="#modal-add-brand">Thêm thương hiệu</a>
                                </c:when>
                                <c:when test="${brand != null}">
                                    <form method="post" action="<%=request.getContextPath()%>/admin/brands">
                                        <input type="submit" class="btn btn-primary" value="Thêm thương hiệu">
                                    </form>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="product-brand card card-default p-24px">
                        <div class="search-form d-lg-inline-block w-25">
                            <div class="input-group">
                                <input oninput="searchBrand(this)" type="text" name="query" id="search-input" class="form-control"
                                       placeholder="search.." autofocus autocomplete="off" />
                            </div>
                            <div id="search-results-container">
                                <ul id="search-results"></ul>
                            </div>
                        </div>
                        <div id="brand-content" class="row mb-m-24px">
                                <c:if test="${brands != null}">
                                    <c:forEach var="b" items="${brands}">
                                    <div class="col-xxl-2 col-xl-3 col-lg-4 col-md-6">
                                        <div class="card card-default">
                                            <div class="card-body text-center p-24px">
                                                <div class="image mb-3">
                                                    <img src="data:image/png;base64,<c:out value="${b.image}"/>" class="img-fluid rounded-circle"
                                                         alt="Avatar Image">
                                                </div>

                                                <h3 class="card-title text-dark">${b.brandName}</h3>
                                                <h5 class="card-title text-dark">${b.origin}</h5>
                                                <h5 class="card-title text-dark">${b.statusCode}</h5>
                                                <p class="item-count">${b.totalProducts}<span> items</span></p>
                                                <span class="brand-delete mdi mdi-delete-outline" data-bs-toggle="modal"
                                                      data-bs-target="#modal-delete-brand" data-backdrop="static" data-keyboard="false" data-id="${b.brandId}" href="#modal-delete-brand"></span>
                                                <a
                                                        href="<%=request.getContextPath()%>/admin/brand/edit?brandId=${b.brandId}"
                                                >
                                                <span class="mdi mdi-account-edit "></span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    </c:forEach>
                                </c:if>
                        </div>
                    </div>

                    <!-- Add Brand Modal  -->
                    <div class="modal fade" id="modal-add-brand" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                            <div class="modal-content">
                                <form class="modal-header border-bottom-0 text-center">
                                    <h3>Brand</h3>
                                </form>

                                <div class="modal-body p-0">
                                    <form id="form-add" class="p-4"
                                            <c:choose>
                                                <c:when test="${brand == null}">
                                                    action="<%=request.getContextPath()%>/admin/brand/add"
                                                </c:when>
                                                <c:when test="${brand != null}">
                                                    action="<%=request.getContextPath()%>/admin/brand/edit"
                                                </c:when>
                                            </c:choose>
                                          method="post" enctype="multipart/form-data"
                                    >
                                        <input type="hidden" id="brandId" name="brandId" value="${brand.brandId}" />
                                        <div class="row">
                                            <label for="brandName" class="col-12 col-form-label">Tên thương hiệu</label>
                                            <div class="col-12">
                                                <input id="brandName" name="brandName" class="form-control here slug-title" type="text" value="${brand.brandName}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label class="col-12 col-form-label" for="brandOrigin">Nguồn gốc</label>
                                            <div class="col-12">
                                                <input type="text" id="brandOrigin" name="brandOrigin" cols="40" rows="4" class="form-control" value="${brand.origin}" required/>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <label class="col-12 col-form-label" for="status">Trạng thái</label>
                                            <div class="col-12">
                                                <select id="status" name="status" class="form-select" required>
                                                    <c:forEach var="s" items="<%=BRAND_STATUS.Status%>">
                                                        <option value="${s.value}" <c:if test="${s.value == brand.status}">selected</c:if> >${s.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row ec-vendor-uploads">
                                            <label class="col-12 col-form-label" for="brand-logo">Logo</label>
                                            <div class="ec-vendor-img-upload">
                                                <div class="ec-vendor-main-img">
                                                    <div class="thumb-upload-set col-md-12">
                                                        <div class="thumb-upload">
                                                            <div class="thumb-edit">
                                                                <input type='file' id="brand-logo" name="brand-logo"
                                                                       class="ec-image-upload"
                                                                       accept=".png, .jpg, .jpeg" <c:if test="${brand == null}"> required</c:if> />
                                                                <label for="brand-logo"><img
                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                        class="svg_img header_svg" alt="edit"/>
                                                                </label>
                                                            </div>
                                                            <div class="thumb-preview ec-preview">
                                                                <div class="image-thumb-preview">
                                                                    <img class="image-thumb-preview ec-image-preview clear-img"
                                                                            <c:choose>
                                                                                <c:when test="${brand == null}">
                                                                                    src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                                                                </c:when>
                                                                                <c:when test="${brand != null}">
                                                                                    src="data:image/png;base64,<c:out value="${brand.image}"/>"
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
                    <div class="modal fade" id="modal-delete-brand" tabindex="-1" role="dialog" aria-hidden="true">
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
        $(document).ready(function () {
            $('#modal-delete-brand').on('show.bs.modal', function (event) {
                let id = $(event.relatedTarget).attr('data-id');
                let link = "<%=request.getContextPath()%>/admin/brand/delete?brandId=" + id;
                document.getElementById('link-delete').href = link
            });
        });
        $(window).on('load', function() {
            if(${brand != null} ||  ${edit != null}) {
                $('#modal-add-brand').modal('show');
            }
            if(${error != null}){
                $('#modal-error').modal('show');
            }
            else if(window.location.href.includes("error")){
                $('#modal-error').modal('show');
            }
        });
        $(".clear-form").on("click", function(e) {
            e.preventDefault();
            document.getElementById('form-add').reset();
            $("input[type='text']").val('');
            $("input[type='file']").val('');
            document.querySelector('.clear-img').src = "<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg";
        })

        function searchBrand(param){
            let txt = param.value.toLowerCase();
            <%
                String json = new Gson().toJson(brands);
             %>
            let html = <%=json%>.map((x, index) => {
                        if(x.brandName.toLowerCase().includes(txt) || x.origin.toLowerCase().includes(txt)){
                            return `<div class="col-xxl-2 col-xl-3 col-lg-4 col-md-6">
                                        <div class="card card-default">
                                            <div class="card-body text-center p-24px">
                                                <div class="image mb-3">
                                                    <img src="data:image/png;base64,` + x.image + `" class="img-fluid rounded-circle"
                                                         alt="Avatar Image">
                                                </div>

                                                <h3 class="card-title text-dark">` + x.brandName + `</h3>
                                                <h5 class="card-title text-dark">` + x.origin + `</h5>
                                                <p class="item-count">` + x.totalProducts + `<span> items</span></p>
                                                <span class="brand-delete mdi mdi-delete-outline" data-bs-toggle="modal"
                                                      data-bs-target="#modal-delete-brand" data-backdrop="static" data-keyboard="false" data-id="` + x.brandId + `" href="#modal-delete-brand"></span>
                                                <a
                                                    <c:choose>
                                                        <c:when test="${brand == null}">
                                                            href="<%=request.getContextPath()%>/admin/brand/edit?brandId=` + x.brandId + `"
                                                        </c:when>
                                                        <c:when test="${brand != null}">
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#modal-add-brand"
                                                            href="#modal-add-brand"
                                                        </c:when>
                                                    </c:choose>
                                                >
                                                <span class="mdi mdi-account-edit "></span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>`
                        }
            })
            document.getElementById("brand-content").innerHTML = html.join('') ;
        }
    </script>
</body>
</html>
