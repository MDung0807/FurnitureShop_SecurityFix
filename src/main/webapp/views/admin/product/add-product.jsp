<%@ page import="utils.constants.PRODUCT_STATUS" %>
<%@ page import="utils.constants.IMAGE_PER_PRODUCT" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="categories" scope="request" type="java.util.ArrayList<models.view_models.categories.CategoryViewModel>"/>
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
                            <h1>Add Product</h1>
                            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                                <span><i class="mdi mdi-chevron-right"></i></span>Product</p>
                        </div>
                        <div>
                            <a href="<%=request.getContextPath()%>/admin/products" class="btn btn-primary"> View All
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="card card-default">
                                <div class="card-header card-header-border-bottom">
                                    <h2>Product</h2>
                                </div>
                                <div class="card-body">
                                    <div class="row ec-vendor-uploads">
                                        <form class="row g-3"
                                                <c:choose>
                                                    <c:when test="${product == null}">
                                                        action="<%=request.getContextPath()%>/admin/product/add"
                                                    </c:when>
                                                    <c:when test="${product != null}">
                                                        action="<%=request.getContextPath()%>/admin/product/edit"
                                                    </c:when>
                                                </c:choose>
                                              method="post" enctype="multipart/form-data"
                                        >
                                            <div class="col-lg-4">
                                                <div class="ec-vendor-img-upload">
                                                    <div class="row ec-vendor-main-img">
                                                        <div class="avatar-upload">
                                                            <div class="avatar-edit">
                                                                <input type='file' id="imageUpload" class="ec-image-upload" name="main-image"
                                                                       accept=".png, .jpg, .jpeg" <c:if test="${product == null}">required</c:if> />
                                                                <label for="imageUpload"><img
                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                        class="svg_img header_svg" alt="edit" /></label>
                                                            </div>
                                                            <div class="avatar-preview ec-preview">
                                                                <div class="imagePreview ec-div-preview">
                                                                    <img class="ec-image-preview"
                                                                            <c:choose>
                                                                                <c:when test="${product == null}">
                                                                                    src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-preview.jpg"
                                                                                </c:when>
                                                                                <c:when test="${product != null}">
                                                                                    src="data:image/png;base64, ${product.image}"
                                                                                </c:when>
                                                                            </c:choose>
                                                                         alt="edit" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="thumb-upload-set col-md-12">
                                                            <c:choose>
                                                                <c:when test="${product == null}">
                                                                    <div class="thumb-upload">
                                                                        <div class="thumb-edit">
                                                                            <input type='file' id="thumbUpload1"
                                                                                   name="sub-image-1"
                                                                                   class="ec-image-upload"
                                                                                   accept=".png, .jpg, .jpeg" required/>
                                                                            <label for="thumbUpload1"><img
                                                                                    src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                                    class="svg_img header_svg" alt="edit" /></label>
                                                                        </div>
                                                                        <div class="thumb-preview ec-preview">
                                                                            <div class="image-thumb-preview">
                                                                                <img class="image-thumb-preview ec-image-preview"
                                                                                     src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                                                                     alt="edit" />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:when>
                                                                <c:when test="${product != null}">
                                                                    <c:forEach var="subImage" begin="1" end="${product.productImages.size()}">
                                                                        <div class="thumb-upload">
                                                                            <div class="thumb-preview ec-preview">
                                                                                <div class="image-thumb-preview">
                                                                                    <img class="image-thumb-preview ec-image-preview"
                                                                                         src="data:image/png;base64, ${product.productImages.get(subImage - 1).image}"
                                                                                         alt="edit" />
                                                                                </div>
                                                                            </div>

                                                                            <div class="d-flex justify-content-center mt-2">
                                                                                <label onclick="removeSubImage(this, ${product.productImages.get(subImage - 1).id}, ${subImage})"><img
                                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/remove.png"
                                                                                        class="svg_img header_svg" alt="remove" /></label>
                                                                            </div>
                                                                        </div>
                                                                    </c:forEach>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-8">
                                                <div class="ec-vendor-upload-detail">
                                                    <div class="row">
                                                        <c:if test="${product != null}">
                                                            <input type="hidden" name="productId" id="productId" value="${product.productId}">
                                                        </c:if>
                                                        <div class="col-md-6">
                                                            <label for="ProductName" class="form-label">Tên sản phẩm</label>
                                                            <input type="text" class="form-control slug-title" id="ProductName" name="productName" required value="${product.name}">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label for="Categories" class="form-label">Danh mục</label>
                                                            <select name="categories" id="Categories" class="form-select" required>
                                                                <c:forEach var="c" items="${categories}">
                                                                    <c:if test="${product != null && c.categoryId == product.categoryId}" >
                                                                        <option value="${c.categoryId}" selected>${c.name}</option>
                                                                    </c:if>
                                                                    <c:if test="${product == null || c.categoryId != product.categoryId}" >
                                                                        <option value="${c.categoryId}">${c.name}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row mt-4">
                                                        <div class="col-md-6">
                                                            <label for="description" class="form-label">Mô tả</label>
                                                            <input type="text" id="description" class="form-control" rows="2" required name="description" value="${product.description}"/>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label for="Brands" class="form-label">Thương hiệu</label>
                                                            <select name="brands" id="Brands" class="form-select" required>
                                                                <c:forEach var="b" items="${brands}">
                                                                    <c:if test="${product != null && b.brandId == product.brandId}" >
                                                                        <option value="${b.brandId}" selected>${b.brandName}</option>
                                                                    </c:if>
                                                                    <c:if test="${product == null || b.brandId != product.brandId}" >
                                                                        <option value="${b.brandId}">${b.brandName}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row mt-4">
                                                        <div class="col-md-6">
                                                            <label for="price" class="form-label">Đơn giá</label>
                                                            <input type="number" class="form-control" min="0" id="price" name="price" value="${product.price}" required>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label for="quantity" class="form-label">Số lượng</label>
                                                            <input type="number" class="form-control" min="0" id="quantity" name="quantity" value="${product.quantity}" required>
                                                        </div>
                                                    </div>
                                                    <div class="row mt-4">
                                                        <div class="col-md-4">
                                                            <label for="origin" class="form-label">Nguồn gốc</label>
                                                            <input type="text" class="form-control" id="origin"
                                                                   name="origin" value="${product.origin}" required/>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="status" class="form-label">Trạng thái</label>
                                                            <select id="status" name="status" class="form-select" required>
                                                                <c:forEach var="x" items="<%=PRODUCT_STATUS.Status%>">
                                                                    <option value="${x.value}" <c:if test="${x.value == product.status}">selected</c:if>>${x.key}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="number-sub-image" class="form-label">Số lượng ảnh mô tả</label>
                                                            <input type="hidden" value="${product != null ? product.productImages.size() : 1}" name="min-number-sub-image" class="min-number-sub-image">
                                                            <input value="${product != null ? product.productImages.size() : 1}" onkeydown="return false" data-prev-value="0" type="number" class="form-control number-sub-image" min="${product != null ? product.productImages.size() : 1}" max="<%=IMAGE_PER_PRODUCT.QUANTITY%>" id="number-sub-image" name="number-sub-image">
                                                        </div>
                                                    </div>
                                                    <div class="row mt-4">
                                                        <div class="col col-6">
                                                            <a class="btn btn-danger" href="<%=request.getContextPath()%>/admin/products">Huỷ</a>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <button type="submit" class="btn btn-primary w-100">Xác nhận</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
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
        $('.number-sub-image').on('input', (e) => {
            changeNumberSubImage(e)
        })
        function changeNumberSubImage(e) {
            let n = document.querySelector(`.number-sub-image`).value
            let min = document.querySelector(`.min-number-sub-image`).value
            let node = document.querySelector('.thumb-upload-set');
            let direction = e.target.value > parseInt(e.target.dataset.prevValue) ? 'up' : 'down'
            e.target.dataset.prevValue = e.target.value;
            if(direction === 'up' && e.target.value <= <%=IMAGE_PER_PRODUCT.QUANTITY%>){
                let html = `
                            <div class="thumb-upload">
                                <div class="thumb-edit">
                                    <input type='file' id="thumbUpload`+ e.target.value +`"
                                            name="sub-image-`+ e.target.value +`"
                                            class="ec-image-upload"
                                           accept=".png, .jpg, .jpeg" required/>
                                    <label for="thumbUpload`+ e.target.value +`"><img
                                            src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                            class="svg_img header_svg" alt="edit" /></label>
                                </div>
                                <div class="thumb-preview ec-preview">
                                    <div class="image-thumb-preview">
                                        <img class="image-thumb-preview ec-image-preview"
                                             src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                             alt="edit" />
                                    </div>
                                </div>
                            </div>`
                node.innerHTML += html
            }else if(direction === 'down' && e.target.value >= min){
                node.removeChild(node.lastElementChild)
            }
        }
        function removeSubImage(e, id, index){
            let node = document.querySelector('.thumb-upload-set');
            let subImageElement = document.querySelector(`.number-sub-image`);
            let n = subImageElement.value
            if(n > 1){
                $.ajax({
                        url: "<%=request.getContextPath()%>/admin/product/images/delete",
                        type: "get",
                        data: {
                            productImageId: id
                        },
                        success: function (data) {
                            let isSuccess = JSON.parse(data)
                            console.log(isSuccess)
                            if(isSuccess === true){
                                subImageElement.setAttribute('min', (n - 1).toString())
                                subImageElement.value = n - 1
                                document.querySelector(`.min-number-sub-image`).value = n - 1
                                subImageElement.dataset.prevValue = (n - 1).toString()
                                node.removeChild(node.children.item(index - 1))
                            }
                        },
                        error: function (xhr) {
                            console.log(xhr)
                        }
                    }
                )
            }
        }
    </script>
    </body>
</html>
