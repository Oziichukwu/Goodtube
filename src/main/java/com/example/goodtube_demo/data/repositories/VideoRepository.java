package com.example.goodtube_demo.data.repositories;

import com.example.goodtube_demo.data.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {

}
