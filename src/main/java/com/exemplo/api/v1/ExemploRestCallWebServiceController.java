package com.exemplo.api.v1;

import com.exemplo.domain.ObjectRequest;
import com.exemplo.domain.ObjectResponse;
import com.exemplo.exceptions.ApiExceptions;
import com.exemplo.json.ApiSuccess;
import com.exemplo.service.CallWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExemploRestCallWebServiceController {

    private final CallWebService webService;

    @GetMapping("/v1/cal-web-service")
    public ResponseEntity calWebService() throws ApiExceptions {
        ObjectRequest request = new ObjectRequest(34242342342l);
        ObjectResponse response = webService.callWebService(request);
        return ResponseEntity.ok(ApiSuccess.builder().data(response).build());
    }
}
