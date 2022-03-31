package com.example.goodtube_demo.services;

import com.example.goodtube_demo.data.Dtos.UploadVideoResponse;
import com.example.goodtube_demo.data.Dtos.VideoDto;
import com.example.goodtube_demo.data.models.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    UploadVideoResponse uploadVideo(MultipartFile multipartFile);

    VideoDto editVideos(VideoDto videoDto);

    String uploadThumbnail(MultipartFile file, String videoId);

    VideoDto getVideoDetails(String videoId);
}
