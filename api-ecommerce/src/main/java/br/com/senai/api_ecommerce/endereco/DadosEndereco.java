package br.com.senai.api_ecommerce.endereco;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @Schema(description= "nome da rua ou avenida", example="Avenida Brasil",  nullable = false)
        @NotBlank
        String logradouro,

        @Schema(description= "Nome do bairro", example= "Rochdale", nullable = false)
        @NotBlank
        String bairro,

        @Schema(description= "Nome da cidade", example= "Osasco", nullable = false)
        @NotBlank
        String cidade,


        @Schema(description= "CEP da localidade", example= "06220210", pattern="\\d{8}", nullable = false)
        @NotBlank
        @Pattern(regexp = "\\d{8}") //"\\d" = só números, "{8}" = precisa ter 8 números"
        String cep,


        @Schema(description = "uf da localidade", example="SP", pattern="[A-Z]{2}", nullable = false)
        @NotBlank
        @Pattern(regexp = "[A-Z]{2}")
        String uf,


        @Schema(description = "Número da casa ou residência", example = "174", nullable = true)
        String numero,


        @Schema(description = "Informação adicional da localidade", example = "Perto da padaria do Raphael Veiga")
        String complemento

) {
}
