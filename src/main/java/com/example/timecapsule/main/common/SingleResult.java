package com.example.timecapsule.main.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SingleResult<T> extends CommonResult {
    private T data;
}
