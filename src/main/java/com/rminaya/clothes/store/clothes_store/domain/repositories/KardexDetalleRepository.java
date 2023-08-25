package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.KardexDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface KardexDetalleRepository extends JpaRepository<KardexDetalleEntity, Long> {
    @Query(value = "select * from kardex_detalle where producto_id = :producto_id and fecha_emision > :fecha_emision and eliminado = 0 order by fecha_emision asc, id asc", nativeQuery = true)
    List<KardexDetalleEntity> detallesByProductoAndFechaEmision(@Param("producto_id")Long productoId, @Param("fecha_emision") LocalDateTime fechaEmision);
}
