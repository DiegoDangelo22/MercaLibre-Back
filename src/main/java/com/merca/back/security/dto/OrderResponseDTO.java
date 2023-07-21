package com.merca.back.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.merca.back.security.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseDTO {
    private String id;
    private OrderStatus status;
    private List<LinkDTO> links;
}