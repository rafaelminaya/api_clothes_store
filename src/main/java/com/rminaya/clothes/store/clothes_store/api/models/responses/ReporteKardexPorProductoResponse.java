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
public class ReporteKardexPorProductoResponse {
    private Long productoId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    private String numero;
    private String tipoOperacion;
    private Integer entradaCantidad;
    private Double entradaPrecio;
    private Double entradaTotal;
    private Integer salidaCantidad;
    private Double salidaPrecio;
    private Double salidaTotal;
    private Integer saldoCantidad;
    private Double saldoPrecio;
    private Double daldoTotal;
}
