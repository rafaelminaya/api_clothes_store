package com.rminaya.clothes.store.clothes_store.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClienteRequest {
    @NotBlank
    private String numeroDocumento;
    @NotBlank
    private String nombre;
    private String direccion;
}
