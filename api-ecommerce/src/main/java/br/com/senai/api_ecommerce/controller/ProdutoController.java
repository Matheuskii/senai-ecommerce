package br.com.senai.api_ecommerce.controller;


import br.com.senai.api_ecommerce.categoria.Categoria;
import br.com.senai.api_ecommerce.categoria.CategoriaRepository;
import br.com.senai.api_ecommerce.produto.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//Adicionar a dependência: SpringDoc OpenAPI no pom.xml e atualizar as dependências do Maven
//
//Executar o projeto e checar se a documentação foi gerada, acessando:
//http://localhost:8080/swagger-ui/index.html
//
//Para personalizar essa rota, você pode editar o arquivo application.properties e adicionar a seguinte configuração:
//springdoc.swagger-ui.path=seucaminhoaqui

@RestController
@RequestMapping("produtos")
@Tag(name="Produtos-controller",description="Gerenciamento dos produtos do ecommerce")
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo produto", description = "Salva os dados do produto no BD")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "SKU já cadastrado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria inválida", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro na requisição, verifique o JSON", content = @Content)
    })
    public ResponseEntity<DadosDetalhamentoProduto> cadastrarProduto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DadosCadastroProduto.class),
                            examples = @ExampleObject(
                                    value = """
                                            { "nome": "Nome Produto",\t
                                            \t"preco": 21.00,
                                            \t"sku":"999999999",
                                            \t"descricao": "Descrição do produto",
                                            \t"estoque": 1,
                                            \t"categoriaId": 6}"""
                            )
                    )
            )
            @RequestBody @Valid DadosCadastroProduto dados){
        //1. Verificar se a categoria existe
        var categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria inválida"));
        //2. Verificar se SKU é único
        if(produtoRepository.existsBySkuAndAtivoTrue(dados.sku()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        //3. Criar o produto
        Produto produto = new Produto(dados, categoria);
        produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso!!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosListagemProduto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Id inválido", content = @Content)
    })
    public ResponseEntity<Page<DadosListagemProduto>> listarProdutos(




            @PageableDefault(size=10, sort={"nome"}) @ParameterObject Pageable paginacao){
        var page = produtoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemProduto::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar produto pot ID", description = "Listar o produto de acordo com o ID da DB")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produto listado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Id inválido", content = @Content)
    })
    public ResponseEntity<DadosDetalhamentoProduto> buscarProdutoPorId(
            @Parameter(description = "ID do produto", example = "2")
            @PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir Produto", description = "Retira um produto do BD")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado!", content = @Content),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity excluirProduto(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        produto.excluirProduto();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso!!",
                    content = @Content(schema = @Schema(implementation = DadosDetalhamentoProduto.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado!",
                    content = @Content),
    })
    public ResponseEntity<DadosDetalhamentoProduto> atualizarProduto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para atualização do produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DadosAtualizarProduto.class), // Link direto com o DTO
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "nome": "Smartphone Atualizado",
                                          "preco": 1200.50,
                                          "categoriaId": 2
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid DadosAtualizarProduto dados
    ){
        //1. Verificar se o produto existe
        var produto = produtoRepository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        //2. Verificar se a categoria existe
        Categoria categoria = null;
        if(dados.categoriaId() != null) {
            categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        }
        //3. Verificar se SKU é único
        if(dados.sku()!=null && !dados.sku().isBlank()) {
            if (produtoRepository.existsBySkuAndAtivoTrue(dados.sku()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        }

        produto.atualizarProduto(dados, categoria);

        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }
}