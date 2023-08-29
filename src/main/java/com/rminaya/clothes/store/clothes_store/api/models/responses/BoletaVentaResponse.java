package com.rminaya.clothes.store.clothes_store.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoletaVentaResponse {
    private Long id;
    private String numero;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    private Double baseImponible;
    private Double importeIgv;
    private Double total;
    private Boolean eliminado = false;
    private ClienteResponse cliente;
    private List<BoletaVentaDetalleResponse> boletaVentaDetalles;
}
