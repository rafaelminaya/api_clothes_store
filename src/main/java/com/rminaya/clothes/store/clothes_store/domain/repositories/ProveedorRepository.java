package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
    Page<ProveedorEntity> findAllByEliminado(Boolean eliminado, Pageable pageable);
}
