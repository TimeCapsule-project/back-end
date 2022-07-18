package com.example.timecapsule.capsule.dto.request;

import lombok.Data;

@Data
public class LocationRequest {
    Long capsuleId;
    Double latitude;
    Double longitude;
}
