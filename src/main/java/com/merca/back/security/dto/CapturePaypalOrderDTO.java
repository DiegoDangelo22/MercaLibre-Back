package com.merca.back.security.dto;

import com.merca.back.model.ImagenColor;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CapturePaypalOrderDTO {
    private String orderID;
    private Object[] items;
}