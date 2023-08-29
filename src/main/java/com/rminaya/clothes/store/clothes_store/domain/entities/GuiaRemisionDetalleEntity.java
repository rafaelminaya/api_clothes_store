package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "guia_remision_detalle")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GuiaRemisionDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioVenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductoEntity producto;
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "guia_remision_id")
    @JsonIgnoreProperties(value = {"guiaRemisionDetalles","hibernateLazyInitializer", "handler"}, allowSetters = true)
    //TODO - Este "@JsonIgnore" NO estaba comentado, verificar si está bien dejarlo sin comentar.
    @JsonIgnore
    private GuiaRemisionEntity guiaRemision;
    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    public Double getPorcentajeComision() {
        return this.guiaRemision.getPorcentajeComision();
    }

    public Double getImporteComision() {
        return this.precioVenta * (this.getPorcentajeComision() / 100);
//        return this.precioVenta * (this.porcentajeComision / 100);
    }

    public Double getPrecioCompra() {
        return this.precioVenta - this.getImporteComision();
    }

    public Double getTotalDetalle() {
        return this.precioVenta * this.cantidad;
    }

}
