package com.example.timecapsule.capsule.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    String success;
    int code;
    String msg;
    List<String> word;
}
