package com.rminaya.clothes.store.clothes_store.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoResponse {
    private Long id;
    private String codigo;
    private String nombre;
    private String talla;
    private String color;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stock;
    private MarcaResponse marca;
}
