package br.com.senai.api_ecommerce.produto;

import io.micrometer.observation.ObservationFilter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsBySku(String sku);

    Optional<Produto> findByIdAndAtivoTrue(Long id);

    Page<Produto> findAllByAtivoTrue(Pageable paginacao);

    boolean existsBySkuAndAtivoTrue(String sku);
}
