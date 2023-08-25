package com.rminaya.clothes.store.clothes_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "boleta_venta_detalle")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoletaVentaDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioCompra;
    private Double precioVenta;
    private Double baseImponible;
    private Double importeIgv;
    private Double totalDetalle;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;
    private static final Double PORCENTAJE_IGV = 0.18;

    public Double calcularBaseImponible() {
        return (this.calcularTotalDetalle() / (1 + PORCENTAJE_IGV));
    }

    public Double calcularImporteIgv() {
        return this.calcularTotalDetalle() - this.calcularBaseImponible();
    }

    public Double calcularTotalDetalle() {
        return this.precioVenta * this.cantidad;
    }

}
