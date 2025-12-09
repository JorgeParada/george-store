package com.example.georgestore.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Long clientId;
    private List<OrderItemRequest> items;
}
