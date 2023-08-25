package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.GuiaRemisionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuiaRemisionRepository extends JpaRepository<GuiaRemisionEntity, Long> {
    Page<GuiaRemisionEntity> findAllByEliminado(Boolean eliminado, Pageable pageable);

    @Query("SELECT g FROM GuiaRemisionEntity AS g WHERE g.id = ?1 AND g.procesado = 0 AND g.eliminado = 0")
    Optional<GuiaRemisionEntity> findByIdAndNoProcesado(Long id);

    @Query("SELECT g FROM GuiaRemisionEntity AS g WHERE g.id = ?1 AND g.procesado = 1 AND g.eliminado = 0")
    Optional<GuiaRemisionEntity> findByIdAndProcesado(Long id);

    //@Query("select p from GuiaRemisionDetalle gt join fetch gt.producto p join fetch gt.guiaRemision g join fetch g.proveedor pr where pr.id = ?1 ")
    //@Query("select p from GuiaRemision g join fetch g.guiaRemisionDetalles gt join fetch gt.producto p join fetch g.proveedor pr where pr.id = ?1")
    //@Query("select p from Producto p join fetch p.guiaRemisionDetalles gt join fetch gt.producto p join fetch g.proveedor pr where pr.id = ?1")
    @Query("SELECT g FROM GuiaRemisionEntity AS g JOIN FETCH g.guiaRemisionDetalles AS gd JOIN FETCH g.proveedor AS pr WHERE pr.id = ?1")
    List<GuiaRemisionEntity> findByProveedor(Long proveedorId);

    //    @Query()
    //    List<GuiaRemision> findProductosVendidosByProveedor(Long proveedorId);
    //
    //    @Query(value = "", nativeQuery = true)
    //    List<IReporteLiquidacionDto> reporteKardexPorProducto(Long productoId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
