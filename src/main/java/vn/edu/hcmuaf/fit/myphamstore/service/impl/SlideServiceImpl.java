package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import vn.edu.hcmuaf.fit.myphamstore.dao.ISlideDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.SlideModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ISlideService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.util.List;

public class SlideServiceImpl implements ISlideService {
    @Inject
    private ISlideDAO slideDAO;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "SLIDE-SERVICE";
    @Override
    public SlideModel findSlideById(Long id) {
        log.info(CLASS_NAME, "Lấy  thông tin slide có id = " + id);
        return slideDAO.findSlideById(id);
    }

    @Override
    public List<SlideModel> pagingSlide(String keyword, int currentPage, int pageSize, String orderBy) {
        return List.of();
    }

    @Override
    public Long getTotalPage(int numOfItem) {
        return slideDAO.getTotalPage(numOfItem);
    }

    @Override
    public void save(SlideModel slideModel) {
        log.info(CLASS_NAME, "Lưu thông tin slide có id = " + slideModel.getId());
        slideDAO.save(slideModel);
    }

    @Override
    public void delete(SlideModel id) {
        log.info(CLASS_NAME, "Xóa thông tin slide có id = " + id.getId());
        slideDAO.delete(id);
    }

    @Override
    public List<SlideModel> findAll() {
        log.info(CLASS_NAME, "Lấy tất cả thông tin slide");
        return slideDAO.findAll();
    }

    @Override
    public void deleteAll() {
        log.info(CLASS_NAME, "Xóa tất cả thông tin slide");
        slideDAO.deleteAll();
    }
}
