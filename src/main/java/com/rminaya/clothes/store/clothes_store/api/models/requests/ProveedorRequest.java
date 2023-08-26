package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProveedorRequest {
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 11, max = 11,message = "solo puede ser de 11 caracteres.")
    private String ruc;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 3, max = 255,message = "debe tener entre 3 y 255 caracteres.")
    private String razonComercial;
    private String email;
    @NotNull
    private String direccion;
    @NotNull
    private String telefono;
}
