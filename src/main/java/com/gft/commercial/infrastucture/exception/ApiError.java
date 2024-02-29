package com.gft.commercial.infrastucture.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 8979419620294221306L;

    private String errorCode;
    private String message;
}
