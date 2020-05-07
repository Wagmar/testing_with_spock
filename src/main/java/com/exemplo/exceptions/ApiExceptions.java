package com.exemplo.exceptions;

import com.exemplo.error.Error;
import com.exemplo.error.ErrorSupport;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

@Getter
public class ApiExceptions extends Exception {

    private final Error               error;
    private final Map<String, Object> attributes;

    public ApiExceptions(Error error) {
        this(error, null, null, null);
    }

    public ApiExceptions(Error error, Map<String, ?> attributes) {
        this(error, null, null, attributes);
    }

    public ApiExceptions(Error error, Throwable cause) {
        this(error, null, cause, null);
    }

    public ApiExceptions(Error error, @Nullable String message, @Nullable Throwable cause,
                                @Nullable Map<String, ?> attributes) {
        super(actualMessage(error, message), cause);
        this.error = error;
        this.attributes = (attributes != null) ? ImmutableMap.copyOf(attributes) : Collections.<String, Object>emptyMap();
    }

    private static String actualMessage(Error error, @Nullable String message) {
        return (message == null) ? error.message() : ErrorSupport.rewordMessage(error, message);
    }
}
