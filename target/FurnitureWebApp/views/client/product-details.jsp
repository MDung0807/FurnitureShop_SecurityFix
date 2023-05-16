<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="product" type="models.view_models.products.ProductViewModel" scope="request"/>
<html>
<head>
    <meta charset="utf-8">
    <title>Furea - Furniture Shop</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/client/img/favicon.ico">
    <jsp:include page="/views/client/common/common_css.jsp"/>
    <style>
        .swiper-wrapper{
            height: unset;
        }
    </style>
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
                        <h1 class="breadcrumb__content--title text-white mb-10">${product.name}</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/products">Shop</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">${product.name}</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->

    <!-- Start product details section -->
    <section class="product__details--section section--padding">
        <div class="container">
            <div class="row row-cols-lg-2 row-cols-md-2">
                <div class="col">
                    <div class="product__details--media">
                        <div class="product__media--preview  swiper">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <div class="product__media--preview__items">
                                        <a class="product__media--preview__items--link glightbox" data-gallery="product-media-preview" href=""><img class="product__media--preview__items--img" src="data:image/png;base64, ${product.image}" alt="product-media-img"></a>
                                        <div class="product__media--view__icon">
                                            <a class="product__media--view__icon--link glightbox" href="" data-gallery="product-media-preview">
                                                <svg class="product__media--view__icon--svg" xmlns="http://www.w3.org/2000/svg" width="22.51" height="22.443" viewBox="0 0 512 512"><path d="M221.09 64a157.09 157.09 0 10157.09 157.09A157.1 157.1 0 00221.09 64z" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-miterlimit="10" stroke-width="32" d="M338.29 338.29L448 448"></path></svg>
                                                <span class="visually-hidden">Media Gallery</span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <c:forEach var="img" items="${product.productImages}">
                                    <div class="swiper-slide">
                                        <div class="product__media--preview__items">
                                            <a class="product__media--preview__items--link glightbox" data-gallery="product-media-preview" href=""><img class="product__media--preview__items--img" src="data:image/png;base64, ${img.image}" alt="product-media-img"></a>
                                            <div class="product__media--view__icon">
                                                <a class="product__media--view__icon--link glightbox" href="" data-gallery="product-media-preview">
                                                    <svg class="product__media--view__icon--svg" xmlns="http://www.w3.org/2000/svg" width="22.51" height="22.443" viewBox="0 0 512 512"><path d="M221.09 64a157.09 157.09 0 10157.09 157.09A157.1 157.1 0 00221.09 64z" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="32"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-miterlimit="10" stroke-width="32" d="M338.29 338.29L448 448"></path></svg>
                                                    <span class="visually-hidden">Media Gallery</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="product__media--nav swiper">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <div class="product__media--nav__items">
                                        <img class="product__media--nav__items--img" src="data:image/png;base64, ${product.image}" alt="product-nav-img">
                                    </div>
                                </div>
                                <c:forEach  var="img" items="${product.productImages}">
                                    <div class="swiper-slide">
                                        <div class="product__media--nav__items">
                                            <img class="product__media--nav__items--img" src="data:image/png;base64, ${img.image}" alt="product-nav-img">
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="swiper__nav--btn swiper-button-next"></div>
                            <div class="swiper__nav--btn swiper-button-prev"></div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="product__details--info">
                        <form action="#">
                            <h2 class="product__details--info__title mb-15">${product.name}</h2>
                            <div class="product__details--info__price mb-10">
                                <span class="current__price">${product.price} VND</span>
                            </div>
                            <div class="product__details--info__rating d-flex align-items-center mb-15">
                                <ul class="rating product__list--rating d-flex">
                                    <c:forEach var="i" begin="1" end="5">
                                        <li class="rating__list">
                                            <span class="rating__list--icon">
                                                <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                                <path data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="${i <= product.avgRating ? "currentColor" : ""}"></path>
                                                </svg>
                                            </span>
                                        </li>
                                    </c:forEach>
                                    <li class="rating__list"><span class="rating__list--text">( ${product.avgRating}.0 )</span></li>
                                </ul>
                            </div>
                            <p class="product__details--info__desc mb-20">${product.description}</p>
                            <div class="product__variant">
                                <div class="product__variant--list quantity d-flex align-items-center mb-20">
                                    <a class="quickview__cart--btn primary__btn" id="add-cartitem" onclick="addCartItem(this,'<%=request.getContextPath()%>')" data-productId="${product.productId}">Thêm vào giỏ hàng</a>
                                </div>
                                <div class="product__variant--list mb-15">
                                    <a class="variant__wishlist--icon mb-15" id="add-wishlist" onclick="addWish(this,'<%=request.getContextPath()%>')" data-productId="${product.productId}" title="Add to wishlist">
                                        <svg class="quickview__variant--wishlist__svg" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 512 512"><path d="M352.92 80C288 80 256 144 256 144s-32-64-96.92-64c-52.76 0-94.54 44.14-95.08 96.81-1.1 109.33 86.73 187.08 183 252.42a16 16 0 0018 0c96.26-65.34 184.09-143.09 183-252.42-.54-52.67-42.32-96.81-95.08-96.81z" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></path></svg>
                                        Thêm vào Danh sách yêu thích
                                    </a>
                                </div>
                                <div class="product__variant--list mb-15">
                                    <div class="product__details--info__meta">
                                        <p class="product__details--info__meta--list"><strong>Tình trạng:</strong>  <span>${product.statusCode}</span> </p>
                                        <p class="product__details--info__meta--list"><strong>Thương hiệu:</strong>  <span>${product.brandName}</span> </p>
                                        <p class="product__details--info__meta--list"><strong>Nguồn gốc:</strong>  <span>${product.origin}</span> </p>
                                        <p class="product__details--info__meta--list"><strong>Danh mục:</strong>  <span>${product.categoryName}</span> </p>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End product details section -->

    <!-- Start product details tab section -->
    <section class="product__details--tab__section section--padding">
        <div class="container">
            <div class="row row-cols-1">
                <div class="col">
                    <ul class="product__details--tab d-flex mb-30">
                        <li class="product__details--tab__list active" data-toggle="tab" data-target="#description">Mô tả sản phẩm</li>
                        <li class="product__details--tab__list" data-toggle="tab" data-target="#reviews">Đánh giá sản phẩm</li>
                        <li class="product__details--tab__list" data-toggle="tab" data-target="#information">Thông tin thêm về sản phẩm</li>
                    </ul>
                    <div class="product__details--tab__inner border-radius-10">
                        <div class="tab_content">
                            <div id="description" class="tab_pane active show">
                                <div class="product__tab--content">
                                    <div class="product__tab--content__items mb-40 d-flex align-items-center">
                                        <div class="product__tab--content__thumbnail">
                                            <img class="product__tab--content__thumbnail--img display-block" src="data:image/png;base64, ${product.image}" alt="product-tab">
                                        </div>
                                        <div class="product__tab--content__right">
                                            <div class="product__tab--content__step mb-20">
                                                <h4 class="product__tab--content__title">${product.name}</h4>
                                                <p class="product__tab--content__desc">${product.description}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="reviews" class="tab_pane">
                                <div class="product__reviews">
                                    <div class="product__reviews--header">
                                        <h3 class="product__reviews--header__title mb-20">Customer Reviews</h3>
                                        <div class="reviews__ratting d-flex align-items-center">
                                            <ul class="rating d-flex">
                                                <c:forEach var="i" begin="1" end="5">
                                                    <li class="rating__list">
                                                        <span class="rating__list--icon">
                                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                                                <path data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="${i <= product.avgRating ? "currentColor" : ""}"></path>
                                                            </svg>
                                                        </span>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                            <span class="reviews__summary--caption">Tổng cộng ${product.productReviews.size()} reviews</span>
                                        </div>
                                    </div>
                                    <div class="reviews__comment--area">
                                        <c:forEach var="review" items="${product.productReviews}">
                                            <div class="reviews__comment--list d-flex">
                                                <div class="reviews__comment--thumbnail">
                                                    <img src="data:image.png;base64, ${review.userAvatar}" alt="comment-thumbnail">
                                                </div>
                                                <div class="reviews__comment--content">
                                                    <h4 class="reviews__comment--content__title">${review.userName}</h4>
                                                    <ul class="rating reviews__comment--rating d-flex mb-5">
                                                        <c:forEach var="i" begin="1" end="5">
                                                            <li class="rating__list">
                                                                <span class="rating__list--icon">
                                                                    <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                                                        <path data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="${i <= review.rating ? "currentColor" : ""}"></path>
                                                                    </svg>
                                                                </span>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                    <p class="reviews__comment--content__desc">${review.content}</p>
                                                    <span class="reviews__comment--content__date">${review.dateUpdated}</span>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div id="information" class="tab_pane">
                                <div class="product__tab--conten">
                                    <div class="product__tab--content">
                                        <div class="product__tab--content__step mb-30">
                                            <h4 class="product__tab--content__title mb-10">Tình trạng</h4>
                                            <p class="product__tab--content__desc">${product.statusCode}</p>
                                        </div>
                                        <div class="product__tab--content__step mb-30">
                                            <h4 class="product__tab--content__title mb-10">Thương hiệu</h4>
                                            <p class="product__tab--content__desc">${product.brandName}</p>
                                        </div>
                                        <div class="product__tab--content__step mb-30">
                                            <h4 class="product__tab--content__title mb-10">Danh mục</h4>
                                            <p class="product__tab--content__desc">${product.categoryName}</p>
                                        </div>
                                        <div class="product__tab--content__step mb-30">
                                            <h4 class="product__tab--content__title mb-10">Nguồn gốc</h4>
                                            <p class="product__tab--content__desc">${product.origin}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End product details tab section -->
    <div class="modal" id="modal-error" style="z-index: 100;" data-animation="slideInUp">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal- border-bottom-0">Sản phẩm đã có trong Danh sách yêu thích của bạn</h3>
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
<script src="<%=request.getContextPath()%>/assets/admin/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/client/js/app/product/product_handler.js"> </script>
<script src="<%=request.getContextPath()%>/assets/client/js/app/wishlist/wishlist_handler.js"></script>
<script src="<%=request.getContextPath()%>/assets/client/js/app/cart/cart-handler.js"></script>
</body>
</html>
