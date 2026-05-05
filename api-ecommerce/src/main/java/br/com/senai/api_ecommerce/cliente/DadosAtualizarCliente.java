package br.com.senai.api_ecommerce.cliente;

import br.com.senai.api_ecommerce.endereco.DadosAtualizarEndereco;
import br.com.senai.api_ecommerce.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCliente(
        @Schema(description = "ID do Cliente no BD", example = "23")

        Long id,


        @Schema(description = "Nome do cliente",  minimum = "3", maximum = "100")
        @Size(min=3, max=100)
        String nome,

        @Schema(description = "Email do cliente", example = "joaozinho321@gmail.com")
        @Email
        String email,

        @Schema(description = "Telefone do cliente", example = "11 99999-9999", maximum = "20")

        @Size(max=20)
        String telefone,

        @Schema(implementation = Endereco.class, description = "Endereço embutido")

        @Valid
        DadosAtualizarEndereco endereco
) {
}
