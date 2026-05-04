package br.com.senai.api_ecommerce.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record DadosListagemProduto(
        @Schema(description = "Id do produto", example = "1" )

        String nome,
        @Schema(description = "Preço do produto", example = "23.3")

        BigDecimal preco,
        @Schema(description = "Quantidade do produto no estoque", pattern = "^\\S{1,20}$", example = "342")

        Long estoque,
        @Schema(description = "Nome da categoria do produto", example = "comida")

        String nomeCategoria
) {

    public DadosListagemProduto(Produto produto){
        this(
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria().getNome()
        );
    }
}
