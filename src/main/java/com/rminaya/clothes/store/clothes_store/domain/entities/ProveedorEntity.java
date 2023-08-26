package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "proveedores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 11)
    private String ruc;
    @Column(name = "razon_comercial")
    private String razonComercial;
    private String email;
    private String direccion;
    private String telefono;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
}
