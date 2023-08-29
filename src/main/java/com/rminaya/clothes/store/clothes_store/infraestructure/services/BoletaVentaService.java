package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.BoletaVentaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.*;
import com.rminaya.clothes.store.clothes_store.domain.entities.BoletaVentaEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.BoletaVentaRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IBoletaVentaService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoletaVentaService implements IBoletaVentaService {

    private BoletaVentaRepository boletaVentaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoletaVentaResponse> findAll() {
        return this.boletaVentaRepository.findAll()
                .stream()
                .filter(boletaVenta -> boletaVenta.getEliminado().equals(false))
                .map(boletaVentaEntity -> this.entityToResponse(boletaVentaEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoletaVentaResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.boletaVentaRepository.findAll(pageable)
                .map(boletaVentaEntity -> this.entityToResponse(boletaVentaEntity));
    }

    @Override
    @Transactional
    public void anular(Long boletaId) {

    }

    @Override
    @Transactional
    public BoletaVentaResponse create(BoletaVentaRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public BoletaVentaResponse read(Long id) {
        BoletaVentaEntity boletaFromDB = this.boletaVentaRepository.findById(id)
                .filter(boletaVenta -> boletaVenta.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.boleta_venta.name()));

        return this.entityToResponse(boletaFromDB);
    }

    private BoletaVentaResponse entityToResponse(BoletaVentaEntity boletaVentaEntity) {
        BoletaVentaResponse boletaVentaResponse = new BoletaVentaResponse();
        BeanUtils.copyProperties(boletaVentaEntity, boletaVentaResponse);

        ClienteResponse clienteResponse = new ClienteResponse();
        BeanUtils.copyProperties(boletaVentaEntity.getCliente(), clienteResponse);
        boletaVentaResponse.setCliente(clienteResponse);

        List<BoletaVentaDetalleResponse> detallesResponse = new ArrayList<>();

        boletaVentaEntity.getBoletaVentaDetalles().forEach(boletaVentaDetalleEntity -> {

            BoletaVentaDetalleResponse detalleResponse = new BoletaVentaDetalleResponse();
            BeanUtils.copyProperties(boletaVentaDetalleEntity, detalleResponse);

            ProductoResponse productoResponse = new ProductoResponse();
            BeanUtils.copyProperties(boletaVentaDetalleEntity.getProducto(), productoResponse);
            detalleResponse.setProducto(productoResponse);

            MarcaResponse marcaResponse = new MarcaResponse();
            BeanUtils.copyProperties(boletaVentaDetalleEntity.getProducto().getMarca(), marcaResponse);
            productoResponse.setMarca(marcaResponse);

            detallesResponse.add(detalleResponse);
        });

        boletaVentaResponse.setBoletaVentaDetalles(detallesResponse);

        return boletaVentaResponse;
    }
}
