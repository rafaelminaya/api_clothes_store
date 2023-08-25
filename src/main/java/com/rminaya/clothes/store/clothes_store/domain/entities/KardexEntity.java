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
import java.util.List;

@Entity
@Table(name = "kardex")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KardexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Long comprobanteId;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_operacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private TipoOperacionEntity tipoOperacion;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kardex_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private List<KardexDetalleEntity> kardexDetalles;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    public void addDetalle(KardexDetalleEntity kardexDetalleEntity) {
        this.kardexDetalles.add(kardexDetalleEntity);
    }
}
