package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.ContactStatus;
import vn.edu.hcmuaf.fit.myphamstore.dao.IContactDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.BrandModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ContactModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IContactService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class ContactServiceImpl implements IContactService {
    //CDI container dependency injection (tiêm phụ thuộc) -> java servlet weld
    @Inject
    private IContactDAO contactDAO;

    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "CONTACT-SERVICE";

    @Override
    public ContactModel findContactById(Long id) {
        return contactDAO.findContactById(id);
    }

    @Override
    public List<ContactModel> getContactsWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        log.info(CLASS_NAME, "Lấy danh sách liên");
        return this.contactDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }

    @Override
    public void displayContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/contact/contact-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage")==null?"1": request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null?"5": request.getParameter("pageSize"));

        List<ContactModel> contacts = this.getContactsWithPaging(keyword, currentPage, pageSize, orderBy);
        Long totalPages = this.contactDAO.getTotalPage(5);

        log.info(CLASS_NAME, "Hiển thị danh sách liên hệ");
        request.setAttribute("contacts", contacts);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        dispatcher.forward(request, response);
    }

    @Override
    public void pending(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        //tến hành cập nhật trạng thái sản phẩm
        ContactModel contactModel = ContactModel.builder().id(id).build();
        contactModel.setStatus(ContactStatus.PENDING);
        ContactModel isSuccess = contactDAO.update(contactModel);
        if (isSuccess == null) {
            request.setAttribute("message", "Có lỗi xảy ra");
            log.error(CLASS_NAME, "Không thể cập nhật trạng thái liên hệ id: " + id);
        } else {
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            log.info(CLASS_NAME, "Cập nhật trạng thái liên hệ thành công id: " + id);
            this.displayContact(request, response);
        }
    }

    @Override
    public void done(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        //tến hành cập nhật trạng thái sản phẩm
        ContactModel contactModel = ContactModel.builder().id(id).build();
        contactModel.setStatus(ContactStatus.DONE);
        ContactModel isSuccess = contactDAO.update(contactModel);
        if (isSuccess == null) {
            request.setAttribute("message", "Có lỗi xảy ra");
            log.error(CLASS_NAME, "Không thể cập nhật trạng thái liên hệ id: " + id);
        } else {
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            log.info(CLASS_NAME, "Cập nhật trạng thái liên hệ thành công id: " + id);
            this.displayContact(request, response);
        }
    }



    @Override
    public Long getTotalPage(int i) {
        return contactDAO.getTotalPage(i);
    }
}
