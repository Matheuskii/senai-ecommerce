package br.com.senai.api_ecommerce.endereco;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizarEndereco(

            String logradouro,

            String bairro,

            String cidade,

            @Pattern(regexp = "\\d{8}") //"\\d" = só números, "{8}" = precisa ter 8 números"
            String cep,

            @Pattern(regexp = "[A-Z]{2}")
            String uf,

            String numero,

            String complemento

    ) {
    }


