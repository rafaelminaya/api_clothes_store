package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.domain.entities.GuiaRemisionEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGuiaRemisionService {
    List<GuiaRemisionEntity> findAll();
    Page<GuiaRemisionEntity> findAll(Integer page);
    GuiaRemisionEntity findById(Long id);
    Long save(GuiaRemisionEntity guiaRemision);
    Long update(GuiaRemisionEntity guiaRemision, Long id);
    void procesarKardex(Long guiaRemisionId);
    void desprocesarKardex(Long guiaRemisionId);
    void deleteById(Long id);
}
