package com.exemplo.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    @Getter @Setter
    private Long         cpf;
    @Getter @Setter
    private String       nome;
}
