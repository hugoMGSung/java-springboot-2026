package com.pknu26.studygroup.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pknu26.studygroup.config.FileProperties;
import com.pknu26.studygroup.dto.HomeContent;
import com.pknu26.studygroup.dto.SiteImage;
import com.pknu26.studygroup.mapper.SiteImageMapper;
import com.pknu26.studygroup.validation.SiteImageForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteImageService {
    private final SiteImageMapper siteImageMapper;
    private final FileProperties fileProperties;

    public List<SiteImage> getImageList() {
        return this.siteImageMapper.findAll();
    }

    // 홈화면에서만 사용하는 서비스 메서드
    public HomeContent getSiteImageListActive() {
        List<SiteImage> list = this.siteImageMapper.findAllActive();

        HomeContent homeImage = new HomeContent();

        for (SiteImage siteImage : list) {
            switch (siteImage.getImageKey()) {
                case "CAROUSEL_1_IMG" -> homeImage.setCaro1ImagePath(siteImage.getImagePath());
                case "CAROUSEL_2_IMG" -> homeImage.setCaro2ImagePath(siteImage.getImagePath());
                case "CAROUSEL_3_IMG" -> homeImage.setCaro3ImagePath(siteImage.getImagePath());
            }
        }
        return homeImage;
    }

    public SiteImage getImage(Long id) {
        return this.siteImageMapper.findById(id);
    }

    // 파일(이미지) 업로드 핵심
    public void create(SiteImageForm form) throws Exception {
        MultipartFile file = form.getImageFile();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일을 선택하세요.");
        }

        String imagePath = saveFile(file); // 파일 실제저장

        // DB에 담을 내용을 
        SiteImage siteImage = new SiteImage();
        siteImage.setImageKey(form.getImageKey().trim()); // "CAROUSEL_1_IMG  " -> "CAROUSEL_1_IMG"
        siteImage.setImagePath(imagePath);
        siteImage.setUseYn(form.getUseYn());

        this.siteImageMapper.insert(siteImage);
    }

    public void update(SiteImageForm form) throws Exception {
        SiteImage siteImage = this.siteImageMapper.findById(form.getId());
        if (siteImage == null) {
            throw new IllegalArgumentException("존재하지 않는 이미지입니다.");
        }

        MultipartFile file = form.getImageFile();
        if (file != null && !file.isEmpty()) {
            siteImage.setImagePath(saveFile(file));
        }

        siteImage.setImageKey(form.getImageKey().trim());
        siteImage.setUseYn(form.getUseYn());

        this.siteImageMapper.update(siteImage);
    }

    public void delete(Long id) {
        this.siteImageMapper.delete(id);
    }

    private String saveFile(MultipartFile file) throws Exception {
        String uploadDir = this.fileProperties.getUploadDir(); // D:/upload/studygroup
        String accessUrl = this.fileProperties.getAccessUrl(); // /upload/site

        File dir = new File(uploadDir);  
        if (!dir.exists()) { // 폴더가 존재하지 않으면
            dir.mkdir();  // 폴더 생성
        }

        // 파일 오리지널명 
        String originName = file.getOriginalFilename();
        String ext = ""; // 파일 확장자 .png, .jpg, .gif 

        // rabbit01.png
        if (originName != null && originName.lastIndexOf(".") != -1) {  
            ext = originName.substring(originName.lastIndexOf("."));
        }
        // 또 다른업로드에서 rabbit01.png를 업로드하면 충돌 --> 해결
        // 이름 새로변경 UUID(Universally Unique Identifier)
        String saveName = UUID.randomUUID() + ext;  // 550e8400-e29b-41d4-a716-446655440000.png 변경
        File dest = new File(uploadDir + saveName);  // 새로 저장할 파일객체 생성
        file.transferTo(dest);

        return accessUrl + saveName;
    }
}
