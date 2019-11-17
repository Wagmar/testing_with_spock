package com.exemplo.service;

import com.exemplo.domain.ObjectRequest;
import com.exemplo.domain.ObjectResponse;
import com.exemplo.exceptions.ApiExceptions;

public interface CallWebService {
    ObjectResponse callWebService(ObjectRequest objectRequest) throws ApiExceptions;
}
