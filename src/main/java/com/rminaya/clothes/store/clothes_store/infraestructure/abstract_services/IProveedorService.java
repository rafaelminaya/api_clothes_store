package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ProveedorRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProveedorResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.CrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProveedorService extends CrudService<ProveedorRequest, ProveedorResponse, Long> {
    List<ProveedorResponse> findAll();
    Page<ProveedorResponse> findAll(Integer page);
}
