package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoletaVentaRequest {
    @NotBlank(message = "no puede estar vacío.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @NotNull
    private ClienteRequest cliente;
    @NotNull
    @Size(min = 1, message = "debe tener minimo 1 detalle por boleta de venta")
    private List<BoletaVentaDetalleRequest> boletaVentaDetalles;
}
