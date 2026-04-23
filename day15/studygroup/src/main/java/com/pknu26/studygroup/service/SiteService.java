package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.HomeContent;
import com.pknu26.studygroup.dto.Site;
import com.pknu26.studygroup.mapper.SiteMapper;
import com.pknu26.studygroup.validation.SiteForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteMapper siteMapper;

    public List<Site> getSiteList() {
        return siteMapper.findAll();
    }

    // useYn, Y인 데이터만 조회
    public HomeContent getSiteListActive() {
        List<Site> list = this.siteMapper.findAllActive();

        HomeContent homeContent = new HomeContent();

        for (Site site : list) {
            switch (site.getContentKey()) { 
                case "CAROUSEL_1_TITLE" -> homeContent.setCaro1Title(site.getContentBody());
                case "CAROUSEL_1_CONTENT" -> homeContent.setCaro1Content(site.getContentBody());
                case "CAROUSEL_2_TITLE" -> homeContent.setCaro2Title(site.getContentBody());
                case "CAROUSEL_2_CONTENT" -> homeContent.setCaro2Content(site.getContentBody());
                case "CAROUSEL_3_TITLE" -> homeContent.setCaro3Title(site.getContentBody());
                case "CAROUSEL_3_CONTENT" -> homeContent.setCaro3Content(site.getContentBody());
            }
        }
        return homeContent;
    }

    public Site getSite(Long id) {
        return siteMapper.findById(id);
    }

    public Site getSiteByContentKey(String contentKey) {
        return siteMapper.findByContentKey(contentKey);
    }

    public void delete(Long id) {
        siteMapper.delete(id);
    }

    public void create(SiteForm siteForm) {
        
        Site site = new Site();
        site.setContentKey(siteForm.getContentKey());
        site.setContentBody(siteForm.getContentBody());        

        siteMapper.insert(site);
    }

    public void update(SiteForm siteForm) {
        
        Site site = siteMapper.findById(siteForm.getId());
        site.setContentKey(siteForm.getContentKey());
        site.setContentBody(siteForm.getContentBody());

        siteMapper.update(site);
    }    
}
