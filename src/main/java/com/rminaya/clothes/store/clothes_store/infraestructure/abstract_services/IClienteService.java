package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ClienteRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ClienteResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.CrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClienteService extends CrudService<ClienteRequest, ClienteResponse, Long> {
    List<ClienteResponse> findAll();
    Page<ClienteResponse> findAll(Integer page);
}
