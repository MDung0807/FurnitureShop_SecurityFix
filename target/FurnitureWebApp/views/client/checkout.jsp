<%@ page import="utils.constants.ORDER_PAYMENT" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="cartItems" type="java.util.ArrayList<models.view_models.cart_items.CartItemViewModel>" scope="request"/>
<html>
<head>
  <meta charset="utf-8">
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
            <h1 class="breadcrumb__content--title text-white mb-10">Checkout</h1>
            <ul class="breadcrumb__content--menu d-flex">
              <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
              <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/cart/items">Giỏ hàng</a></li>
              <li class="breadcrumb__content--menu__items"><span class="text-white">Checkout</span></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End breadcrumb section -->

  <!-- Start checkout page area -->
  <div class="checkout__page--area section--padding">
    <div class="container">
      <div class="row">
        <form method="post" action="<%=request.getContextPath()%>/order/create">
          <div class="container">
            <div class="row">
              <div class="col-lg-7 col-md-6">
                <div class="main checkout__mian">
                  <div class="checkout__content--step section__shipping--address">
                    <div class="section__header mb-25">
                      <h2 class="section__header--title h3">Thông tin đơn hàng</h2>
                    </div>
                    <div class="section__shipping--address__content">
                      <div class="row">
                        <div class="col-12 mb-20">
                          <div class="checkout__input--list ">
                            <label class="checkout__input--label mb-5" for="name">Họ và tên <span class="checkout__input--label__star">*</span></label>
                            <input class="checkout__input--field border-radius-5" placeholder="Họ và tên" id="name" name="name" type="text" required>
                          </div>
                        </div>
                        <div class="col-12 mb-20">
                          <div class="checkout__input--list">
                            <label class="checkout__input--label mb-5" for="phone">Số điện thoại <span class="checkout__input--label__star">*</span></label>
                            <input class="checkout__input--field border-radius-5" placeholder="Số điện thoại" id="phone" name="phone" required pattern="[0-9]{10}" type="text">
                          </div>
                        </div>
                        <div class="col-12 mb-20">
                          <div class="checkout__input--list">
                            <label class="checkout__input--label mb-5" for="email">Email<span class="checkout__input--label__star">*</span></label>
                            <input class="checkout__input--field border-radius-5" placeholder="Email" id="email" name="email" type="email" required>
                          </div>
                        </div>
                        <div class="col-12 mb-20">
                          <div class="checkout__input--list">
                            <label class="checkout__input--label mb-5" for="address">Địa chỉ<span class="checkout__input--label__star">*</span></label>
                            <input class="checkout__input--field border-radius-5" placeholder="Địa chỉ" id="address" name="address" type="text" required>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="checkout__content--step__footer d-flex align-items-center">
                    <a class="continue__shipping--btn primary__btn border-radius-5" href="<%=request.getContextPath()%>/products">Tiếp tục mua sắm</a>
                    <a class="previous__link--content" href="<%=request.getContextPath()%>/cart/items">Giỏ hàng của bạn</a>
                  </div>
                </div>
              </div>
              <div class="col-lg-5 col-md-6">
                <aside class="checkout__sidebar sidebar border-radius-10">
                  <h2 class="checkout__order--summary__title text-center mb-15">Đơn hàng của bạn</h2>
                  <div class="cart__table checkout__product--table">
                    <table class="cart__table--inner">
                      <tbody class="cart__table--body">
                      <c:forEach var="c" items="${cartItems}">
                        <tr class="cart__table--body__items">
                          <td class="cart__table--body__list">
                            <div class="product__image two  d-flex align-items-center">
                              <div class="product__thumbnail border-radius-5">
                                <a class="display-block" href="<%=request.getContextPath()%>/product/details?productId=${c.productId}"><img class="display-block border-radius-5" src="data:image/png;base64, ${c.productImage}" alt="cart-product"></a>
                                <span class="product__thumbnail--quantity">${c.quantity}</span>
                              </div>
                              <div class="product__description">
                                <h4 class="product__description--name"><a href="<%=request.getContextPath()%>/product/details?productId=${c.productId}">${c.productName}</a></h4>
                              </div>
                            </div>
                          </td>
                          <td class="cart__table--body__list">
                            <span class="cart__price">${c.totalPrice}</span>
                          </td>
                        </tr>
                      </c:forEach>
                      </tbody>
                    </table>
                  </div>
                  <div class="checkout__discount--code d-flex">
                      <label>
                        <input class="checkout__discount--code__input--field border-radius-5" name="discount" id="discount" placeholder="Mã giảm giá"  type="text">
                      </label>
                      <a id="apply-btn" <c:if test="${cartItems.size() == 0}">disabled style="background-color: #000000; color: #ffffff" </c:if> class="checkout__discount--code__btn primary__btn border-radius-5" <c:if test="${cartItems.size() > 0}">onclick="onApplyDiscount('<%=request.getContextPath()%>')"</c:if> >Áp dụng</a>
                  </div>
                  <p id="discountValidateMessage"></p>
                  <div class="checkout__total">
                    <table class="checkout__total--table">
                      <tbody class="checkout__total--body">
                      <tr class="checkout__total--items">
                        <td class="checkout__total--title text-left">Tổng tiền sản phẩm</td>
                        <td class="checkout__total--amount text-right">
                          <input value="${totalItemPrice}" name="totalItemPrice" class="primary__btn" size="8" type="text" readonly/>
                        </td>
                      </tr>
                      <tr class="checkout__total--items">
                        <td class="checkout__total--title text-left">Phí vận chuyển</td>
                        <td class="checkout__total--calculated__text text-right">
                          <input value="${shipping}" name="shipping" class="primary__btn" size="8" type="text" readonly/>
                        </td>
                      </tr>
                      <tr class="checkout__total--items">
                        <td class="checkout__total--title text-left">Giảm giá</td>
                        <td class="checkout__total--calculated__text text-right">
                          <input value="${discount} %" name="discountValue" id="discountValue" class="primary__btn" size="8" type="text" readonly/>
                          <input type="hidden" value="" name="discountId" id="discountId" />
                        </td>
                      </tr>
                      </tbody>
                      <tfoot class="checkout__total--footer">
                      <tr class="checkout__total--footer__items">
                        <td class="checkout__total--footer__title checkout__total--footer__list text-left">Tổng tiền </td>
                        <td class="checkout__total--footer__amount checkout__total--footer__list text-right">
                          <input value="${totalPrice}" name="totalPrice" id="totalPrice" class="primary__btn" size="8" type="text" readonly/>
                        </td>
                      </tr>
                      </tfoot>
                    </table>
                  </div>
                  <div class="payment__history mb-30">
                    <h3 class="payment__history--title mb-20">Phương thức thanh toán</h3>
                    <select class="product__view--select payment__history--inner" name="payment">
                      <option value="<%=ORDER_PAYMENT.PAYPAL%>">PayPal</option>
                      <option value="<%=ORDER_PAYMENT.COD%>">COD</option>
                    </select>
                  </div>
                  <button <c:if test="${cartItems.size() == 0}">disabled style="background-color: #000000; color: #ffffff" </c:if> class="checkout__now--btn primary__btn" type="submit">Thanh toán</button>
                </aside>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- End checkout page area -->
</main>

<jsp:include page="/views/client/common/footer.jsp" />

<script src="<%=request.getContextPath()%>/assets/admin/plugins/jquery/jquery-3.5.1.min.js"></script>
<jsp:include page="/views/client/common/common_js.jsp"/>
<script src="<%=request.getContextPath()%>/assets/client/js/app/order/order_handler.js"></script>
</body>
</html>
