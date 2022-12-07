package controllers.client.wish;

import models.repositories.wish.WishRepository;
import models.view_models.users.UserViewModel;
import models.view_models.wish_items.WishItemCreateRequest;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddWish", value = "/add-wish")
public class AddWish extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        int productId = StringUtils.toInt(request.getParameter("productId"));

        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        if(user == null)
            return;
        int userId = user.getId();
        int wishId = WishRepository.getInstance().getWishIdByUserId(userId);

        WishItemCreateRequest createReq = new WishItemCreateRequest();
        createReq.setProductId(productId);
        createReq.setStatus(1);
        createReq.setWishId(wishId);

        int count = WishRepository.getInstance().insert(createReq);

        if(count <= 0){
            out.println("error");
        }else{
            user.setTotalWishListItem(user.getTotalWishListItem() + 1);
            session.setAttribute("user", user);
            out.println(user.getTotalWishListItem() + "success");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
