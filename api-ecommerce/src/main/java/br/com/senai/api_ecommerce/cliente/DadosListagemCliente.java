package br.com.senai.api_ecommerce.cliente;

import io.swagger.v3.oas.annotations.media.Schema;

public record DadosListagemCliente(
        @Schema(description = "ID do Cliente no BD", example = "23")

        Long id,
        @Schema(description = "Nome do cliente", example = "Joãozinho")

        String nome,
        @Schema(description = "Email do cliente", example = "joaozinho321@gmail.com")

        String email
) {
    public DadosListagemCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
}
