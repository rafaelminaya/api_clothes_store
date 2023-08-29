package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.GuiaRemisionRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.GuiaRemisionResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IGuiaRemisionService;
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
@RequestMapping(path = "/api/guias-remision")
@Tag(name = "guias de remision")
@AllArgsConstructor
public class GuiaRemisionController {

    private IGuiaRemisionService guiaRemisionService;

    @Operation(summary = "Devuelve todas las guias de remision")
    @GetMapping
    public ResponseEntity<List<GuiaRemisionResponse>> index() {
        return ResponseEntity.ok(this.guiaRemisionService.findAll());
    }

    @Operation(summary = "Devuelve todas las guias de remision paginadas por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<GuiaRemisionResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.guiaRemisionService.findAll(page));
    }

    @Operation(summary = "Devuelve una guia de remision por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<GuiaRemisionResponse> get(@PathVariable("id") Long guiaRemisionId) {
        return ResponseEntity.ok(this.guiaRemisionService.read(guiaRemisionId));
    }

    @Operation(summary = "Guarda una guia de remision dada")
    @PostMapping
    public ResponseEntity<GuiaRemisionResponse> post(@Valid @RequestBody GuiaRemisionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guiaRemisionService.create(request));
    }

    @Operation(summary = "Actualiza una guia de remision y id dados")
    @PutMapping(path = "/{id}")
    public ResponseEntity<GuiaRemisionResponse> put(@Valid @RequestBody GuiaRemisionRequest request, @PathVariable("id") Long guiaRemisionId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.guiaRemisionService.update(request, guiaRemisionId));
    }

    @Operation(summary = "Elimina una guia de remision por un id dado")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.guiaRemisionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
