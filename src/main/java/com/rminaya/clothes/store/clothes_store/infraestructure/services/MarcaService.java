package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.MarcaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.MarcaRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IMarcaService;
import com.rminaya.clothes.store.clothes_store.util.enums.Tables;
import com.rminaya.clothes.store.clothes_store.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MarcaService implements IMarcaService {

    private MarcaRepository marcaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MarcaResponse> findAll() {
        return this.marcaRepository.findAll()
                .stream()
                .filter(marca -> marca.getEliminado().equals(false))
                .map(marcaEntity -> this.entityToResponse(marcaEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarcaResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        return this.marcaRepository.findAllByEliminado(false, pageable)
                .map(marcaEntity -> this.entityToResponse(marcaEntity));
    }

    @Override
    @Transactional
    public MarcaResponse create(MarcaRequest request) {

        MarcaEntity marcaToPersist = MarcaEntity.builder()
                .nombre(request.getNombre())
                .eliminado(false)
                .build();

        MarcaEntity marcaPersisted = this.marcaRepository.save(marcaToPersist);

        return this.entityToResponse(marcaPersisted);
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaResponse read(Long id) {
        MarcaEntity marcaFromDB = this.marcaRepository.findById(id)
                .filter(marca -> marca.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));

        return this.entityToResponse(marcaFromDB);
    }

    @Override
    @Transactional
    public MarcaResponse update(MarcaRequest request, Long id) {
        MarcaEntity marcaToUpdate = this.marcaRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));
        marcaToUpdate.setNombre(request.getNombre());
        MarcaEntity marcaUpdated = this.marcaRepository.save(marcaToUpdate);

        return this.entityToResponse(marcaUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MarcaEntity marcaToDelete = this.marcaRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));
        marcaToDelete.setEliminado(true);
        this.marcaRepository.save(marcaToDelete);
    }

    private MarcaResponse entityToResponse(MarcaEntity marcaEntity) {
        MarcaResponse marcaResponse = new MarcaResponse();
        BeanUtils.copyProperties(marcaEntity, marcaResponse);
        return marcaResponse;
    }
}
