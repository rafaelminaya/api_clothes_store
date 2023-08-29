package com.rminaya.clothes.store.clothes_store.infraestructure.helpers;

import com.rminaya.clothes.store.clothes_store.domain.entities.*;
import com.rminaya.clothes.store.clothes_store.domain.repositories.*;
import com.rminaya.clothes.store.clothes_store.util.enums.Operacion;
import com.rminaya.clothes.store.clothes_store.util.enums.Tables;
import com.rminaya.clothes.store.clothes_store.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class KardexHelper {
    private KardexRepository kardexRepository;
    private KardexDetalleRepository kardexDetalleRepository;
    private GuiaRemisionRepository guiaRemisionRepository;
    private TipoOperacionRepository tipoOperacionRepository;
    private ProductoRepository productoRepository;

    @Transactional
    public void procesarGuiaRemision(Long guiaRemisionId) {
        // Buscamos la guia con sus detalles, que exista en la BBDD y que NO esté procesada.
        GuiaRemisionEntity guiaToUpdate = this.guiaRemisionRepository
                .findByIdAndNoProcesado(guiaRemisionId)
                .orElseThrow(() -> new IdNotFoundException(Tables.guia_remision.name()));
        guiaToUpdate.setProcesado(true);

        GuiaRemisionEntity guiaRemision = this.guiaRemisionRepository.save(guiaToUpdate);

        List<KardexDetalleEntity> kardexDetalles = new ArrayList<>();
        // Iteramos los detalles de la guía para registrar su "kardex detalle" y actualizar los saldos correspondientes.
        guiaRemision.getGuiaRemisionDetalles()
                .stream()
                .filter(guiaDetalle -> guiaDetalle.getEliminado().equals(false))
                .forEach(guiaDetalle -> {
                    // Obtenemos el ultimo saldo del producto a registrar
                    Integer kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
                            guiaDetalle.getProducto().getId(),
                            this.generateLocalDateTime(guiaDetalle.getGuiaRemision().getFechaEmision()));
                    // Obtenemos el ultimo saldo del producto registrado en algun kardex
                    Integer ultimoSaldoCantidad = 0;

                    if (kardexDetalleUltimoSaldo != null) {
                        ultimoSaldoCantidad = kardexDetalleUltimoSaldo;
                    }
                    // Registramos los detalles del kardex
                    KardexDetalleEntity KardexDetalleToPersist = KardexDetalleEntity.builder()
                            .fechaEmision(this.generateLocalDateTime(guiaRemision.getFechaEmision()))
                            .producto(guiaDetalle.getProducto())
                            .entradaCantidad(guiaDetalle.getCantidad())
                            .entradaPrecio(guiaDetalle.getTotalDetalle() / guiaDetalle.getCantidad())
                            .entradaTotal(guiaDetalle.getTotalDetalle())
                            .salidaCantidad(0)
                            .salidaPrecio(0D)
                            .salidaTotal(0D)
                            .saldoCantidad(ultimoSaldoCantidad + guiaDetalle.getCantidad())
                            .saldoPrecio(guiaDetalle.getTotalDetalle() / guiaDetalle.getCantidad())
                            .saldoTotal((guiaDetalle.getTotalDetalle() / guiaDetalle.getCantidad()) * (ultimoSaldoCantidad + guiaDetalle.getCantidad()))
                            .eliminado(false)
                            .build();
                    // Añadimos el detalle al kardex
                    kardexDetalles.add(KardexDetalleToPersist);
                    // Obtengo los diferentes "kardex detalles" a actualizar según el nuevo movimiento
                    List<KardexDetalleEntity> kardexDetallesByProducto = this.kardexDetalleRepository.detallesByProductoAndFechaEmision(
                            guiaDetalle.getProducto().getId(),
                            this.generateLocalDateTime(guiaRemision.getFechaEmision()));
                    // Re asignamos el último saldo cantidad
                    Integer nuevoUltimoSaldoCantidad = KardexDetalleToPersist.getSaldoCantidad();
                    // Actualizamos del stock de cada producto
                    ProductoEntity productoToUpdate = this.productoRepository.findById(guiaDetalle.getProducto().getId())
                            .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));
                    productoToUpdate.setStock(nuevoUltimoSaldoCantidad);
                    this.productoRepository.save(productoToUpdate);
                    // Iteramos los diferentes "kardex detalles" obtenidos, los cuales serán actualizados con los nuevos saldos
                    for (KardexDetalleEntity detalleByProducto : kardexDetallesByProducto) {
                        KardexDetalleEntity kardexDetalleToUpdate = this.kardexDetalleRepository.findById(detalleByProducto.getId())
                                .filter(detalleEncontrado -> detalleEncontrado.getEliminado().equals(false))
                                .orElseThrow(() -> new IdNotFoundException(Tables.kardex_detalle.name()));

                        kardexDetalleToUpdate.setSaldoCantidad(nuevoUltimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad());
                        //TODO faltaria agregar el saldo precio, lo veremos cuando se implemente la aplicación cliente
//                        kardexDetalleToUpdate.setSaldoPrecio(0d);
                        kardexDetalleToUpdate.setSaldoTotal(detalleByProducto.getSaldoPrecio() * kardexDetalleToUpdate.getSaldoCantidad());
                        // Actualizamos el "kardex detalle"
                        kardexDetalleToUpdate = this.kardexDetalleRepository.save(kardexDetalleToUpdate);
                        // Volvemos a re asignar el último saldo cantidad
                        nuevoUltimoSaldoCantidad = kardexDetalleToUpdate.getSaldoCantidad();
                    }
                });
        // Obtenemos el tipo operación para una "consignación recibida"
        TipoOperacionEntity tipoOperacion = this.tipoOperacionRepository
                .findById(Operacion.CONSIGNACION_RECIBIDA.getId())
                .orElseThrow(() -> new IdNotFoundException(Tables.tipo_operacion.name()));
        // Creamos el kardex con sus detalles
        KardexEntity KardexToPersist = KardexEntity.builder()
                .numero(guiaRemision.getNumero())
                .comprobanteId(guiaRemision.getId())
                .fechaEmision(this.generateLocalDateTime(guiaRemision.getFechaEmision()))
                .tipoOperacion(tipoOperacion)
                .kardexDetalles(kardexDetalles)
                .eliminado(false)
                .build();

        this.kardexRepository.save(KardexToPersist);
    }

    @Transactional
    public void desprocesarGuiaRemision(Long guiaRemisionId) {
        // Buscamos la guia con sus detalles, que exista en la BBDD y que SI esté procesada.
        GuiaRemisionEntity guiaToUpdate = this.guiaRemisionRepository
                .findByIdAndProcesado(guiaRemisionId)
                .orElseThrow(() -> new IdNotFoundException(Tables.guia_remision.name()));
        // Actualizamos el estado a no procesado.
        guiaToUpdate.setProcesado(false);
        GuiaRemisionEntity guiaRemision = this.guiaRemisionRepository.save(guiaToUpdate);
        // Iteramos los detalles de la guía, para buscar los detalles de los kardex y actualizar sus saldos
        guiaRemision.getGuiaRemisionDetalles().stream()
                .filter(guiaRemisionDetalle -> guiaRemisionDetalle.getEliminado().equals(false))
                .forEach(guiaRemisionDetalle -> {
                    // Obtengo los diferentes "kardex detalles" a actualizar según el nuevo moviemiento
                    List<KardexDetalleEntity> kardexDetallesByProducto = this.kardexDetalleRepository
                            .detallesByProductoAndFechaEmision(
                                    guiaRemisionDetalle.getProducto().getId(),
                                    this.generateLocalDateTime(guiaRemision.getFechaEmision()));
                    // Obtenemos el ultimo saldo del producto a registrar
                    Integer kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
                            guiaRemisionDetalle.getProducto().getId(),
                            this.generateLocalDateTime(guiaRemisionDetalle.getGuiaRemision().getFechaEmision()));
                    // Obtenemos el ultimo saldo del producto registrado en algun kardex
                    Integer ultimoSaldoCantidad = 0;

                    if (kardexDetalleUltimoSaldo != null) {
                        ultimoSaldoCantidad = kardexDetalleUltimoSaldo;
                    }
                    // Actualizamos del stock de cada producto
                    ProductoEntity productoBuscado = this.productoRepository
                            .findById(guiaRemisionDetalle.getProducto().getId())
                            .orElseThrow(() -> new IdNotFoundException(Tables.productos.name()));
                    productoBuscado.setStock(ultimoSaldoCantidad);
                    this.productoRepository.save(productoBuscado);
                    // Iteramos los diferentes "kardex detalles" obtenidos, los cuales serán actualizados con los nuevos saldos
                    for (KardexDetalleEntity detalleByProducto : kardexDetallesByProducto) {

                        KardexDetalleEntity kardexDetalleUpdate = this.kardexDetalleRepository.findById(detalleByProducto.getId())
                                .filter(kardexDetalle -> kardexDetalle.getEliminado().equals(false))
                                .orElseThrow(() -> new IdNotFoundException(Tables.kardex_detalle.name()));

                        kardexDetalleUpdate.setSaldoCantidad(ultimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad());
                        //TODO faltaria agregar el saldo precio, lo veremos cuando se implemente la aplicación cliente
//                        kardexDetalleUpdate.setSaldoPrecio(0d);
                        kardexDetalleUpdate.setSaldoTotal(detalleByProducto.getSaldoPrecio() * (ultimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad()));
                        // Actualizamos el "kardex detalle"
                        kardexDetalleUpdate = this.kardexDetalleRepository.save(kardexDetalleUpdate);
                        // Re asignamos el último saldo cantidad
                        ultimoSaldoCantidad = kardexDetalleUpdate.getSaldoCantidad();
                    }
                });

        TipoOperacionEntity tipoOperacion = this.tipoOperacionRepository
                .findById(Operacion.CONSIGNACION_RECIBIDA.getId())
                .orElseThrow(() -> new IdNotFoundException(Tables.tipo_operacion.name()));
        // Buscamos el kardex y sus detalles para eliminarlos
        KardexEntity kardexToUpdate = this.kardexRepository.kardexByIdAndTipoOperacion(guiaRemisionId, tipoOperacion.getId())
                .filter(kardex1 -> kardex1.getEliminado().equals(false))
                .orElseThrow(() -> new IdNotFoundException(Tables.kardex.name()));
        // Eliminamos el kardex y sus detalles
        kardexToUpdate.setEliminado(true);
        kardexToUpdate.getKardexDetalles().forEach(kardexDetalle -> {
            kardexDetalle.setEliminado(true);
        });
        // Guardamos los cambios del cambio de estado a eliminado
        this.kardexRepository.save(kardexToUpdate);
    }

    @Transactional
    public void procesarBoletaVenta(Long boletaVentaId) {

    }

    @Transactional
    public void desprocesarBoletaVenta(Long boletaVentaId) {

    }

    private LocalDateTime generateLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }


}
