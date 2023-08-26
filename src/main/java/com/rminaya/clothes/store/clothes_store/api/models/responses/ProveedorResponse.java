package com.rminaya.clothes.store.clothes_store.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProveedorResponse {
    private Long id;
    private String ruc;
    private String razonComercial;
    private String email;
    private String direccion;
    private String telefono;
}
