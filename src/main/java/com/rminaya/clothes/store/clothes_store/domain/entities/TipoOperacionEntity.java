package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tipo_operacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TipoOperacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String nombre;

}
