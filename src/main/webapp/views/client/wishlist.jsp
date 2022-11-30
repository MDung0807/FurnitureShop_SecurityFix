<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="wishItems" type="java.util.ArrayList<models.view_models.wish_items.WishItemViewModel>" scope="request"/>
<jsp:useBean id="brands" type="java.util.ArrayList<models.view_models.brands.BrandViewModel>" scope="request"/>
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
                        <h1 class="breadcrumb__content--title text-white mb-10">Danh sách yêu thích</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Danh sách yêu thích</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->

    <!-- cart section start -->
    <section class="cart__section section--padding">
        <div class="container">
            <div class="cart__section--inner">
                <form action="#">
                    <h2 class="cart__title mb-40">Danh sách yêu thích</h2>
                    <div class="cart__table">
                        <table class="cart__table--inner">
                            <thead class="cart__table--header">
                                <tr class="cart__table--header__items">
                                    <th class="cart__table--header__list">Sản phẩm</th>
                                    <th class="cart__table--header__list">Giá</th>
                                    <th class="cart__table--header__list text-center">Trạng thái</th>
                                    <th class="cart__table--header__list text-right">Action</th>
                                </tr>
                            </thead>
                            <tbody class="cart__table--body">
                                <c:forEach var="w" items="${wishItems}">
                                <tr class="cart__table--body__items">
                                    <td class="cart__table--body__list">
                                        <div class="cart__product d-flex align-items-center">
                                            <a class="cart__remove--btn" aria-label="search button"
                                               data-open="modal-delete-wishitem" onclick="openWishModal(this)" data-wishItemId="${w.wishItemId}"
                                            ><svg fill="currentColor" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 24 24" width="16px" height="16px"><path d="M 4.7070312 3.2929688 L 3.2929688 4.7070312 L 10.585938 12 L 3.2929688 19.292969 L 4.7070312 20.707031 L 12 13.414062 L 19.292969 20.707031 L 20.707031 19.292969 L 13.414062 12 L 20.707031 4.7070312 L 19.292969 3.2929688 L 12 10.585938 L 4.7070312 3.2929688 z"></path></svg></a>
                                            <div class="cart__thumbnail">
                                                <a href="<%=request.getContextPath()%>/product/details?productId=${w.productId}"><img class="border-radius-5" src="data:image/png;base64, ${w.productImage}" alt="cart-product"></a>
                                            </div>
                                            <div class="cart__content">
                                                <h4 class="cart__content--title"><a href="<%=request.getContextPath()%>/product/details?productId=${w.productId}">${w.productName}</a></h4>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="cart__table--body__list">
                                        <span class="cart__price">${w.unitPrice}</span>
                                    </td>
                                    <td class="cart__table--body__list text-center">
                                        <span class="in__stock text__secondary">${w.productStatus}</span>
                                    </td>
                                    <td class="cart__table--body__list text-right">
                                        <a class="wishlist__cart--btn primary__btn" id="add-cartitem" onclick="addCartItem(this,'<%=request.getContextPath()%>')" data-productId="${w.productId}">Add To Cart</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="continue__shopping d-flex justify-content-between">
                            <a class="continue__shopping--link" href="<%=request.getContextPath()%>/home">Tiếp tục mua sắm</a>
                            <a class="continue__shopping--clear" href="<%=request.getContextPath()%>/products">Xem tất cả sản phẩm</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <!-- cart section end -->
    <!-- Start brand logo section -->
    <div class="brand__logo--section bg__secondary section--padding">
        <div class="container-fluid">
            <div class="row row-cols-1">
                <div class="col">
                    <div class="brand__logo--section__inner d-flex justify-content-center align-items-center">
                        <c:forEach var="b" items="${brands}">
                            <div class="brand__logo--items">
                                <img class="brand__logo--items__thumbnail--img" src="data:image/png;base64 ,${b.image}" alt="brand logo">
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End brand logo section -->


    <div class="modal" id="modal-delete-wishitem" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá sản phẩm này khỏi danh sách yêu thích</h3>
            <div class="quickview__inner">
                <button class="close-modal primary__btn" data-close>Huỷ</button>
                <a id="link-delete" class="primary__btn" onclick="deleteWishItem(this, '<%=request.getContextPath()%>')">Xoá</a>
            </div>
        </div>
    </div>
    <div class="modal" id="modal-error" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Thao tác lỗi, vui lòng thực hiện lại</h3>
        </div>
    </div>
    <div class="modal" id="modal-success" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Thêm thành công</h3>
        </div>
    </div>
    <div class="modal" id="modal-expired" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Sản phẩm đã hết hàng hoặc ngừng kinh doanh</h3>
        </div>
    </div>
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script src="<%=request.getContextPath()%>/assets/client/js/app/wishlist/wishlist_handler.js"></script>
<script src="<%=request.getContextPath()%>/assets/client/js/app/cart/cart-handler.js"></script>
</body>
</html>
