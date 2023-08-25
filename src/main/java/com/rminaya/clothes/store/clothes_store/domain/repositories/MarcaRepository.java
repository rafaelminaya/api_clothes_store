package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {
    Page<MarcaEntity> findAllByEliminado(Boolean eliminado, Pageable pageable);
}
