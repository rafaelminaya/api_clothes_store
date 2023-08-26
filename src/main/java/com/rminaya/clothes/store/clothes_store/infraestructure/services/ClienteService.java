package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ClienteRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ClienteResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.ClienteEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.ClienteRespository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IClienteService;
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
public class ClienteService implements IClienteService {

    private ClienteRespository clienteRespository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return this.clienteRespository.findAll()
                .stream()
                .filter(cliente -> cliente.getEliminado().equals(false))
                .map(clienteEntity -> this.entityToResponse(clienteEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.clienteRespository.findAllByEliminado(false, pageable)
                .map(clienteEntity -> this.entityToResponse(clienteEntity));
    }

    @Override
    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        ClienteEntity clienteToPesist = ClienteEntity.builder()
                .numeroDocumento(request.getNumeroDocumento())
                .nombre(request.getNombre())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .eliminado(false)
                .build();
        ClienteEntity clientePesisted = this.clienteRespository.save(clienteToPesist);

        return this.entityToResponse(clientePesisted);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse read(Long id) {
        ClienteEntity clienteFromDB = this.clienteRespository.findById(id)
                .filter(cliente -> cliente.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.clientes.name()));

        return this.entityToResponse(clienteFromDB);
    }

    @Override
    @Transactional
    public ClienteResponse update(ClienteRequest request, Long id) {
        ClienteEntity clienteToUpdate = this.clienteRespository.findById(id)
                .filter(cliente -> cliente.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.clientes.name()));

        clienteToUpdate.setNumeroDocumento(request.getNumeroDocumento());
        clienteToUpdate.setNombre(request.getNombre());
        clienteToUpdate.setDireccion(request.getDireccion());
        clienteToUpdate.setTelefono(request.getTelefono());

        ClienteEntity clienteUpdated = this.clienteRespository.save(clienteToUpdate);

        return this.entityToResponse(clienteUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ClienteEntity clienteToDelete = this.clienteRespository.findById(id)
                .filter(cliente -> cliente.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.clientes.name()));

        clienteToDelete.setEliminado(true);
        this.clienteRespository.save(clienteToDelete);
    }

    private ClienteResponse entityToResponse(ClienteEntity clienteEntity) {
        ClienteResponse clienteResponse = new ClienteResponse();
        BeanUtils.copyProperties(clienteEntity, clienteResponse);
        return clienteResponse;
    }
}
