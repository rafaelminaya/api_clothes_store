package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.GuiaRemisionRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.GuiaRemisionResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.CrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGuiaRemisionService extends CrudService<GuiaRemisionRequest, GuiaRemisionResponse, Long> {
    List<GuiaRemisionResponse> findAll();
    Page<GuiaRemisionResponse> findAll(Integer page);
}
