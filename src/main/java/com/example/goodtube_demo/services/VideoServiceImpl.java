package com.example.goodtube_demo.services;

import com.amazonaws.services.s3.transfer.Upload;
import com.example.goodtube_demo.data.Dtos.UploadVideoResponse;
import com.example.goodtube_demo.data.Dtos.VideoDto;
import com.example.goodtube_demo.data.models.Video;
import com.example.goodtube_demo.data.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final S3Service s3Service;

    private final VideoRepository videoRepository;

    @Override
    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {

        String videoUrl = s3Service.upload(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        Video savedVideo = videoRepository.save(video);

        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    @Override
    public VideoDto editVideos(VideoDto videoDto) {

        Video savedVideo = getVideoById(videoDto.getId());

        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        videoRepository.save(savedVideo);

        return videoDto;
    }

    @Override
    public String uploadThumbnail(MultipartFile file, String videoId) {

        Video savedVideo = getVideoById(videoId);
        String thumbnailUrl = s3Service.upload(file);

        savedVideo.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(savedVideo);

        return thumbnailUrl;
    }

    @Override
    public VideoDto getVideoDetails(String videoId) {


        Video savedVideo = getVideoById(videoId);

        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(savedVideo.getVideoUrl());
        videoDto.setVideoStatus(savedVideo.getVideoStatus());
        videoDto.setDescription(savedVideo.getDescription());
        videoDto.setTags(savedVideo.getTags());
        videoDto.setTitle(savedVideo.getTitle());
        videoDto.setId(savedVideo.getId());
        return videoDto;
    }

    public Video getVideoById(String videoId){

        return videoRepository.findById(videoId).orElseThrow(()->
                new IllegalArgumentException("Cannot find video by id " + videoId));
    }
}
