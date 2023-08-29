package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.BoletaVentaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.BoletaVentaResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.SimpleCrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBoletaVentaService extends SimpleCrudService<BoletaVentaRequest, BoletaVentaResponse, Long> {
    List<BoletaVentaResponse> findAll();
    Page<BoletaVentaResponse> findAll(Integer page);
    void anular(Long boletaId);
}
