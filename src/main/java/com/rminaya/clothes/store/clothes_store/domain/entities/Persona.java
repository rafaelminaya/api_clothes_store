package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@MappedSuperclass
public class Persona {
    @NotEmpty(message = "no puede estar vacio.")
    @Size( min = 8, max = 13, message = "debe tener entre 8 y 13 caracteres.")
    protected String numeroDocumento;
    @NotEmpty(message = "no puede estar vacio.")
    @Size( min = 2, max = 255, message = "debe tener entre 2 y 255 caracteres.")
    protected String nombre;
    @NotNull
    protected String direccion;
    protected String telefono;
    @Column(columnDefinition = "boolean default false")
    protected Boolean eliminado = false;
}
