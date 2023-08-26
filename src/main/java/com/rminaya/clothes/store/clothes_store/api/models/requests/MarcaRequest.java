package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarcaRequest {
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    private String nombre;
}
