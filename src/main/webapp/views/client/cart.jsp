<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="cartItems" type="java.util.ArrayList<models.view_models.cart_items.CartItemViewModel>" scope="request"/>
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
                        <h1 class="breadcrumb__content--title text-white mb-10">Giỏ hàng</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Giỏ hàng</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->

    <!-- cart section start -->
    <section class="cart__section section--padding">
        <div class="container-fluid">
            <div class="cart__section--inner">
                <form action="#">
                    <h2 class="cart__title mb-40">Giỏ hàng</h2>
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="cart__table">
                                <table class="cart__table--inner">
                                    <thead class="cart__table--header">
                                        <tr class="cart__table--header__items">
                                            <th class="cart__table--header__list">Sản phẩm</th>
                                            <th class="cart__table--header__list">Đơn giá</th>
                                            <th class="cart__table--header__list">Số lượng</th>
                                            <th class="cart__table--header__list">Trạng thái</th>
                                            <th class="cart__table--header__list">Tổng tiền</th>
                                        </tr>
                                    </thead>
                                    <tbody class="cart__table--body">
                                    <c:forEach var="c" items="${cartItems}" >
                                        <tr class="cart__table--body__items">
                                            <td class="cart__table--body__list">
                                                <div class="cart__product d-flex align-items-center">
                                                    <a id="cart-remove-${c.cartItemId}" class="cart__remove--btn" aria-label="search button"
                                                       data-open="modal-delete-cart" onclick="openCartItemModal(this)" data-cartItemId="${c.cartItemId}"
                                                    >
                                                        <svg fill="currentColor" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 24 24" width="16px" height="16px">
                                                            <path d="M 4.7070312 3.2929688 L 3.2929688 4.7070312 L 10.585938 12 L 3.2929688 19.292969 L 4.7070312 20.707031 L 12 13.414062 L 19.292969 20.707031 L 20.707031 19.292969 L 13.414062 12 L 20.707031 4.7070312 L 19.292969 3.2929688 L 12 10.585938 L 4.7070312 3.2929688 z">
                                                            </path>
                                                        </svg>
                                                    </a>
                                                    <div class="cart__thumbnail">
                                                        <a href="<%=request.getContextPath()%>/product/details?productId=${c.productId}"><img class="border-radius-5" src="data:image/png;base64, ${c.productImage}" alt="cart-product"></a>
                                                    </div>
                                                    <div class="cart__content">
                                                        <h4 class="cart__content--title"><a href="<%=request.getContextPath()%>/product/details?productId=${c.productId}">${c.productName}</a></h4>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="cart__table--body__list">
                                                <span id="cart-item-${c.cartItemId}-unitPrice" class="cart__price">${c.unitPrice}</span> VND
                                            </td>
                                            <td class="cart__table--body__list">
                                                <div class="quantity__box">
                                                    <button <c:if test="${c.quantity == 0}">disabled</c:if> data-cartItemId="${c.cartItemId}" onclick="updateCartItemQuantity(this, '<%=request.getContextPath()%>', '${c.quantity}')"  type="button" class="quantity__value quickview__value--quantity decrease" aria-label="quantity value" value="Decrease Value">-</button>
                                                    <label>
                                                        <input data-cartItemId="${c.cartItemId}" oninput="updateCartItemQuantity(this, '<%=request.getContextPath()%>', '${c.quantity}')" min="1" type="number" class="quantity__number quickview__value--number" id="cart-item-quantity-${c.cartItemId}" value="${c.quantity}" />
                                                    </label>
                                                    <button <c:if test="${c.quantity == 0}">disabled</c:if> data-cartItemId="${c.cartItemId}" onclick="updateCartItemQuantity(this, '<%=request.getContextPath()%>', '${c.quantity}')" type="button" class="quantity__value quickview__value--quantity increase" aria-label="quantity value" value="Increase Value">+</button>
                                                </div>
                                                <p id="over-quantity-${c.cartItemId}" class="continue__shopping--link"></p>
                                            </td>
                                            <td class="cart__table--body__list">
                                                <span class="cart__price end">${c.productStatus}</span>
                                            </td>
                                            <td class="cart__table--body__list">
                                                <span class="cart__price end total-price" id="cart-item-${c.cartItemId}-totalPrice">${c.totalPrice}</span><span> VND</span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="continue__shopping d-flex justify-content-between">
                                    <a class="continue__shopping--link" href="<%=request.getContextPath()%>/products">Tiếp tục mua sắm</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="cart__summary border-radius-10">
                                <div class="cart__summary--total mb-20">
                                    <table class="cart__summary--total__table">
                                        <tbody>
                                            <tr class="cart__summary--total__list">
                                                <td class="cart__summary--total__title text-left">Tổng tiền</td>
                                                <td id="total-item-price" class="cart__summary--amount text-right">${total} VND</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="cart__summary--footer">
                                    <p class="cart__summary--footer__desc">Tính tiền ở bước thanh toán</p>
                                    <p class="cart__summary--footer__desc">Lưu ý: Giỏ hàng phải có ít nhất 1 sản phẩm mới có thể thanh toán</p>
                                    <c:if test="${cartItems.size() > 0}">
                                        <ul class="d-flex justify-content-between">
                                            <li><a class="cart__summary--footer__btn primary__btn checkout" href="<%=request.getContextPath()%>/checkout">Mua hàng</a></li>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>
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


    <div class="modal" id="modal-delete-cart" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá sản phẩm này khỏi giỏ hàng</h3>
            <div class="quickview__inner">
                <button class="close-modal primary__btn" data-close>Huỷ</button>
                <a id="link-delete" class="primary__btn" onclick="deleteCartItem(this, '<%=request.getContextPath()%>')">Xoá</a>
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
    <div class="modal" id="modal-checkout-error" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Có lỗi xảy ra khi đặt hàng, vui lòng thử lại</h3>
        </div>
    </div>
    <div class="modal" id="modal-checkout-success" data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal-header border-bottom-0">Đặt hàng thành công, vui lòng kiểm tra email</h3>
        </div>
    </div>
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script src="<%=request.getContextPath()%>/assets/client/js/app/cart/cart-handler.js"></script>
<script>
    $(window).on('load', function() {
        if(window.location.href.includes("error") || ${error != null}){
            document.getElementById("modal-checkout-error").classList.add('is-visible')
        }else if(window.location.href.includes("success")){
            document.getElementById("modal-checkout-success").classList.add('is-visible')
        }
    });
</script>
</body>
</html>
