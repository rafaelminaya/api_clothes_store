package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoRequest {
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    private String codigo;
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    private String nombre;
    @NotNull
    private String talla;
    @NotNull
    private String color;
    @NotNull(message = "no puede estar vacio.")
    private Double precioCompra;
    @NotNull(message = "no puede estar vacio.")
    private Double precioVenta;
    @Positive(message = "debe ser mayor a 0")
    @NotNull(message = "no puede estar vacio.")
    private Long marcaId;
}
