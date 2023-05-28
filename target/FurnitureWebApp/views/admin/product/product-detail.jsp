<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="product" scope="request" class="models.view_models.products.ProductViewModel"/>
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
            <h1>Product Detail</h1>
            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
              <span><i class="mdi mdi-chevron-right"></i></span>Product
            </p>
          </div>
          <div>
            <a href="<%=request.getContextPath()%>/admin/product/edit?productId=${product.productId}" class="btn btn-primary"> Edit
            </a>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="card card-default">
              <div class="card-header card-header-border-bottom">
                <h2>Product Detail</h2>
              </div>

              <div class="card-body product-detail">
                <div class="row">
                  <div class="col-xl-4 col-lg-8">
                    <div class="row">
                      <div class="single-pro-img">
                        <div class="row single-product-scroll">
                          <div class="single-product-cover">
                            <div class="single-slide zoom-image-hover">
                              <img class="img-responsive"
                                   src="data:image/png;base64, ${product.image}" alt="">
                            </div>
                            <c:forEach var="productImg" items="${product.productImages}">
                              <div class="single-slide zoom-image-hover">
                                <img class="img-responsive"
                                     src="data:image/png;base64, ${productImg.image}" alt="">
                              </div>
                            </c:forEach>
                          </div>
                          <div class="row single-nav-thumb">
                            <div class="single-slide">
                              <img class="img-responsive"
                                   src="data:image/png;base64, ${product.image}" alt="">
                            </div>

                            <c:forEach var="productImg" items="${product.productImages}">
                              <div class="single-slide">
                                <img class="img-responsive"
                                     src="data:image/png;base64, ${productImg.image}" alt="">
                              </div>
                            </c:forEach>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-xl-4 col-lg-8">
                    <div class="row product-overview">
                      <div class="col-12">
                        <h5 class="product-title">${product.name}</h5>
                        <p class="product-rate">
                          <c:forEach var="r" begin="1" end="${product.avgRating}">
                            <i class="mdi mdi-star is-rated"></i>
                          </c:forEach>
                          <c:forEach var="r" begin="${product.avgRating}" end="5">
                            <i class="mdi mdi-star"></i>
                          </c:forEach>
                        </p>
                        <p class="product-desc">
                          ${product.description}
                        </p>
                        <p class="product-price">Price: ${product.price}</p>

                        <div class="product-stock">
                          <div class="stock">
                            <p class="title">Available</p>
                            <p class="text">${product.quantity}</p>
                          </div>
                          <div class="stock">
                            <p class="title">InOrder</p>
                            <p class="text">${product.totalPurchased}</p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                <div class="row review-rating mt-4">
                  <div class="col-12">
                    <ul class="nav nav-tabs" id="myRatingTab" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link"
                           id="product-reviews-tab" data-bs-toggle="tab"
                           data-bs-target="#productreviews" href="#productreviews"
                           role="tab" aria-selected="false">
                          <i class="mdi mdi-star-half mr-1"></i> Reviews</a>
                      </li>
                    </ul>
                    <div class="tab-content" id="myTabContent2">
                      <div class="tab-pane pt-3 fade" id="productreviews" role="tabpanel">
                        <div class="ec-t-review-wrapper">
                          <c:forEach var="r" items="${product.productReviews}">
                            <div class="ec-t-review-item">
                              <div class="ec-t-review-avtar">
                                <img src="data:image/png;base64, ${r.userAvatar}" alt="">
                              </div>
                              <div class="ec-t-review-content">
                                <div class="ec-t-review-top">
                                  <p class="ec-t-review-name">${r.userName}</p>
                                  <div class="ec-t-rate">
                                    <c:forEach var="rate" begin="1" end="${r.rating}">
                                      <i class="mdi mdi-star is-rated"></i>
                                    </c:forEach>
                                    <c:forEach var="rate" begin="${r.rating}" end="5">
                                      <i class="mdi mdi-star"></i>
                                    </c:forEach>
                                  </div>
                                </div>
                                <div class="ec-t-review-bottom">
                                  <p>
                                      ${r.content}
                                  </p>
                                </div>
                              </div>
                            </div>
                          </c:forEach>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
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

</body>
</html>
