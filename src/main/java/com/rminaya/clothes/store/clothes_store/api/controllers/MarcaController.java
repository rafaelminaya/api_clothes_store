package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.MarcaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IMarcaService;
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
@RequestMapping(path = "/api/marcas")
@AllArgsConstructor
@Tag(name = "marcas")
public class MarcaController {

    private IMarcaService marcaService;

    @Operation(summary = "Devuelve todas las marcas")
    @GetMapping
    public ResponseEntity<List<MarcaResponse>> index() {
        return ResponseEntity.ok(this.marcaService.findAll());
    }

    @Operation(summary = "Devuelve todas las marcas paginadas por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<MarcaResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.marcaService.findAll(page));
    }

    @Operation(summary = "Devuelve una marca por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<MarcaResponse> get(@PathVariable("id") Long marcaId) {
        return ResponseEntity.ok(this.marcaService.read(marcaId));
    }

    @Operation(summary = "Guarda una marca dada")
    @PostMapping
    public ResponseEntity<MarcaResponse> post(@Valid @RequestBody MarcaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.create(request));
    }

    @Operation(summary = "Actualiza una marca y id dados")
    @PutMapping(path = "/{id}")
    public ResponseEntity<MarcaResponse> put(@Valid @RequestBody MarcaRequest request, @PathVariable("id") Long marcaId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.marcaService.update(request, marcaId));
    }

    @Operation(summary = "Elimina una marca por un id dado")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
