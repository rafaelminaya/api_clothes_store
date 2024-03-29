package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClienteRequest {
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 8, max = 13, message = "debe tener entre 8 y 13 caracteres.")
    private String numeroDocumento;
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 2, max = 255, message = "debe tener entre 2 y 255 caracteres.")
    private String nombre;
    @NotNull
    private String direccion;
    private String telefono;
}
