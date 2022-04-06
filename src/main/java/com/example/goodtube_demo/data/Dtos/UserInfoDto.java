package com.example.goodtube_demo.data.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private String id;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;
    private String email;
}
