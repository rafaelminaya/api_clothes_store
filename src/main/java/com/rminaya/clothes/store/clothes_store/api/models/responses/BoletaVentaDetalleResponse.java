package com.rminaya.clothes.store.clothes_store.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoletaVentaDetalleResponse {
    private Long id;
    private Integer cantidad;
    private Double precioCompra;
    private Double precioVenta;
    private Double baseImponible;
    private Double importeIgv;
    private Double totalDetalle;
    private Boolean eliminado;
    private ProductoResponse producto;
}
