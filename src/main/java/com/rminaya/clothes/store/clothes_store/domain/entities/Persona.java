package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Persona {

    protected String numeroDocumento;
    protected String nombre;
    protected String direccion;
    protected String telefono;
    @Column(columnDefinition = "boolean default false")
    protected Boolean eliminado = false;
}
