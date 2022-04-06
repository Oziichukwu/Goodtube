package com.example.goodtube_demo.services;

import com.example.goodtube_demo.data.models.User;
import com.example.goodtube_demo.data.models.Video;
import com.example.goodtube_demo.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        String sub = ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
        return userRepository.findBySub(sub).orElseThrow(()->
                new IllegalArgumentException("Cannot find user with sub " + sub));
    }

    @Override
    public void addToLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedVideo(String videoId) {
       return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo ->
               likedVideo.equals(videoId));
    }

    @Override
    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser().getDisLikedVideos().stream().anyMatch(likedVideo ->
                likedVideo.equals(videoId));
    }

    @Override
    public void removeFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void addVideoToHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToVideoHistory(videoId);
        userRepository.save(currentUser);
    }
}
