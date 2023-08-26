package com.rminaya.clothes.store.clothes_store.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClienteResponse {
    private Long id;
    private String numeroDocumento;
    private String nombre;
    private String direccion;
}
