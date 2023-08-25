package com.rminaya.clothes.store.clothes_store.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    @Column(nullable = false)
    private String codigo;
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 255, message = "debe tener entre 3 y 255 caracteres.")
    @Column(nullable = false)
    private String nombre;
    @NotNull
    private String talla;
    @NotNull
    private String color;
    @NotNull(message = "no puede estar vacio.")
    private Double precioCompra;
    @NotNull(message = "no puede estar vacio.")
    private Double precioVenta;
    @Column(columnDefinition = "int default 0")
    private Integer stock = 0;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @NotNull(message = "no puede estar vacio.")
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
