package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kardex_detalle")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KardexDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    private Integer entradaCantidad;
    private Double entradaPrecio;
    private Double entradaTotal;
    private Integer salidaCantidad;
    private Double salidaPrecio;
    private Double salidaTotal;
    private Integer saldoCantidad;
    private Double saldoPrecio;
    private Double saldoTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private ProductoEntity producto;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
}
