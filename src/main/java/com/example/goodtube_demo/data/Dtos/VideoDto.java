package com.example.goodtube_demo.data.Dtos;

import com.example.goodtube_demo.data.models.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private String id;

    private String title;

    private String description;

    private Set<String> tags;

    private String videoUrl;

    private VideoStatus videoStatus;

    private String thumbnailUrl;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer viewCount;

}
