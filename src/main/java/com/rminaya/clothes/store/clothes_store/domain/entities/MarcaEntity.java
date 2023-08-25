package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "marcas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MarcaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    @Column(nullable = false)
    private String nombre;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
}
