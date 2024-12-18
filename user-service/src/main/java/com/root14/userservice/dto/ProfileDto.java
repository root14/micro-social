package com.root14.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileDto {
    private String phone;
    private String fullName;
    private String location;
    private String link;
    private String image;
    private String bio;
}
