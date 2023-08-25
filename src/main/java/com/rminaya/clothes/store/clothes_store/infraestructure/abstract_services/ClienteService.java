package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.domain.entities.ClienteEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClienteService {
    List<ClienteEntity> findAll();
    Page<ClienteEntity> findAll(Integer page);
    ClienteEntity findById(Long id);
    Long save(ClienteEntity cliente);
    Long update(ClienteEntity cliente, Long id);
    void deleteById(Long id);
}
