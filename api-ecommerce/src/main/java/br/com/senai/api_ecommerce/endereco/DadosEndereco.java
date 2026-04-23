package br.com.senai.api_ecommerce.endereco;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        @Pattern(regexp = "\\d{8}") //"\\d" = só números, "{8}" = precisa ter 8 números"
        String cep,

        @NotBlank
        @Pattern(regexp = "[A-Z]{2}")
        String uf,

        String numero,

        String complemento

) {
}
