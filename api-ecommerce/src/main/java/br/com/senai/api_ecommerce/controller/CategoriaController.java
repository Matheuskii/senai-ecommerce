package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

   // @GetMapping()
    //public List<DadosListagemCategoria> listarCategorias(){
    //    Pageable pageado = Pageable.ofSize(3);

    //    return repository.findAll(pageado)
    //             .stream()
    //            .map(DadosListagemCategoria::new)
    //            .toList();
    //  }

    @GetMapping()
    public Page<DadosListagemCategoria> listarCategorias(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){

        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCategoria::new);
    }

    @PutMapping
    @Transactional
    public void atualizarCategoria(@RequestBody  @Valid DadosAtualizarCategoria dados){
        var  categoria = repository.getReferenceById(dados.id());
        categoria.atualizarCategoria(dados);

    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void deletarCategoria(@PathVariable Long id){
//            repository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
  @Transactional
  public void deletarCategoria(@PathVariable Long id){
           var categoria =  repository.getReferenceById(id);
           categoria.excluirCategoria();
    }


    @GetMapping("/{id}")
    public DadosDetalhesCategoria detalharCategoria(@PathVariable Long id){

        Categoria categoria = repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Tomale, pau!"));

       return new DadosDetalhesCategoria(categoria);
    }

}
