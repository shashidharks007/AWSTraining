package com.es.security.requestandresponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
@Getter
@Setter
public class ApiResponse {

    private boolean error;
    private String message;
    private int statusCode;
    private Object responseData;
}
