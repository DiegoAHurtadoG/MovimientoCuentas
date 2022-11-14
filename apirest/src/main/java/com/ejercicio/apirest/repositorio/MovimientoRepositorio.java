package com.ejercicio.apirest.repositorio;

import com.ejercicio.apirest.enumerador.TipoCuenta;
import com.ejercicio.apirest.modelo.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepositorio extends CrudRepository<Movimiento, Integer> {

    @Query(value = "select abs(sum(m.valor)) from Movimiento m " +
            "where m.tipoMovimiento = 0 and m.cuenta.numeroCuenta = :numCuenta and m.fecha = :fecha")
    BigDecimal obteneCupoDiarioPorCuentaYFecha(@Param("numCuenta") String numCuenta, @Param("fecha") Date fecha);

    @Query(value = "select m.fecha, p.nombre cliente, ct.numero_cuenta numerocuenta, " +
            "ct.tipo_cuenta tipocuenta, ct.saldo_inicial saldoinicial, ct.estado, " +
            "case when m.tipo_movimiento = 0 then m.valor * -1 else m.valor end movimiento, " +
            "m.saldo_disponible saldodisponible " +
            "from movimiento m " +
            "join cuenta ct on m.numero_cuenta = ct.numero_cuenta " +
            "join cliente c on ct.cliente_id = c.id " +
            "join persona p on c.id = p.id " +
            "where m.fecha between :fechaDesde and :fechaHasta and p.identificacion = :identificacion " +
            "order by m.id ", nativeQuery = true)
    List<EstadoCuenta> obtenerEstadoCuenta(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("identificacion") String identificacion);

    public interface EstadoCuenta {
        Date getFecha();

        String getCliente();

        String getNumeroCuenta();

        TipoCuenta getTipoCuenta();

        BigDecimal getSaldoInicial();

        Boolean getEstado();

        BigDecimal getMovimiento();

        String getSaldoDisponible();
    }
}
