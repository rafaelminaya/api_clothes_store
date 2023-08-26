package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.requests.ClienteRequest;
import com.rminaya.clothes.store.clothes_store.api.models.requests.MarcaRequest;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ClienteResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.MarcaResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.ClienteEntity;
import com.rminaya.clothes.store.clothes_store.domain.entities.MarcaEntity;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IClienteService;
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
@RequestMapping(value = "/api/clientes")
@AllArgsConstructor
@Tag(name = "clientes")
public class ClienteController {

    private IClienteService clienteService;

    @Operation(summary = "Devuelve todas los clientes")
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> index() {
        return ResponseEntity.ok(this.clienteService.findAll());
    }

    @Operation(summary = "Devuelve los clientes paginadas por un numero de pagina dada")
    @GetMapping(path = "page/{page}")
    public ResponseEntity<Page<ClienteResponse>> indexPage(@PathVariable Integer page) {
        return ResponseEntity.ok(this.clienteService.findAll(page));
    }

    @Operation(summary = "Devuelve un cliente por una id dado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ClienteResponse> get(@PathVariable("id") Long marcaId) {
        return ResponseEntity.ok(this.clienteService.read(marcaId));
    }

    @Operation(summary = "Guarda un cliente dado")
    @PostMapping
    public ResponseEntity<ClienteResponse> post(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(request));
    }

    @Operation(summary = "Actualiza un cliente y id dados")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ClienteResponse> put(@Valid @RequestBody ClienteRequest request, @PathVariable("id") Long marcaId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.update(request, marcaId));
    }

    @Operation(summary = "Elimina un cliente por un id dado")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
