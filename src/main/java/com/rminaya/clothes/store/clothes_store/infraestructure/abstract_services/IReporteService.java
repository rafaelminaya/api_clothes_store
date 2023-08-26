package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.responses.ReporteKardexPorProductoResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ReporteLiquidacionResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ReporteRegistroVentasResponse;

import java.util.List;

public interface IReporteService {
    List<ReporteRegistroVentasResponse> registroVentas(String fechaInicio, String fechaFin);
    List<ReporteKardexPorProductoResponse> kardexPorProducto(String productoId);
    List<ReporteLiquidacionResponse> liquidacionProveedores(String proveedorId, String fechaInicio, String fechaFin);
}
