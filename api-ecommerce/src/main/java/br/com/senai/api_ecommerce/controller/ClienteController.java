package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.cliente.*;
import br.com.senai.api_ecommerce.produto.DadosDetalhamentoProduto;
import br.com.senai.api_ecommerce.produto.DadosListagemProduto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("clientes")
@Tag(name = "Cliente-controller", description="controller do cliente com GET, PUT, POST and DELETE")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo Cliente", description = "Salva os dados do cliente no BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso!!",  content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DadosDetalhamentoCliente.class))
            }),
            @ApiResponse(responseCode = "400", description = "Erro na requisição, verifique o JSON", content = @Content)

    })
    public void cadastrarCliente(@RequestBody @Valid DadosCadastroCliente dados){
        repository.save(new Cliente(dados));
    }


    @Operation(summary = "Listar todos os clientes")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso!!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosListagemCliente.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Id inválido", content = @Content)
    })
    @GetMapping
    public Page<DadosListagemCliente> listarCategorias(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCliente::new);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza os dados de um cliente", description = "Atualiza nome, email, telefone ou endereço")
    @ApiResponse(
            responseCode = "200",
            description = "Cliente atualizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DadosAtualizarCliente.class),
                    examples = @ExampleObject(
                            value = """
                {
                    "id": 23,
                    "nome": "João Silva",
                    "email": "joao.silva@email.com",
                    "telefone": "11999998888",
                    "endereco": {
                        "logradouro": "Avenida Paulista",
                        "numero": "1000",
                        "complemento": "Apto 12",
                        "bairro": "Bela Vista",
                        "cidade": "São Paulo",
                        "uf": "SP",
                        "cep": "01310-100"
                    }
                }
                """
                    )
            )
    )
    public void atualizarCliente(@RequestBody @Valid DadosAtualizarCliente dados){
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
    }

    @Operation(summary = "Excluir cliente", description = "Retira um cliente do BD")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado!", content = @Content),
    })
    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCliente(@PathVariable Long id){
        var cliente = repository.getReferenceById(id);
        cliente.excluirCliente();
    }


    @Operation(summary = "Listar cliente pot ID", description = "Listar o cliente de acordo com o ID da DB")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Cliente listado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCliente.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Id inválido", content = @Content)
    })
    @GetMapping("/{id}")
    public DadosDetalhamentoCliente detalharCliente(@PathVariable Long id){
        Cliente cliente = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não existe"
                ));
        return new DadosDetalhamentoCliente(cliente);
    }
}
