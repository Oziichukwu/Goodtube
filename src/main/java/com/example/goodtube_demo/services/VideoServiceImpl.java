package com.example.goodtube_demo.services;

import com.amazonaws.services.s3.transfer.Upload;
import com.example.goodtube_demo.data.Dtos.UploadVideoResponse;
import com.example.goodtube_demo.data.Dtos.VideoDto;
import com.example.goodtube_demo.data.models.Video;
import com.example.goodtube_demo.data.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final S3Service s3Service;

    private final VideoRepository videoRepository;

    private final UserService userService;

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

        incrementVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

        VideoDto videoDto = mapTOVideoDto(savedVideo);
        return videoDto;
    }



    @Override
    public VideoDto likeVideo(String videoId) {
    // get video by id
        Video videoById = getVideoById(videoId);
    // increment like count
    // if user already liked the video then decrement like count
    // like - 0 , dislike - 0
    // like - 1 , dislike - 0
    // like - 0 , dislike - 0
    // like - 0 , dislike - 1

    // if user already disliked the video then increment like count and decrement dislike count

        if (userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        }else if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videoById);


        return  mapTOVideoDto(videoById);

    }

    private VideoDto mapTOVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setId(videoById.getId());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }

    @Override
    public VideoDto disLikeVideo(String videoId) {
        Video videoById = getVideoById(videoId);

        if (userService.ifDisLikedVideo(videoId)){
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        }else if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }
        videoRepository.save(videoById);


        return mapTOVideoDto(videoById);


    }

    @Override
    public void incrementVideoCount(Video savedVideo) {
        savedVideo.incrementVideoCount();
        videoRepository.save(savedVideo);
    }

    public Video getVideoById(String videoId){
        return videoRepository.findById(videoId).orElseThrow(()->
                new IllegalArgumentException("Cannot find video by id " + videoId));
    }
}
