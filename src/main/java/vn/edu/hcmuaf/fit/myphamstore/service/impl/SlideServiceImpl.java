package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import vn.edu.hcmuaf.fit.myphamstore.dao.ISlideDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.SlideModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ISlideService;

import java.util.List;

public class SlideServiceImpl implements ISlideService {
    @Inject
    private ISlideDAO slideDAO;
    @Override
    public SlideModel findSlideById(Long id) {
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
        slideDAO.save(slideModel);
    }

    @Override
    public void delete(SlideModel id) {
        slideDAO.delete(id);
    }

    @Override
    public List<SlideModel> findAll() {
        return slideDAO.findAll();
    }

    @Override
    public void deleteAll() {
        slideDAO.deleteAll();
    }
}
