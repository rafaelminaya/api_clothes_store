package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ProductoRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProductoResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProductoEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.MarcaRepository;
import com.rminaya.clothes.store.clothes_store.domain.repositories.ProductoRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IProductoService;
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
public class ProductoService implements IProductoService {

    private ProductoRepository productoRepository;
    private MarcaRepository marcaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> findAll() {
        return this.productoRepository.findAll()
                .stream()
                .filter(producto -> producto.getEliminado().equals(false))
                .map(productoEntity -> this.entityToResponse(productoEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponse> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.productoRepository.findAllByEliminado(false, pageable)
                .map(productoEntity -> this.entityToResponse(productoEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> findByFiltroVenta(String termino) {
        return this.productoRepository.findByFiltroVenta(termino)
                .stream()
                .map(productoEntity -> this.entityToResponse(productoEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> findByMarcaId(Long marcaId) {
        MarcaEntity marcaFromDB = this.marcaRepository.findById(marcaId)
                .filter(marca -> marca.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));

        return this.productoRepository.findByMarcaId(marcaFromDB.getId())
                .stream()
                .map(productoEntity -> this.entityToResponse(productoEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoResponse create(ProductoRequest request) {

        MarcaEntity marca = this.marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));

        ProductoEntity productoToPersist = ProductoEntity.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .talla(request.getTalla())
                .color(request.getColor())
                .precioCompra(request.getPrecioCompra())
                .precioVenta(request.getPrecioVenta())
                .stock(0)
                .eliminado(false)
                .marca(marca)
                .build();

        ProductoEntity productoPersisted = this.productoRepository.save(productoToPersist);

        return this.entityToResponse(productoPersisted);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse read(Long id) {
        ProductoEntity productoFromDB = this.productoRepository.findById(id)
                .filter(producto -> producto.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

        return this.entityToResponse(productoFromDB);
    }

    @Override
    @Transactional
    public ProductoResponse update(ProductoRequest request, Long id) {
        MarcaEntity marca = this.marcaRepository.findById(request.getMarcaId())
                .filter(marcaEntity -> marcaEntity.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.marcas.name()));

        ProductoEntity productoToUpdate = this.productoRepository.findById(id)
                .filter(producto -> producto.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

        productoToUpdate.setCodigo(request.getCodigo());
        productoToUpdate.setNombre(request.getNombre());
        productoToUpdate.setColor(request.getColor());
        productoToUpdate.setPrecioCompra(request.getPrecioCompra());
        productoToUpdate.setPrecioVenta(request.getPrecioVenta());
        productoToUpdate.setMarca(marca);

        ProductoEntity productoUpdated = this.productoRepository.save(productoToUpdate);
        return this.entityToResponse(productoUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductoEntity productoToDelete = this.productoRepository.findById(id)
                .filter(producto -> producto.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));

        productoToDelete.setEliminado(true);
        this.productoRepository.save(productoToDelete);
    }

    private ProductoResponse entityToResponse(ProductoEntity productoEntity) {
        ProductoResponse productoResponse = new ProductoResponse();
        BeanUtils.copyProperties(productoEntity, productoResponse);

        MarcaResponse marcaResponse = new MarcaResponse();
        BeanUtils.copyProperties(productoEntity.getMarca(), marcaResponse);
        productoResponse.setMarca(marcaResponse);
        return productoResponse;
    }
}
