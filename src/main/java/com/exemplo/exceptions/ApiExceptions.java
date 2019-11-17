package com.exemplo.exceptions;

import com.exemplo.error.Error;
import com.exemplo.error.ErrorSupport;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Map;

@RequiredArgsConstructor
public class ApiExceptions extends Exception {


    public ApiExceptions(Error error) {
        this(error, null, null);
    }

    public ApiExceptions(Error error, Map<String, ?> attributes) {
        this(error, null, null);
    }

    public ApiExceptions(Error error, Throwable cause) {
        this(error, null, cause);
    }

    public ApiExceptions(Error error, @Nullable String message, @Nullable Throwable cause) {
        super(actualMessage(error,message),cause);
    }

    private static String actualMessage(Error error, @Nullable String message) {
        return (message == null) ? error.message() : ErrorSupport.rewordMessage(error, message);
    }
}
