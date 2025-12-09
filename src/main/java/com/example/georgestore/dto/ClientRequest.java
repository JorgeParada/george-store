package com.example.georgestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;

    private String phone;
}
