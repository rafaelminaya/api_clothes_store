package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Page<ProductoEntity> findAllByEliminado(Boolean eliminado, Pageable pageable);
    @Query("SELECT p FROM ProductoEntity as p JOIN p.marca as m WHERE m.id = ?1 and p.eliminado = 0")
    List<ProductoEntity> findByMarcaId(Long marcaId);
    @Query("SELECT p FROM ProductoEntity as p WHERE p.codigo LIKE %?1% OR p.nombre LIKE %?1%")
    List<ProductoEntity> findByFiltroVenta(String termino);
}
