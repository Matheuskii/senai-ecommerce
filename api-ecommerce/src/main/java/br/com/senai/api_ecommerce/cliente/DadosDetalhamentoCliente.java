package br.com.senai.api_ecommerce.cliente;

import br.com.senai.api_ecommerce.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

public record DadosDetalhamentoCliente(
        @Schema(description = "ID do Cliente no BD", example = "23")
        Long id,
        @Schema(description = "Nome do cliente", example = "Joãozinho")
        String nome,
        @Schema(description = "Email do cliente", example = "joaozinho321@gmail.com")
        String email,
        @Schema(description = "CPF do cliente", example = "222-222-222-41")

        String cpf,
        @Schema(description = "Telefone do cliente", example = "11 99999-9999")
        String telefone,

        @Schema(implementation = Endereco.class, description = "Endereço embutido")
        Endereco endereco
) {
    public DadosDetalhamentoCliente(Cliente cliente){
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone(),
        cliente.getEndereco());
    }
}
