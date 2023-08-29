package com.rminaya.clothes.store.clothes_store.api.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GuiaRemisionDetalleResponse {
    private Long id;
    private Integer cantidad;
    private Double precioVenta;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductoResponse producto;
    // Obtenidos por metodos en el entity
    private Double porcentajeComision;
    private Double importeComision;
    private Double precioCompra;
    private Double totalDetalle;
}
