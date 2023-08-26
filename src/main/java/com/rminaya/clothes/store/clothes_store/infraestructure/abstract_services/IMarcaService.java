package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.MarcaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.CrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMarcaService extends CrudService<MarcaRequest, MarcaResponse, Long> {
    List<MarcaResponse> findAll();
    Page<MarcaResponse> findAll(Integer page);
}
