package com.rminaya.clothes.store.clothes_store.util.enums;

public enum Operacion {
    VENTA(1L),
    CONSIGNACION_RECIBIDA(2L),
    CONSIGNACION_ENTREGADA(3L);
    private final Long id;

    Operacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
