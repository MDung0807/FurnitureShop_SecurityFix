<%@ page import="utils.constants.PRODUCT_STATUS" %>
<%@ page import="utils.constants.SORT_BY" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="products" scope="request" type="java.util.ArrayList<models.view_models.products.ProductViewModel>"/>
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
            <h1>Product</h1>
            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
              <span><i class="mdi mdi-chevron-right"></i></span>Product
            </p>
          </div>
          <div>
            <a href="<%=request.getContextPath()%>/admin/products?grid=true" class="btn btn-outline-info active">
              Grid
            </a>
            <a href="<%=request.getContextPath()%>/admin/products" class="btn btn-outline-success ">
              List
            </a>
            <a href="<%=request.getContextPath()%>/admin/product/add" class="btn btn-primary"> Add Product</a>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="card card-default">
              <div class="card-header card-header-border-bottom d-flex justify-content-between">
                <form class="container-fluid" action="<%=request.getContextPath()%>/admin/products">
                  <input type="hidden" name="grid" value="true"/>
                  <div class="row col-lg-12 col-md-12">
                      <div class="col-lg-9 col-md-9 p-space">
                        <input type="text" class="form-control" id="searchProduct" name="keyword"
                               placeholder="Tìm kếm theo tên sản phẩm">
                      </div>
                      <div class="col-lg-3 col-md-3 p-space">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                      </div>
                  </div>
                </form>
                <div class="card-bar mt-3">
                  <form class="container-fluid" action="<%=request.getContextPath()%>/admin/products">
                    <input type="hidden" name="grid" value="true"/>
                    <div class="row col-lg-12 col-md-12 ">
                        <div class="col-lg-3 col-md-3 p-space">
                          <span>Danh mục </span>
                          <select class="form-control" id="dropdownCategory" name="categoryId">
                            <option value="0">None</option>
                            <c:forEach var="c" items="${categories}">
                              <option <c:if test="${categoryId == c.categoryId}">selected</c:if> value="${c.categoryId}">${c.name}</option>
                            </c:forEach>
                          </select>
                        </div>
                        <div class="col-lg-3 col-md-3 p-space">
                          <span>Thương hiệu </span>
                          <select class="form-control" id="dropdownBrand" name="brandId">
                            <option value="0">None</option>
                            <c:forEach var="b" items="${brands}">
                              <option <c:if test="${brandId == b.brandId}">selected</c:if> value="${b.brandId}">${b.brandName}</option>
                            </c:forEach>
                          </select>
                        </div>
                        <div class="col-lg-3 col-md-3 p-space">
                          <span>Sắp xếp </span>
                          <select class="form-control" id="dropdownOrderBy" name="sortBy">
                            <c:forEach var="c" items="<%=SORT_BY.SortBy%>">
                            <option <c:if test="${sortBy == c.value}">selected</c:if> value="${c.value}">${c.key}</option>
                            </c:forEach>
                          </select>
                        </div>
                        <div class="col-lg-3 col-md-3 p-space mt-2">
                          <button type="submit" class="btn btn-success">Lọc</button>
                        </div>
                    </div>
                  </form>
                </div>
              </div>
              <div class="card-body">
                <div class="row">
                  <c:forEach var="product" items="${products}">
                    <div class="col-lg-3 col-md-4 col-sm-6">
                      <div class="card-wrapper">
                        <div class="card-container">
                          <div class="card-top">
                            <img class="card-image" src="data:image/png;base64, ${product.image}"
                                 alt="" />
                          </div>
                          <div class="card-bottom">
                            <h1 class="mt-2">${product.name}</h1>
                            <h6 class="mt-2">Danh mục: ${product.categoryName}</h6>
                            <h6 class="mt-2">Thương hiệu: ${product.brandName}</h6>
                            <h6 class="mt-2">Nguồn gốc: ${product.origin}</h6>
                            <div class="d-flex justify-content-between mt-2">
                              <p>${product.price} VND</p>
                              <p>${product.quantity}</p>
                            </div>
                            <p class="mt-2"><span class="${product.statusClass}">${product.statusCode}</span></p>
                          </div>
                          <div class="card-action">
                            <a href="<%=request.getContextPath()%>/admin/product/edit?productId=${product.productId}" class="card-edit">
                              <i class="mdi mdi-circle-edit-outline"></i>
                            </a>
                            <a href="<%=request.getContextPath()%>/admin/product/detail?productId=${product.productId}" class="card-preview">
                              <i class="mdi mdi-eye-outline"></i>
                            </a>
                            <a href="<%=request.getContextPath()%>/admin/product/delete?productId=${product.productId}" class="card-remove">
                              <i class="mdi mdi mdi-delete-outline"></i>
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:forEach>
                </div>
                <div class="row">
                  <nav aria-label="Page navigation example p-0">
                    <ul class="pagination pagination-seperated pagination-seperated-rounded">
                      <li class="page-item">
                        <a class="page-link" onclick="onClickLink(this, '${pageIndex - 1 > 0 ? pageIndex - 1 : 1}')" aria-label="Previous">
                                        <span aria-hidden="true"
                                              class="mdi mdi-chevron-left mr-1"></span> Prev
                          <span class="sr-only">Previous</span>
                        </a>
                      </li>
                      <c:forEach var="p" begin="1" end="${totalPage}">
                          <li class="page-item ${pageIndex == p ? "active" : ""}">
                            <a class="page-link" onclick="onClickLink(this, '${p}')">${p}</a>
                          </li>
                      </c:forEach>

                      <li class="page-item">
                        <a class="page-link" onclick="onClickLink(this, '${pageIndex + 1 <= totalPage ? pageIndex + 1 : totalPage}')" aria-label="Next">
                          Next
                          <span aria-hidden="true"
                                class="mdi mdi-chevron-right ml-1"></span>
                          <span class="sr-only">Next</span>
                        </a>
                      </li>
                    </ul>
                  </nav>
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
<script>
  if(${products.size() == 0}){
    document.querySelector(".card-body").innerHTML = `<p class="text-center " style="font-size: 30px; color:red;">Không có sản phẩm</p>`
  }
  function onClickLink(e, index){
    index = parseInt(index)
    let alteredURL = removeParam('pageIndex', window.location.href)
    if(!alteredURL.includes('?')) {
      alteredURL += '?pageIndex=' + index
    }
    else{
      alteredURL += '&pageIndex='+ index
    }
    e.href = alteredURL
  }
  function removeParam(key, sourceURL) {
    let rtn = sourceURL.split("?")[0],
            param,
            params_arr = [],
            queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
    if (queryString !== "") {
      params_arr = queryString.split("&");
      for (let i = params_arr.length - 1; i >= 0; i -= 1) {
        param = params_arr[i].split("=")[0];
        if (param === key) {
          params_arr.splice(i, 1);
        }
      }
      if (params_arr.length) rtn = rtn + "?" + params_arr.join("&");
    }
    return rtn;
  }
</script>
</body>
</html>
