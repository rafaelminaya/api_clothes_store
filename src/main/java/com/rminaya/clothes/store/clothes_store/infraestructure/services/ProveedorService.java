package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ProveedorRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProveedorResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.ProveedorRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IProveedorService;
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

@Service
@AllArgsConstructor
public class ProveedorService implements IProveedorService {

    private ProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorResponse> findAll() {
        return this.proveedorRepository.findAll()
                .stream()
                .filter(proveedor -> proveedor.getEliminado().equals(false))
                .map(proveedorEntity -> this.entityToResponse(proveedorEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        return this.proveedorRepository.findAllByEliminado(false, pageable)
                .map(proveedorEntity -> this.entityToResponse(proveedorEntity));
    }

    @Override
    @Transactional
    public ProveedorResponse create(ProveedorRequest request) {
        ProveedorEntity proveedorToPersist = ProveedorEntity.builder()
                .ruc(request.getRuc())
                .razonComercial(request.getRazonComercial())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .eliminado(false)
                .build();

        ProveedorEntity proveedorPersisted = this.proveedorRepository.save(proveedorToPersist);
        return this.entityToResponse(proveedorPersisted);
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorResponse read(Long id) {
        ProveedorEntity proveedorFromDB = this.proveedorRepository.findById(id)
                .filter(proveedor -> proveedor.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.proveedores.name()));

        return this.entityToResponse(proveedorFromDB);
    }

    @Override
    @Transactional
    public ProveedorResponse update(ProveedorRequest request, Long id) {

        ProveedorEntity proveedorToUpdate = this.proveedorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.proveedores.name()));

        proveedorToUpdate.setRuc(request.getRuc());
        proveedorToUpdate.setDireccion(request.getDireccion());
        proveedorToUpdate.setEmail(request.getEmail());
        proveedorToUpdate.setDireccion(request.getDireccion());
        proveedorToUpdate.setTelefono(request.getTelefono());
        ProveedorEntity proveedorUpdated = this.proveedorRepository.save(proveedorToUpdate);

        return this.entityToResponse(proveedorUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProveedorEntity proveedorToDelete = this.proveedorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.proveedores.name()));

        proveedorToDelete.setEliminado(true);
        this.proveedorRepository.save(proveedorToDelete);
    }

    private ProveedorResponse entityToResponse(ProveedorEntity proveedorEntity) {
        ProveedorResponse proveedorResponse = new ProveedorResponse();
        BeanUtils.copyProperties(proveedorEntity, proveedorResponse);
        return proveedorResponse;
    }
}
