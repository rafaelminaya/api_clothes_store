package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProveedorService {
    List<ProveedorEntity> findAll();
    Page<ProveedorEntity> findAll(Integer page);
    ProveedorEntity findById(Long id);
    Long save(ProveedorEntity proveedor);
    Long update(ProveedorEntity proveedor, Long id);
    void deleteById(Long id);
}
