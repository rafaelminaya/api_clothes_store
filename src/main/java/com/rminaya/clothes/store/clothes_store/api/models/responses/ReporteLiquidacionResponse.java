package com.rminaya.clothes.store.clothes_store.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReporteLiquidacionResponse {
    private Long boletaId;
    private String boletaNumero;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime boletaFechaEmision;
    private Long productoId;
    private String marcaNombre;
    private String productoNombre;
    private String productoCodigo;
    private String productoTalla;
    private String productoColor;
    private Long boletaCantidad;
    private Double boletaPrecioCompra;
    private Double boletaPrecioVenta;
    private Double boletaPrecioNeto;
}
