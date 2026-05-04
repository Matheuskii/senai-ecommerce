package br.com.senai.api_ecommerce.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record DadosDetalhamentoProduto(
        @Schema(description = "Id do produto", example = "1" )
        Long id,
        @Schema(description = "Nome do produto", example = "Pipoca")
        String nome,
        @Schema(description = "Preço do produto", example = "23.3")
        BigDecimal preco,
                                       @Schema(description = "Código de barras", example = "123456789")
                                       String sku,
                                       @Schema(description = "Descrição do produto")
                                       String descricao,
        @Schema(description = "Quantidade do produto no estoque", pattern = "^\\S{1,20}$", example = "342")
                                       Long estoque,
                                       @Schema(description = "Nome da categoria do produto", example = "comida")
                                       String nomeCategoria) {


    public DadosDetalhamentoProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco(), produto.getSku(), produto.getDescricao(), produto.getEstoque(), produto.getCategoria().getNome());
    }
}

