package com.exemplo.service;

import com.exemplo.domain.ObjectRequest;
import com.exemplo.domain.ObjectResponse;
import com.exemplo.exceptions.ApiExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.exemplo.error.Errors.WEB_SERVICE_NAO_RESPONDE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultaWebServiceImpl implements CallWebService {

    private static final String URL = "http://192.168.0.1:6011/banana";

    private final RestTemplate restTemplate;

    @Override
    public ObjectResponse callWebService(ObjectRequest objectRequest) throws ApiExceptions {
        ObjectResponse response;
        try {
            response = restTemplate.postForObject(URL, objectRequest, ObjectResponse.class);
        }catch (HttpClientErrorException e){
            log.error(String.format("Erro na consulta do WS %s,", URL));
            throw new ApiExceptions(WEB_SERVICE_NAO_RESPONDE);
        }
        return response;

    }

}
