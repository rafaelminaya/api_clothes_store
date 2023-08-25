package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.BoletaVentaRequest;
import com.rminaya.clothes.store.clothes_store.domain.entities.BoletaVentaEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.KardexEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoletaVentaService {
    List<BoletaVentaEntity> findAll();
    Page<BoletaVentaEntity> findAll(Integer page);
    BoletaVentaEntity findById(Long id);
    Long save(BoletaVentaRequest boletaVenta);
    void deleteById(Long id);
    // kardex
    KardexEntity saveKardex(KardexEntity kardex);
    void anular(Long boletaVentaId);
}
