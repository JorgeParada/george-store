package com.example.georgestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
