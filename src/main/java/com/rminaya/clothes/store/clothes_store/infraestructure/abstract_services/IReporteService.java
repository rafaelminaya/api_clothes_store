package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services;

import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteKardexPorProductoResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteLiquidacionResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteRegistroVentasResponse;

import java.util.List;

public interface IReporteService {
    List<IReporteRegistroVentasResponse> registroVentas(String fechaInicio, String fechaFin);
    List<IReporteKardexPorProductoResponse> kardexPorProducto(String productoId);
    List<IReporteLiquidacionResponse> liquidacionProveedores(String proveedorId, String fechaInicio, String fechaFin);
}
