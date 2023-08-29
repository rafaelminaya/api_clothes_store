package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "guia_remision")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GuiaRemisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6)
    private String numero;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate fechaEmision;
    private Double porcentajeComision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProveedorEntity proveedor;
    @ToString.Exclude // agregado segun el proyecto best_travel
    @OneToMany(mappedBy = "guiaRemision", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "guia_remision_id")
    @JsonIgnoreProperties(value = {"guiaremision", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<GuiaRemisionDetalleEntity> guiaRemisionDetalles;
    @Column(columnDefinition = "boolean default false")
    private Boolean procesado = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    // MÉTODOS
    @PrePersist
    public void updateFk(){
        this.guiaRemisionDetalles.forEach(detalles -> detalles.setGuiaRemision(this));
    }

    public void addDetalle(GuiaRemisionDetalleEntity guiaRemisionDetalle) {
        this.guiaRemisionDetalles.add(guiaRemisionDetalle);
    }

    public Double getTotalPrecioCompra() {
        return this.guiaRemisionDetalles
                .stream()
                .mapToDouble(value -> value.getPrecioCompra() * value.getCantidad())
                .sum();
    }

    public Double getTotalPrecioVenta() {
        return this.guiaRemisionDetalles
                .stream()
                .mapToDouble(value -> value.getPrecioVenta() * value.getCantidad())
                .sum();
    }

    public Double getTotalImporteComision() {
        return this.getTotalPrecioVenta() - this.getTotalPrecioCompra();
    }

}
