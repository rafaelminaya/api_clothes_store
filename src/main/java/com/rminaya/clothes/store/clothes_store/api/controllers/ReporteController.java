package com.rminaya.clothes.store.clothes_store.api.controllers;

import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteKardexPorProductoResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteLiquidacionResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteRegistroVentasResponse;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IReporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reportes")
@Tag(name = "reportes")
@AllArgsConstructor
public class ReporteController {

    private IReporteService reporteService;

    @GetMapping(value = "/registro-ventas")
    public ResponseEntity<List<IReporteRegistroVentasResponse>> registroVentas(
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) {

        return ResponseEntity.ok(this.reporteService.registroVentas(fechaInicio, fechaFin));
    }

    @GetMapping(value = "/kardex-producto/{productoId}")
    public ResponseEntity<List<IReporteKardexPorProductoResponse>> kardexPorProducto(
            @PathVariable(value = "productoId") String productoId) {

        return ResponseEntity.ok(this.reporteService.kardexPorProducto(productoId));
    }

    @GetMapping(value = "/liquidacion-proveedores/{proveedorId}")
    public ResponseEntity<List<IReporteLiquidacionResponse>> liquidacionProveedores(
            @PathVariable(value = "proveedorId") String proveedorId,
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) {

        return ResponseEntity.ok(this.reporteService.liquidacionProveedores(proveedorId, fechaInicio, fechaFin));
    }
}
