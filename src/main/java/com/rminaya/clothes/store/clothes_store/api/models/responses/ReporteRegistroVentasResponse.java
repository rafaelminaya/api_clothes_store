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
public class ReporteRegistroVentasResponse {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime FechaEmision;
    private String numero;
    private Double baseImponible;
    private Double importeIgv;
    private Double total;
    private Long clienteId;
    private String clienteNombre;
    private String clienteDni;
    private Boolean eliminado;
}
