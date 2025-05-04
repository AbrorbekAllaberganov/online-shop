package com.example.shop.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse {
    String message;
    boolean status;
    Object data;

    public ApiResponse(String message, boolean status){
        this.message = message;
        this.status = status;
    }


}
