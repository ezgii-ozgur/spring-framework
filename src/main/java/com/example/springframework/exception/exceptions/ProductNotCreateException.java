package com.example.springframework.exception.exceptions;

import com.example.springframework.enums.Language;
import com.example.springframework.exception.enums.IFriendlyMessageCode;
import com.example.springframework.exception.utils.FriendlyMessageUtils;
import com.example.springframework.response.FriendlyMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotCreateException extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;


    public ProductNotCreateException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[Product not created exceptions] -> message : {}, developer message : {}",
                FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}
