<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="reviews" scope="request" type="java.util.ArrayList<models.view_models.review_items.ReviewItemViewModel>"/>
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

                <div
                        class="breadcrumb-wrapper breadcrumb-wrapper-2 d-flex align-items-center justify-content-between">
                    <h1>Đánh giá sản phẩm</h1>
                    <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                        <span><i class="mdi mdi-chevron-right"></i></span>Review
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
                                                <th>Thumb</th>
                                                <th>Tên sản phẩm</th>
                                                <th>Tên người dùng</th>
                                                <th>Ratings</th>
                                                <th>Nội dung đánh giá</th>
                                                <th>Ngày đánh giá</th>
                                                <th>Ngày cập nhật</th>
                                                <th>Trạng thái</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach var="review" items="${reviews}">
                                                <tr>
                                                    <td><img class="tbl-thumb" src="data:image/png;base64,${review.productImage}" alt="product image"/></td>
                                                    <td>${review.productName}</td>
                                                    <td>${review.userName}</td>
                                                    <td>
                                                        <div class="ec-t-rate">
                                                            <c:forEach begin="1" end="${review.rating}" var="r">
                                                                <i class="mdi mdi-star is-rated"></i>
                                                            </c:forEach>
                                                            <c:forEach begin="${review.rating + 1}" end="5" var="r">
                                                                <i class="mdi mdi-star"></i>
                                                            </c:forEach>
                                                        </div>
                                                    </td>
                                                    <td>${review.content}</td>
                                                    <td>${review.dateCreated}</td>
                                                    <td>${review.dateUpdated}</td>
                                                    <td>${review.status == 1 ? "ACTIVE" : "INACTIVE"}</td>
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
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-change-review" data-id="${review.reviewItemId}" href="#modal-change-review">Đổi trạng thái
                                                                </a>
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
            </div>
            <div class="modal fade" id="modal-change-review" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                    <div class="modal-content">
                        <h3 class="modal-header border-bottom-0">Bạn có muốn thay đổi trạng thái đánh giá này</h3>
                        <div class="modal-footer px-4">
                            <button type="button" class="btn btn-secondary btn-pill"
                                    data-bs-dismiss="modal">Huỷ</button>
                            <a id="link-change" class="btn btn-danger btn-pill" >Xác nhận</a>
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
        <jsp:include page="/views/admin/common/footer.jsp"/>
    </div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
    $(window).on('load', function() {
        if(window.location.href.includes("error") || ${error != null}){
            $('#modal-error').modal('show');
        }
    });
    $(document).ready(function () {
        $('#modal-change-review').on('show.bs.modal', function (event) {
            let id = $(event.relatedTarget).attr('data-id');
            let link = "<%=request.getContextPath()%>/admin/review/editStatus?reviewItemId=" + id;
            document.getElementById('link-change').href = link
        });
    });
</script>
</body>
</html>
