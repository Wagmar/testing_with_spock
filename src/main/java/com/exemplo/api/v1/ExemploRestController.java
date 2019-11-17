package com.exemplo.api.v1;

import com.exemplo.domain.Cliente;
import com.exemplo.exceptions.ApiExceptions;
import com.exemplo.json.ApiSuccess;
import com.exemplo.service.ClienteService;
import com.exemplo.validate.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.slog4j.SLogger;
import org.slog4j.SLoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ExemploRestController {

    private static final SLogger slog = SLoggerFactory.getLogger(ExemploRestController.class);

    private final ClienteService service;



    private final ClienteValidator clienteValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(clienteValidator);
    }


    @PostMapping("/v1/cliente/busca")
    public ResponseEntity serachCArd(@Valid  @RequestBody Cliente cliente) throws ApiExceptions {
        Cliente cartao = service.buscar(cliente);
        return ResponseEntity.ok(ApiSuccess.builder().data(cartao).build());
    }

    @PutMapping("/v1/cliente/atualiza")
    public ResponseEntity updateCArd(@Valid  @RequestBody Cliente cliente) throws ApiExceptions {
        service.atualizar(cliente);
        return ResponseEntity.ok(ApiSuccess.builder().code(2000).build());
    }

}
