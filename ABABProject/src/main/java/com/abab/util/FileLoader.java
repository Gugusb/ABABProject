package com.abab.util;

import com.abab.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileLoader {
    public static ServerResponse<String> uploadVideo(MultipartFile video, String path){
        try {
            File file = new File(path, UUIDMaker.generationFileName()+ Objects.requireNonNull(video.getOriginalFilename()).substring(video.getOriginalFilename().lastIndexOf(".")));
            video.transferTo(file);
            String newPath = file.getPath().replace("\\", "/");
            return ServerResponse.createRespBySuccess(RegexUtil.getVideoRelativePath(newPath));
        } catch (IOException e) {
            return ServerResponse.createByErrorMessage("视频上传错误！");
        }
    }

    public static ServerResponse<String> uploadImage(MultipartFile image, String path){
        try {
            File file = new File(path, UUIDMaker.generationFileName()+ Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf(".")));
            image.transferTo(file);
            String newPath = file.getPath().replace("\\", "/");
            return ServerResponse.createRespBySuccess(RegexUtil.getImageRelativePath(newPath));
        } catch (IOException e) {
            System.out.println(e);
            return ServerResponse.createByErrorMessage("图片上传错误！");
        }
    }
}