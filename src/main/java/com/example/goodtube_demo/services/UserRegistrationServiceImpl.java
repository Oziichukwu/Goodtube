package com.example.goodtube_demo.services;


import com.example.goodtube_demo.data.Dtos.UserInfoDto;
import com.example.goodtube_demo.data.models.User;
import com.example.goodtube_demo.data.repositories.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService{

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;

    @Override
    public void registerUser(String tokenValue) {

        // make a call to the user info endpoint
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            UserInfoDto userInfoDto = objectMapper.readValue(body , UserInfoDto.class);

            User user = new User();
            user.setFirstName(userInfoDto.getGivenName());
            user.setLastName(userInfoDto.getFamilyName());
            user.setFullName(userInfoDto.getName());
            user.setEmailAddress(userInfoDto.getEmail());
            user.setSub(userInfoDto.getSub());

            userRepository.save(user);

        } catch (Exception e){
            throw new RuntimeException("Exception occur while trying to register user", e);
        }
        // fetch user details and save to the database

    }
}
