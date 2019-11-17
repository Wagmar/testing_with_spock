package com.exemplo.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * Classe envelope de erros comuns da API
 * 
 * @author fernando.arruda
 *
 */
@Getter
@Builder
public class ApiError {

	@JsonInclude(Include.NON_EMPTY)
	private Integer code;
	private String message;
	
	@JsonInclude(Include.NON_EMPTY)
	private String fieldName;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<ApiError> errors;

	public ApiError addError(Integer code, String message) {
		
		if(errors == null) {
			errors = Collections.emptyList();
		}
		
		errors.add(ApiError.builder()
							.code(code)
							.message(message).build());
		
		return this;
	}
}