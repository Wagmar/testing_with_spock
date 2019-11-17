package com.exemplo.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Classe envelope de respostas com sucesso na requisição
 **/

@Getter
@Builder
public class ApiSuccess<T> {

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Integer code;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String message;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private T data;

}
