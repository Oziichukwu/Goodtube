package com.example.goodtube_demo.web.controllers;

import com.example.goodtube_demo.data.Dtos.UploadVideoResponse;
import com.example.goodtube_demo.data.Dtos.VideoDto;
import com.example.goodtube_demo.services.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/videos/")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file ){
        log.info("controller hit -> {}", 0);
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId ){
        log.info("controller hit -> {}", 0);
       return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto){

        return videoService.editVideos(videoDto);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable String videoId){
       return videoService.getVideoDetails(videoId);
    }
}

