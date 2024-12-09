package com.root14.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteDto {
    private String username;
    private String password;
    private String email;
}
