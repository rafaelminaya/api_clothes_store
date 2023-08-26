package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.MarcaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.requests.ProveedorRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ProveedorResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.ProveedorEntity;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IProveedorService;
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
@RequestMapping(value = "/api/proveedores")
@AllArgsConstructor
@Tag(name = "proveedores")
public class ProveedorController {

    private IProveedorService proveedorService;

    @Operation(summary = "Devuelve todos los proveedores")
    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> index() {
        return ResponseEntity.ok(this.proveedorService.findAll());
    }

    @Operation(summary = "Devuelve todos los proveedores paginados por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<ProveedorResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.proveedorService.findAll(page));
    }

    @Operation(summary = "Devuelve un proveedor por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProveedorResponse> get(@PathVariable("id") Long proveedorId) {
        return ResponseEntity.ok(this.proveedorService.read(proveedorId));
    }

    @Operation(summary = "Guarda un proveedor dada")
    @PostMapping
    public ResponseEntity<ProveedorResponse> post(@Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.create(request));
    }

    @Operation(summary = "Actualiza un proveedor y id dados")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProveedorResponse> put(@Valid @RequestBody ProveedorRequest request, @PathVariable("id") Long proveedorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.proveedorService.update(request, proveedorId));
    }

    @Operation(summary = "Elimina un proveedor por un id dado")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
