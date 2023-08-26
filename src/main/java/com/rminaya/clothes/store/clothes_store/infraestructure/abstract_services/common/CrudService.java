package com.rminaya.clothes.store.clothes_store.infraestructure.abstract_services.common;

public interface CrudService<RQ, RS, ID> {
    RS create(RQ request);
    RS read(ID id);
    RS update(RQ request, ID id);
    void delete(ID id);
}
