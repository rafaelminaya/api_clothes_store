package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ProductoRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProductoResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/productos")
@AllArgsConstructor
@Tag(name = "productos")
public class ProductoController {

    private IProductoService productoService;

    @Operation(summary = "Devuelve todos los productos")
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> index() {
        return ResponseEntity.ok(this.productoService.findAll());
    }

    @Operation(summary = "Devuelve todos los productos paginadas por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<ProductoResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.productoService.findAll(page));
    }

    @GetMapping("/{id}/by-marca")
    @Operation(summary = "Devuelve todos los productos segun una marcaId dado")
    public ResponseEntity<List<ProductoResponse>> getProductosByMarcaId(@PathVariable("id") Long marcaId) {
        return ResponseEntity.ok(this.productoService.findByMarcaId(marcaId));
    }

    @GetMapping("/by-filtro-venta")
    @Operation(summary = "Devuelve todos los productos cuyos codigo o nombre coincida por el filtro dado")
    public ResponseEntity<List<ProductoResponse>> getProductosByFiltroVenta(@RequestParam("termino") String termino) {
        return ResponseEntity.ok(this.productoService.findByFiltroVenta(termino));
    }

    @Operation(summary = "Devuelve un producto por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductoResponse> get(@PathVariable("id") Long productoId) {
        return ResponseEntity.ok(this.productoService.read(productoId));
    }

    @Operation(summary = "Guarda un producto dado")
    @PostMapping
    public ResponseEntity<ProductoResponse> post(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }

    @Operation(summary = "Actualiza un producto y id dados")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductoResponse> put(@Valid @RequestBody ProductoRequest request, @PathVariable("id") Long productoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productoService.update(request, productoId));
    }

    @Operation(summary = "Elimina un producto por un id dado")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
