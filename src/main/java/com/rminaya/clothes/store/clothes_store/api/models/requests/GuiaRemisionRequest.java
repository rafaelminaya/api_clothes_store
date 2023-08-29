package com.rminaya.clothes.store.clothes_store.api.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GuiaRemisionRequest {

    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @NotNull(message = "no puede estar vacio.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;
    @NotNull(message = "no puede estar vacio.")
    @Min(value = 0, message = "no debe ser menor que 0.")
    @Max(value = 100, message = "no debe ser mayor que 100.")
    private Double porcentajeComision;
    @Positive(message = "debe ser mayor a 0")
    @NotNull(message = "no puede estar vacio.")
    private Long proveedorId;
    @NotNull(message = "no puede estar vacio.")
    @Size(min = 1, message = "debe tener minimo 1 detalle por guia de remision")
    @JsonIgnoreProperties(value = {"guiaremision", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<GuiaRemisionDetalleRequest> guiaRemisionDetalles;
}
