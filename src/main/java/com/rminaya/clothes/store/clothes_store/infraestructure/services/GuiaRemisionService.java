package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.GuiaRemisionRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.*;
import com.rminaya.clothes.store.clothes_store.domain.entities.GuiaRemisionDetalleEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.GuiaRemisionEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProductoEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.GuiaRemisionRepository;
import com.rminaya.clothes.store.clothes_store.domain.repositories.ProductoRepository;
import com.rminaya.clothes.store.clothes_store.domain.repositories.ProveedorRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IGuiaRemisionService;
import com.rminaya.clothes.store.clothes_store.infraestructure.helpers.KardexHelper;
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
public class GuiaRemisionService implements IGuiaRemisionService {

    private GuiaRemisionRepository guiaRemisionRepository;
    private ProveedorRepository proveedorRepository;
    private ProductoRepository productoRepository;
    private KardexHelper kardexHelper;

    @Override
    @Transactional(readOnly = true)
    public List<GuiaRemisionResponse> findAll() {
        return this.guiaRemisionRepository.findAll()
                .stream()
                .filter(guiaRemision -> guiaRemision.getEliminado().equals(false))
                .map(guiaRemisionEntity -> this.entityToResponse(guiaRemisionEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuiaRemisionResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.guiaRemisionRepository.findAllByEliminado(false, pageable)
                .map(guiaRemisionEntity -> this.entityToResponse(guiaRemisionEntity));
    }

    @Override
    @Transactional
    public GuiaRemisionResponse create(GuiaRemisionRequest request) {
        ProveedorEntity proveedor = this.proveedorRepository
                .findById(request.getProveedorId())
                .orElseThrow(() -> new IdNotFoundException(Tables.proveedores.name()));

        List<GuiaRemisionDetalleEntity> guiaRemisionDetalles = new ArrayList<>(request.getGuiaRemisionDetalles().size());

        request.getGuiaRemisionDetalles().forEach(guiaDetalleRequest -> {

            ProductoEntity producto = this.productoRepository
                    .findById(guiaDetalleRequest.getProductoId())
                    .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

            GuiaRemisionDetalleEntity guiaDetalleToPersist = GuiaRemisionDetalleEntity.builder()
                    .cantidad(guiaDetalleRequest.getCantidad())
                    .precioVenta(guiaDetalleRequest.getPrecioVenta())
                    .producto(producto)
                    .eliminado(false)
                    .build();

            guiaRemisionDetalles.add(guiaDetalleToPersist);
        });

        GuiaRemisionEntity guiaToPersist = GuiaRemisionEntity.builder()
                .numero(request.getNumero())
                .fechaEmision(request.getFechaEmision())
                .porcentajeComision(request.getPorcentajeComision())
                .guiaRemisionDetalles(guiaRemisionDetalles)
                .proveedor(proveedor)
                .procesado(false)
                .eliminado(false)
                .build();

        GuiaRemisionEntity guiaPersisted = this.guiaRemisionRepository.save(guiaToPersist);

        guiaPersisted.getGuiaRemisionDetalles().forEach(detallePersisted -> {
            ProductoEntity productoToUpdate = this.productoRepository
                    .findById(detallePersisted.getProducto().getId())
                    .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

            productoToUpdate.setPrecioCompra(detallePersisted.getPrecioCompra());
            productoToUpdate.setPrecioVenta(detallePersisted.getPrecioVenta());
            this.productoRepository.save(productoToUpdate);
        });

        //TODO Aqui va el metodo de procesar al kardex
        this.kardexHelper.procesarGuiaRemision(guiaPersisted.getId());

        return this.entityToResponse(guiaPersisted);
    }

    @Override
    @Transactional(readOnly = true)
    public GuiaRemisionResponse read(Long id) {
        GuiaRemisionEntity guiaFromDB = this.guiaRemisionRepository.findById(id)
                .filter(guiaRemision -> guiaRemision.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.guia_remision.name()));

        return this.entityToResponse(guiaFromDB);
    }

    @Override
    @Transactional
    public GuiaRemisionResponse update(GuiaRemisionRequest request, Long id) {

        this.kardexHelper.desprocesarGuiaRemision(id);

        List<GuiaRemisionDetalleEntity> guiaRemisionDetalles = new ArrayList<>(request.getGuiaRemisionDetalles().size());

        request.getGuiaRemisionDetalles().forEach(guiaDetalleRequest -> {

            ProductoEntity producto = this.productoRepository
                    .findById(guiaDetalleRequest.getProductoId())
                    .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

            GuiaRemisionDetalleEntity guiaDetalleToPersist = GuiaRemisionDetalleEntity.builder()
                    .cantidad(guiaDetalleRequest.getCantidad())
                    .precioVenta(guiaDetalleRequest.getPrecioVenta())
                    .producto(producto)
                    .eliminado(false)
                    .build();

            guiaRemisionDetalles.add(guiaDetalleToPersist);
        });

        GuiaRemisionEntity guiaToUpdate = this.guiaRemisionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.guia_remision.name()));

        ProveedorEntity proveedor = this.proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new IdNotFoundException(Tables.proveedores.name()));

        guiaToUpdate.setNumero(request.getNumero());
        guiaToUpdate.setFechaEmision(request.getFechaEmision());
        guiaToUpdate.setPorcentajeComision(request.getPorcentajeComision());
        guiaToUpdate.setProveedor(proveedor);
        //TODO es duda si con este se modificarán los detalles de la guia a eliminado = true
        guiaToUpdate.getGuiaRemisionDetalles().forEach(guiaDetalle -> guiaDetalle.setEliminado(true));
        //TODO es duda si luego de modificarse estos nuevos detalles podran ser persistidos correctamente
        guiaToUpdate.setGuiaRemisionDetalles(guiaRemisionDetalles);
        guiaToUpdate.getGuiaRemisionDetalles().forEach(guiaDetalle -> {

            ProductoEntity productoToUpdate = this.productoRepository
                    .findById(guiaDetalle.getProducto().getId())
                    .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));
            productoToUpdate.setPrecioCompra(guiaDetalle.getPrecioCompra());
            productoToUpdate.setPrecioVenta(guiaDetalle.getPrecioVenta());
        });

        GuiaRemisionEntity guiaUpdated = this.guiaRemisionRepository.save(guiaToUpdate);

        this.kardexHelper.procesarGuiaRemision(guiaUpdated.getId());

        return this.entityToResponse(guiaUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GuiaRemisionEntity guiaToDelete = this.guiaRemisionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.guia_remision.name()));
        guiaToDelete.setEliminado(true);

        guiaToDelete.getGuiaRemisionDetalles().forEach(guiaRemisionDetalle -> {
            guiaRemisionDetalle.setEliminado(true);
        });

        this.guiaRemisionRepository.save(guiaToDelete);
    }

    private GuiaRemisionResponse entityToResponse(GuiaRemisionEntity guiaRemisionEntity) {
        GuiaRemisionResponse guiaRemisionResponse = new GuiaRemisionResponse();
        BeanUtils.copyProperties(guiaRemisionEntity, guiaRemisionResponse);

        ProveedorResponse proveedorResponse = new ProveedorResponse();
        BeanUtils.copyProperties(guiaRemisionEntity.getProveedor(), proveedorResponse);
        guiaRemisionResponse.setProveedor(proveedorResponse);

        List<GuiaRemisionDetalleResponse> detallesResponse = new ArrayList<>();

        guiaRemisionEntity.getGuiaRemisionDetalles().forEach(guiaRemisionDetalleEntity -> {
            GuiaRemisionDetalleResponse detalleResponse = new GuiaRemisionDetalleResponse();
            BeanUtils.copyProperties(guiaRemisionDetalleEntity, detalleResponse);

            ProductoResponse productoResponse = new ProductoResponse();
            BeanUtils.copyProperties(guiaRemisionDetalleEntity.getProducto(), productoResponse);
            detalleResponse.setProducto(productoResponse);

            MarcaResponse marcaResponse = new MarcaResponse();
            BeanUtils.copyProperties(guiaRemisionDetalleEntity.getProducto().getMarca(), marcaResponse);
            productoResponse.setMarca(marcaResponse);

            detallesResponse.add(detalleResponse);
        });

        guiaRemisionResponse.setGuiaRemisionDetalles(detallesResponse);

        return guiaRemisionResponse;
    }
}
