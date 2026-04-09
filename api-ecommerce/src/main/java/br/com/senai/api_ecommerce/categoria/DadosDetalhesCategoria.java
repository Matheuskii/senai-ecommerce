package br.com.senai.api_ecommerce.categoria;

public record DadosDetalhesCategoria(Long id,
                                     String nome,
                                     String descricao
) {
   public DadosDetalhesCategoria(Categoria categoria){
       this(categoria.getId(), categoria.getNome(), categoria.getDescricao());
   }
}
