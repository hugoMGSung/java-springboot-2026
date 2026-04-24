package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Site;

@Mapper
public interface SiteMapper {

    List<Site> findAll();

    List<Site> findAllActive(); // 사용여부 Y인 컨텐츠만 조회

    Site findById(Long id);

    Site findByContentKey(String contentKey);

    int countByContentKey(String contentKey);

    void insert(Site site);

    void update(Site site);

    void delete(Long id);
}
