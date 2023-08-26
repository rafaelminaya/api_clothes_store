package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String nombre;
    private String talla;
    private String color;
    private Double precioCompra;
    private Double precioVenta;
    @Column(columnDefinition = "int default 0")
    private Integer stock = 0;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "marca_id")
    private MarcaEntity marca;

    public String getCadenaProducto() {
        String cadenaProducto, itemColor, itemTalla;

        itemColor = this.getColor().equals("") ? "" : " - ".concat(this.getColor());
        itemTalla = this.getTalla().equals("") ? "" : " - ".concat(this.getTalla());

        cadenaProducto = this.getCodigo().concat(" - ").concat(this.getNombre()).concat(itemColor).concat(itemTalla);

        return cadenaProducto;
    }

}
