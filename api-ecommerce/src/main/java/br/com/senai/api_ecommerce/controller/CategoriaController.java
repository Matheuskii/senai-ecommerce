package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.Categoria;
import br.com.senai.api_ecommerce.categoria.CategoriaRepository;
import br.com.senai.api_ecommerce.categoria.DadosCadastroCategoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired //indica para o Spring que ele vai instaciar(criar) esse objeto
    private CategoriaRepository repository;

    @PostMapping
    @Transactional
    public DadosCadastroCategoria  cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria dados) {


            repository.save(new Categoria(dados));
            return dados;

    }

    @GetMapping()
    public List<Categoria> listarCategorias(){
        Pageable pageado = Pageable.ofSize(3);

        return repository.findAll(pageado).stream().toList();
    }
}
