package com.example.goodtube_demo.services;

import com.example.goodtube_demo.data.models.User;
import com.example.goodtube_demo.data.models.Video;

public interface UserService {

    User getCurrentUser();

    void addToLikedVideos(String videoId);

    boolean ifLikedVideo(String videoId);

    boolean ifDisLikedVideo(String videoId);

    void removeFromLikedVideos(String videoId);

    void removeFromDisLikedVideos(String videoId);

    void addToDisLikedVideos(String videoId);

    void addVideoToHistory(String videoId);
}
