package vn.edu.hcmuaf.fit.myphamstore.controller.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.action.AdminAction;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;

import java.io.IOException;

@WebServlet(name = "AdminProductManagement", value = "/admin/products")
public class ProductController extends HttpServlet {
    @Inject
    private IProductService productService;
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equalsIgnoreCase(AdminAction.DISPLAY) || action.equalsIgnoreCase(AdminAction.SEARCH) || action.isEmpty()) {
            productService.displayProduct(request, response);
        } else if (action.equalsIgnoreCase(AdminAction.STOP_BUYING)) {
            productService.stopBuying( request, response);
        }else if (action.equalsIgnoreCase(AdminAction.START_BUYING)) {
            productService.startBuying(request, response);
        }else if (action.equalsIgnoreCase(AdminAction.ADD)) {
            productService.addProduct(request, response);
        } else if (action.equalsIgnoreCase(AdminAction.EDIT)) {
            productService.updateProduct(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //them sua xoa san
        String action = request.getParameter("action");
        if(action.equalsIgnoreCase(AdminAction.INSERT)){
            productService.executeAddProduct(request, response);
        }else if (action.equalsIgnoreCase(AdminAction.EDIT)) {
            productService.executeUpdateProduct(request, response);
        }
    }

}
