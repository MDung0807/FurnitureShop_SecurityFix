<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="reviewItems" type="java.util.ArrayList<models.view_models.review_items.ReviewItemViewModel>" scope="request"/>
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
                        <h1 class="breadcrumb__content--title text-white mb-10">Đánh giá sản phẩm</h1>
                        <ul class="breadcrumb__content--menu d-flex">
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li class="breadcrumb__content--menu__items"><a class="text-white" href="<%=request.getContextPath()%>/my-account">Đơn hàng của tôi</a></li>
                            <li class="breadcrumb__content--menu__items"><span class="text-white">Đánh giá sản pẩm</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End breadcrumb section -->

    <!-- Start about section -->
    <section class="about__section section--padding mb-95">
        <div class="container">
            <div class="row">
                <div class="product__reviews">
                    <div class="product__reviews--header">
                        <a class="actions__newreviews--btn primary__btn" onclick="document.getElementById('writereview').style.display = 'block'">Thêm đánh giá</a>
                    </div>
                    <div class="reviews__comment--area">
                        <c:forEach var="review" items="${reviewItems}">
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
                                <a class="text-white primary__btn" href="<%=request.getContextPath()%>/my-account/order/review/edit?reviewItemId=${review.reviewItemId}">Chỉnh sửa</a>
                            </div>
                        </c:forEach>
                    </div>
                    <div id="writereview" style="display: none" class="reviews__comment--reply__area">
                        <form
                                <c:if test="${productReview == null}">
                                    action="<%=request.getContextPath()%>/my-account/order/review/add"
                                </c:if>
                                <c:if test="${productReview != null}">
                                    action="<%=request.getContextPath()%>/my-account/order/review/edit"
                                </c:if>
                                method="post"
                        >
                            <h3 class="reviews__comment--reply__title mb-15">Viết đánh giá</h3>
                            <div class="reviews__ratting d-flex align-items-center mb-20">
                                <ul class="rating d-flex">
                                    <li class="rating__list">
                                        <span class="rating__list--icon">
                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                            <path data-index="1" id="star-1" onclick="onChangeRating(this)" data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="currentColor"></path>
                                            </svg>
                                        </span>
                                    </li>
                                    <li class="rating__list">
                                        <span class="rating__list--icon">
                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                            <path data-index="2" id="star-2" onclick="onChangeRating(this)" data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="currentColor"></path>
                                            </svg>
                                        </span>
                                    </li>
                                    <li class="rating__list">
                                        <span class="rating__list--icon">
                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                            <path data-index="3" id="star-3" onclick="onChangeRating(this)" data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="currentColor"></path>
                                            </svg>
                                        </span>
                                    </li>
                                    <li class="rating__list">
                                        <span class="rating__list--icon">
                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                            <path data-index="4" id="star-4" onclick="onChangeRating(this)" data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="currentColor"></path>
                                            </svg>
                                        </span>
                                    </li>
                                    <li class="rating__list">
                                        <span class="rating__list--icon">
                                            <svg class="rating__list--icon__svg" xmlns="http://www.w3.org/2000/svg" width="13.105" height="13.732" viewBox="0 0 10.105 9.732">
                                            <path data-index="5" id="star-5" onclick="onChangeRating(this)" data-name="star - Copy" d="M9.837,3.5,6.73,3.039,5.338.179a.335.335,0,0,0-.571,0L3.375,3.039.268,3.5a.3.3,0,0,0-.178.514L2.347,6.242,1.813,9.4a.314.314,0,0,0,.464.316L5.052,8.232,7.827,9.712A.314.314,0,0,0,8.292,9.4L7.758,6.242l2.257-2.231A.3.3,0,0,0,9.837,3.5Z" transform="translate(0 -0.018)" fill="currentColor"></path>
                                            </svg>
                                        </span>
                                    </li>
                                </ul>
                            </div>
                            <input type="hidden" name="productId" id="productId" value="${product.productId}">
                            <div class="row">
                                <div class="col-12 mb-10">
                                    <textarea name="content" id="content" class="reviews__comment--reply__textarea" placeholder="Đánh giá của bạn...." required>${productReview.content}</textarea>
                                </div>
                            </div>
                            <input type="hidden" id="rating" name="rating" value="${productReview == null ? 5 :productReview.rating}">
                            <input type="hidden" id="reviewItemId" name="reviewItemId" value="${productReview.reviewItemId}">
                            <button class="text-white primary__btn" data-hover="Submit" type="submit">Đánh giá</button>
                            <button class="text-white primary__btn" data-hover="Submit" onclick="document.getElementById('writereview').style.display = 'none'">Huỷ</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End about section -->

    <div class="modal" id="modal-error"  data-animation="slideInUp" style="z-index: 999;">
        <div class="modal-dialog quickview__main--wrapper">
            <h3 class="modal- border-bottom-0">Thao tác bị lỗi. Lưu ý: Chỉ có thể đánh giá sản phẩm 1 lần <br> nhưng bạn có thể chỉnh sửa</h3>
        </div>
    </div>
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<jsp:include page="/views/client/common/common_js.jsp"/>
<script>
    if(window.location.href.includes("error")){
        document.getElementById("modal-error").classList.add('is-visible')
    }
    if(${productReview != null}){
        document.getElementById('writereview').style.display = 'block'
        onChangeRating(document.getElementById("star-"+`${productReview.rating}`))
    }
    function onChangeRating(e){
        let index = parseInt(e.getAttribute("data-index"));
        for(let i =1; i<=5; i++){
            let str = ''
            if(i <= index){
                str = "currentColor"
            }else{
                str = ""
            }
            document.getElementById("star-" + i).setAttribute("fill", str)
        }
        document.getElementById('rating').value = index
    }
</script>
</body>
</html>
