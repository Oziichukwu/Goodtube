package com.example.goodtube_demo.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String fullName;

    private String emailAddress;

    private String sub;

    private Set<String> subscribedToUsers;

    private Set<String>subscribers;

    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();

    private Set<String>likedVideos = ConcurrentHashMap.newKeySet();

    private Set<String>disLikedVideos = ConcurrentHashMap.newKeySet();


    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDisLikedVideos(String videoId) {
        disLikedVideos.remove(videoId);
    }

    public void addToDisLikedVideos(String videoId) {
        disLikedVideos.add(videoId);
    }

    public void addToVideoHistory(String videoId) {
        videoHistory.add(videoId);
    }
}
