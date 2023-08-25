package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boleta_venta")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoletaVentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    private Double baseImponible;
    private Double importeIgv;
    private Double total;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "boleta_venta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private List<BoletaVentaDetalleEntity> boletaVentaDetalles;

    public Double calcularBaseImponible() {
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.calcularBaseImponible())
                .sum();
    }

    public Double calcularImporteIgv() {
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.calcularImporteIgv())
                .sum();
    }

    public Double calcularTotal() {
         /*
        Double total = 0.0;
        for (BoletaVentaDetalle detalle : this.boletaVentaDetalles) {
            total += detalle.getTotal();
        }
        return total;
        */
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.getTotalDetalle())
                .sum();
    }

}
