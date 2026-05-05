package br.com.senai.api_ecommerce.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DadosAtualizarProduto(
        @Schema(description = "Id do produto", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Long id,

        @Schema(description = "Nome do produto", example = "Pipoca")

        @Size(min = 3,max = 100)
                String nome,

        @Schema(description = "Preço do produto", example = "23.3")

        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal preco,

        @Schema(description = "Código de barras", example = "123456789")

        @Pattern(regexp = "^\\S{1,20}$", message = "não pode conter espaço em branco")
        @Size(max = 20)
        String sku,

        @Schema(description = "Descrição do produto")

        @Size(max=255)
        String descricao,

        @Schema(description = "Quantidade do produto no estoque", pattern = "^\\S{1,20}$")

        @PositiveOrZero
        Long estoque,
        @Schema(description = "Id da categoria do produto", example = "2")

        Long categoriaId
){

}







