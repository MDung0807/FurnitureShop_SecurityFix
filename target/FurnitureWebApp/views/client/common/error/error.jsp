<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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
  <!-- Start error section -->
  <section class="error__section section--padding">
    <div class="container">
      <div class="row row-cols-1">
        <div class="col">
          <div class="error__content text-center">
            <img class="error__content--img mb-50" src="<%=request.getContextPath()%>/assets/client/img/other/404-thumb.webp" alt="error-img">
            <h2 class="error__content--title">Opps ! Looks like something went wrong.</h2>
            <p class="error__content--desc">Đã có lỗi xảy ra !!!!!</p>
            <a class="error__content--btn primary__btn" href="<%=request.getContextPath()%>/home">Quay về trang chủ</a>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End error section -->
</main>

<jsp:include page="/views/client/common/footer.jsp" />
<script src="<%=request.getContextPath()%>/assets/admin/plugins/jquery/jquery-3.5.1.min.js"></script>
<jsp:include page="/views/client/common/common_js.jsp"/>
</body>
</html>
