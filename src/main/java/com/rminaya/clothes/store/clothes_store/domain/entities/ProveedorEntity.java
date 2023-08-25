package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name = "proveedores")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 11, max = 11,message = "solo puede ser de 11 caracteres.")
    private String ruc;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 3, max = 255,message = "debe tener entre 3 y 255 caracteres.")
    @Column(name = "razon_comercial")
    private String razonComercial;
    private String email;
    @NotNull
    private String direccion;
    @NotNull
    private String telefono;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
}
