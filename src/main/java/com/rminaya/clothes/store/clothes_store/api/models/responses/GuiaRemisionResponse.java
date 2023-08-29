package com.rminaya.clothes.store.clothes_store.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GuiaRemisionResponse {
    private Long id;
    private String numero;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;
    private Double porcentajeComision;
    private ProveedorResponse proveedor;
    private List<GuiaRemisionDetalleResponse> guiaRemisionDetalles;
    private Boolean procesado;
    // Obtenidos por metodos en el entity
    private Double totalPrecioCompra;
    private Double totalPrecioVenta;
    private Double totalImporteComision;

}
