package com.example.springframework.exception.handler;


import com.example.springframework.exception.enums.FriendlyMessageCodes;
import com.example.springframework.exception.exceptions.ProductNotCreateException;
import com.example.springframework.exception.utils.FriendlyMessageUtils;
import com.example.springframework.response.FriendlyMessage;
import com.example.springframework.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductNotCreateException.class)
    public InternalApiResponse<String> handleProductNotCreatedException(ProductNotCreateException exception){
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }
}
