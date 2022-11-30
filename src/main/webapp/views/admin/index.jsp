<%@ page import="com.google.gson.Gson" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="customers" type="java.util.ArrayList<models.view_models.users.UserViewModel>" scope="request"/>
<jsp:useBean id="orders" type="java.util.ArrayList<models.view_models.orders.OrderViewModel>" scope="request"/>
<jsp:useBean id="statistics" type="models.view_models.orders.OrderOverviewViewModel" scope="request"/>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="Ekka - Admin Dashboard">

    <title>Ekka - Admin Dashboard eCommerce</title>
    <jsp:include page="/views/admin/common/common_css.jsp" />
</head>
    <body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-light ec-header-light" id="body" onload="updateChart()">
        <div class="wrapper">
            <jsp:include page="/views/admin/common/sidebar.jsp"/>
            <div class="ec-page-wrapper">
                <jsp:include page="/views/admin/common/header.jsp"/>
                <div class="ec-content-wrapper">
                    <div class="content">
                        <!-- Top Statistics -->
                        <div class="row">
                            <div class="col-xl-4 col-sm-6 p-b-15 lbl-card">
                                <div class="card card-mini dash-card card-1">
                                    <div class="card-body">
                                        <h2 class="mb-1">${totalUsers}</h2>
                                        <p>Tổng người dùng</p>
                                        <span class="mdi mdi-account-arrow-left"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-6 p-b-15 lbl-card">
                                <div class="card card-mini dash-card card-3">
                                    <div class="card-body">
                                        <h2 class="mb-1">${totalOrders}</h2>
                                        <p>Tổng đơn hàng</p>
                                        <span class="mdi mdi-package-variant"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-4 col-sm-6 p-b-15 lbl-card">
                                <div class="card card-mini dash-card card-4">
                                    <div class="card-body">
                                        <h2 class="mb-1">${totalRevenue} VND</h2>
                                        <p>Tổng doanh thu</p>
                                        <span class="mdi mdi-currency-usd"></span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xl-7 col-md-12 p-b-15">
                                <!-- New Customers -->
                                <div class="card ec-cust-card card-table-border-none card-default">
                                    <div class="card-header justify-content-between ">
                                        <h2>Top 10 khách hàng mua nhiều</h2>
                                    </div>
                                    <div class="card-body pt-0 pb-15px">
                                        <table class="table ">
                                            <thead>
                                                <tr>
                                                    <th>Người dùng</th>
                                                    <th>Số lượng đơn hàng</th>
                                                    <th>Số lượng sản phẩm đã mua</th>
                                                    <th>Tổng giá (VND)</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="u" items="${customers}">
                                                    <tr>
                                                        <td class="text-center">
                                                            <div class="media">
                                                                <div class="media-image mr-3 rounded-circle">
                                                                    <a href="<%=request.getContextPath()%>/admin/user/detail?userId=${u.id}"><img
                                                                            class="profile-img rounded-circle w-45"
                                                                            src="data:image/png;base64, ${u.avatar}"
                                                                            alt="customer image"></a>
                                                                </div>
                                                                <div class="media-body align-self-center">
                                                                    <a href="<%=request.getContextPath()%>/admin/user/detail?userId=${u.id}">
                                                                        <h6 class="mt-0 text-dark font-weight-medium">${u.firstName} ${u.lastName}</h6>
                                                                    </a>
                                                                    <small>${u.email}</small>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="text-center">${u.totalOrders}</td>
                                                        <td class="text-center">${u.totalBought}</td>
                                                        <td class="text-dark text-center d-none d-md-block">${u.totalCost}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xl-5 col-md-12 p-b-15">
                                <!-- Doughnut Chart -->
                                <div class="card card-default">
                                    <div class="card-header justify-content-center">
                                        <h2>Tổng quan trạng thái đơn hàng</h2>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="doChart"></canvas>
                                    </div>
                                    <div class="card-footer d-flex flex-wrap bg-white p-0">
                                        <div class="col-6">
                                            <div class="p-20">
                                                <ul class="d-flex flex-column justify-content-between">
                                                    <li class="mb-2"><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                                        style="color: #88aaf3"></i>Đã giao thành công</li>
                                                    <li class="mb-2"><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                                        style="color: #50d7ab "></i>Đang giao</li>
                                                    <li><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                           style="color: #9586cd "></i>Sẵn sàng chuyển đi</li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="col-6 border-left">
                                            <div class="p-20">
                                                <ul class="d-flex flex-column justify-content-between">
                                                    <li class="mb-2"><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                                        style="color: #f3d676"></i>Đang đợi</li>
                                                    <li class="mb-2"><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                                        style="color: #ed9090"></i>Đã huỷ</li>
                                                    <li><i class="mdi mdi-checkbox-blank-circle mr-2"
                                                           style="color: #a4d9e5"></i>Hoàn trả</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 p-b-15">
                                <!-- Recent Order Table -->
                                <div class="card card-table-border-none card-default recent-orders" id="recent-orders">
                                    <div class="card-header justify-content-between">
                                        <h2>Đơn hàng gần đây</h2>
                                        <div class="date-range-report">
                                            <span></span>
                                        </div>
                                    </div>
                                    <div class="card-body pt-0 pb-5">
                                        <table class="table card-table table-responsive table-responsive-large"
                                               style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Tên tài khoản</th>
                                                    <th>Tên người nhận</th>
                                                    <th class="d-none d-lg-table-cell">Tổng sản phẩm mua</th>
                                                    <th class="d-none d-lg-table-cell">Ngày tạo đơn hàng</th>
                                                    <th class="d-none d-lg-table-cell">Phương thức thanh toán</th>
                                                    <th class="d-none d-lg-table-cell">Ngày thanh toán</th>
                                                    <th class="d-none d-lg-table-cell">Tổng giá</th>
                                                    <th>Trạng thái</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="o" items="${orders}">
                                                    <tr>
                                                        <td>#${o.orderId}</td>
                                                        <td>${o.userName}</td>
                                                        <td>${o.name}</td>
                                                        <td class="d-none text-center d-lg-table-cell">${o.totalItem}</td>
                                                        <td class="d-none d-lg-table-cell">${o.dateCreated}</td>
                                                        <td class="d-none text-center d-lg-table-cell">${o.paymentMethod}</td>
                                                        <td class="d-none d-lg-table-cell">${o.dateDone}</td>
                                                        <td class="d-none d-lg-table-cell">${o.totalPrice} VND</td>
                                                        <td class="text-center">
                                                            <span class="${o.statusClass}">${o.statusCode}</span>
                                                        </td>
                                                        <td class="text-right">
                                                            <div class="dropdown show d-inline-block widget-dropdown">
                                                                <a class="dropdown-toggle icon-burger-mini" href="#"
                                                                   role="button" id="dropdown-recent-order${o.orderId}"
                                                                   data-bs-toggle="dropdown" aria-haspopup="true"
                                                                   aria-expanded="false" data-display="static"></a>
                                                                <ul class="dropdown-menu dropdown-menu-right">
                                                                    <li class="dropdown-item">
                                                                        <a href="<%=request.getContextPath()%>/admin/order/detail?orderId=${o.orderId}">Chi tiết</a>
                                                                    </li>
                                                                </ul>
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
                <jsp:include page="/views/admin/common/footer.jsp"/>
            </div>
        </div>
        <jsp:include page="/views/admin/common/common_js.jsp" />
    <script>
        function updateChart(){
            <%
                String json = new Gson().toJson(statistics);
            %>
            let obj = <%=json%>;
            let ctx = $('#doChart');
            let graph = new Chart(ctx, {
                type: "doughnut",
                data: {
                    labels: ["completed", "delivering", "ready", "pending", "canceled", "returned"],
                    datasets: [
                        {
                            label: ["completed", "delivering", "ready", "pending", "canceled", "returned"],
                            data: [obj["totalCompleted"],obj["totalDelivering"],obj["totalReady"],obj["totalPending"],obj["totalCanceled"],obj["totalReturned"] ],
                            backgroundColor: ["#88aaf3", "#50d7ab", "#9586cd", "#f3d676", "#ed9090", "#a4d9e5"],
                            borderWidth: 1
                            // borderColor: ['#88aaf3','#29cc97','#8061ef','#fec402']
                            // hoverBorderColor: ['#88aaf3', '#29cc97', '#8061ef', '#fec402']
                        }
                    ]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    cutoutPercentage: 75,
                    tooltips: {
                        callbacks: {
                            title: function(tooltipItem, data) {
                                return "Order : " + data["labels"][tooltipItem[0]["index"]];
                            },
                            label: function(tooltipItem, data) {
                                return data["datasets"][0]["data"][tooltipItem["index"]];
                            }
                        },
                        titleFontColor: "#888",
                        bodyFontColor: "#555",
                        titleFontSize: 12,
                        bodyFontSize: 14,
                        backgroundColor: "rgba(256,256,256,0.95)",
                        displayColors: true,
                        borderColor: "rgba(220, 220, 220, 0.9)",
                        borderWidth: 2
                    }
                }
            });
            graph.update();
        }
    </script>
    </body>
</html>
