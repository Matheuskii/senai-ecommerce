package br.com.senai.api_ecommerce.categoria;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

@Table(name = "categorias")
@Entity(name = "Categoria")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;


    public Categoria( DadosCadastroCategoria dados){
        this.nome = dados.nome();
        this.descricao = dados.descricao();
    }
}
