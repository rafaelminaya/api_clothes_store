package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ProductoRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProductoResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProductoEntity;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common.CrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductoService extends CrudService<ProductoRequest, ProductoResponse, Long> {
    List<ProductoResponse> findAll();
    Page<ProductoResponse> findAll(Integer page);
    List<ProductoResponse> findByFiltroVenta(String termino);
    List<ProductoResponse> findByMarcaId(Long marcaId);
}
