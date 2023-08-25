package com.rminaya.clothes.store.clothes_store.domain.repositories;

import com.rminaya.clothes.store.clothes_store.domain.entities.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRespository extends JpaRepository<ClienteEntity, Long> {
    Page<ClienteEntity> findAllByEliminado(Boolean eliminado, Pageable pageable);

    Optional<ClienteEntity> findByNumeroDocumento(String numerDocumento);
}
