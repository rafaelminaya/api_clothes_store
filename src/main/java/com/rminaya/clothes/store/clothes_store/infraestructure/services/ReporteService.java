package com.rminaya.clothes.store.clothes_store.infraestructure.services;

import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteKardexPorProductoResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteLiquidacionResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.IReporteRegistroVentasResponse;
import com.rminaya.clothes.store.clothes_store.domain.entities.GuiaRemisionEntity;
import com.rminaya.clothes.store.clothes_store.domain.repositories.BoletaVentaRepository;
import com.rminaya.clothes.store.clothes_store.domain.repositories.GuiaRemisionRepository;
import com.rminaya.clothes.store.clothes_store.domain.repositories.KardexRepository;
import com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.IReporteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReporteService implements IReporteService {

    private BoletaVentaRepository boletaVentaRepository;
    private GuiaRemisionRepository guiaRemisionRepository;
    private KardexRepository kardexRepository;

    @Override
    public List<IReporteRegistroVentasResponse> registroVentas(String fechaInicio, String fechaFin) {
        // Convertimos las fechas de tipo String a LocalDate le añadimos la hora mínima y máxima
        LocalDateTime fechaInicioDateTime = LocalDateTime.of(LocalDate.parse(fechaInicio), LocalTime.MIN);
        LocalDateTime fechaFinDateTime = LocalDateTime.of(LocalDate.parse(fechaFin), LocalTime.MAX);

        return this.boletaVentaRepository.reporteVenta(fechaInicioDateTime, fechaFinDateTime);
    }

    @Override
    public List<IReporteKardexPorProductoResponse> kardexPorProducto(String productoId) {
        // Convertimos el "String" recbido en "Long" que es lo que se necesita
        return this.kardexRepository.findByProductoId(Long.valueOf(productoId));
    }

    @Override
    public List<IReporteLiquidacionResponse> liquidacionProveedores(String proveedorId, String fechaInicio, String fechaFin) {
        // Obtengo las guías del proveedor
        List<GuiaRemisionEntity> guiasByProveedor = this.guiaRemisionRepository.findByProveedor(Long.valueOf(proveedorId));
        // Obtengo los id de los productos del proveedor
        List<Long> productosByProveedor = guiasByProveedor
                .stream()
                .flatMap(guiaRemision -> guiaRemision.getGuiaRemisionDetalles().stream())
                .map(guiaRemisionDetalle -> guiaRemisionDetalle.getProducto())
                .map(producto -> producto.getId())
                .distinct()
                .collect(Collectors.toList());
        // Obtengo los productos vendidos del proveedor en el rango de fechas recibido
        LocalDateTime fechaInicioDateTime = LocalDateTime.of(LocalDate.parse(fechaInicio), LocalTime.MIN);
        LocalDateTime fechaFinDateTime = LocalDateTime.of(LocalDate.parse(fechaFin), LocalTime.MAX);

        return this.boletaVentaRepository.findByProductosAgrupados(productosByProveedor,
                fechaInicioDateTime, fechaFinDateTime);
    }
}
