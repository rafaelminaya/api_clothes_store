package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoletaVentaProductoRequest {
    @Positive(message = "debe ser mayor a 0")
    @NotNull(message = "no puede estar vacio.")
    private Long id;
}
