package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common;

public interface SimpleCrudService<RQ, RS, ID> {
    RS create(RQ request);
    RS read(ID id);
}
