package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.BoletaVentaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.BoletaVentaResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IBoletaVentaService;
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
@RequestMapping(value = "/api/boletas-venta")
@AllArgsConstructor
@Tag(name = "boletas de venta")
public class BoletaVentaController {

    private IBoletaVentaService boletaVentaService;

    @Operation(summary = "Devuelve todas las boletas de venta")
    @GetMapping
    public ResponseEntity<List<BoletaVentaResponse>> index() {
        return ResponseEntity.ok(this.boletaVentaService.findAll());
    }

    @Operation(summary = "Devuelve todas las boletas de venta paginadas por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<BoletaVentaResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.boletaVentaService.findAll(page));
    }

    @Operation(summary = "Devuelve una boleta de venta por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<BoletaVentaResponse> get(@PathVariable("id") Long boletaVentaId) {
        return ResponseEntity.ok(this.boletaVentaService.read(boletaVentaId));
    }

    @Operation(summary = "Guarda una boleta de venta dada")
    @PostMapping
    public ResponseEntity<BoletaVentaResponse> post(@Valid @RequestBody BoletaVentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaVentaService.create(request));
    }

    @Operation(summary = "Anula una boleta de venta por un id dado")
    @PutMapping(path = "/{id}/anular")
    public ResponseEntity<Void> put(@PathVariable Long boletaVentaId) {
        this.boletaVentaService.anular(boletaVentaId);
        return ResponseEntity.noContent().build();
    }
}
