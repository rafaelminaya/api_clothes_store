package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @NotNull(message = "no puede estar vacio.")
    //@Column(name = "fecha_emision", columnDefinition = "DATETIME")
    //@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate fechaEmision;
    @NotNull(message = "no puede estar vacio.")
    @Min(value = 0, message = "no debe ser menor que 0.")
    @Max(value = 100, message = "no debe ser mayor que 100.")
    private Double porcentajeComision;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProveedorEntity proveedor;
    @NotNull
    @OneToMany(mappedBy = "guiaRemision",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "guia_remision_id")
    @JsonIgnoreProperties(value = {"guiaremision", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<GuiaRemisionDetalleEntity> guiaRemisionDetalles;
    @Column(columnDefinition = "boolean default false")
    private Boolean procesado = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;

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
