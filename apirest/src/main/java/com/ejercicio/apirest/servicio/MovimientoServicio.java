package com.ejercicio.apirest.servicio;

import com.ejercicio.apirest.enumerador.TipoMovimiento;
import com.ejercicio.apirest.excepcion.MovimientoNotFoundException;
import com.ejercicio.apirest.modelo.Movimiento;
import com.ejercicio.apirest.repositorio.MovimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class MovimientoServicio {

    private final BigDecimal limiteRetiro = BigDecimal.valueOf(1000);

    @Autowired
    private MovimientoRepositorio movimientoRepositorio;

    @Autowired
    private CuentaServicio cuentaServicio;

    public List<Movimiento> listarTodos() {
        return (List<Movimiento>) movimientoRepositorio.findAll();
    }

    public Movimiento obtenerEntidad(Integer entidadId) {
        return movimientoRepositorio.findById(entidadId).orElseThrow(() -> new NoSuchElementException("Movimiento No encontrado movimientoId: " + entidadId));
    }

    public Movimiento crearActualizar(Movimiento movimiento) throws Exception {
        try {
            validarSignoMovimiento(movimiento);
            validarLimiteDebito(movimiento);
            validarSaldoDisponible(movimiento);
            return movimientoRepositorio.save(movimiento);
        } catch (DataIntegrityViolationException | TransactionSystemException ex) {
            throw new Exception(ex.getRootCause().getMessage());
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            String errorMessage = "Restricción";
            if (!violations.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                violations.forEach(violation -> builder.append(" *" + violation.getPropertyPath() + ":" + violation.getMessage()));
                errorMessage = builder.toString();
            }
            throw new Exception(errorMessage);
        }
    }

    public void eliminarEntidad(Integer entidadId) throws Exception {
        try {
            movimientoRepositorio.deleteById(entidadId);
        } catch (EmptyResultDataAccessException ex) {
            throw new MovimientoNotFoundException("Movimiento No encontrada movimientoId: " + entidadId);
        }
    }

    private void validarSignoMovimiento(Movimiento movimiento) throws Exception {
        if (movimiento.getValor().compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("No se debe ingresar valores negativos");
    }

    private void validarLimiteDebito(Movimiento movimiento) throws Exception {
        if (movimiento.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            BigDecimal totalDebito = movimientoRepositorio.obteneCupoDiarioPorCuentaYFecha(movimiento.getCuenta().getNumeroCuenta(), movimiento.getFecha());
            if (totalDebito == null)
                totalDebito = BigDecimal.ZERO;

            if (totalDebito.compareTo(limiteRetiro) == 0
                    || totalDebito.compareTo(limiteRetiro) > 0)
                throw new Exception("Cupo diario Excedido");
        }
    }

    private void validarSaldoDisponible(Movimiento movimiento) throws Exception {
        movimiento.setCuenta(cuentaServicio.obtenerEntidad(movimiento.getCuenta().getNumeroCuenta()));

        if (movimiento.getTipoMovimiento().equals(TipoMovimiento.DEBITO)
                && (movimiento.getCuenta().getSaldoActual().compareTo(BigDecimal.ZERO) == 0
                || movimiento.getCuenta().getSaldoActual().compareTo(movimiento.getValor()) < 0))
            throw new Exception("Saldo no disponible");

        if (movimiento.getTipoMovimiento().equals(TipoMovimiento.DEBITO))
            movimiento.setSaldoDisponible(movimiento.getCuenta().getSaldoActual().subtract(movimiento.getValor()));
        else
            movimiento.setSaldoDisponible(movimiento.getCuenta().getSaldoActual().add(movimiento.getValor()));

        movimiento.getCuenta().setSaldoActual(movimiento.getSaldoDisponible());
    }

    public List<MovimientoRepositorio.EstadoCuenta> obtenerEstadoCuenta(Date fechaDesde, Date fechaHasta, String identificacion) {
        return movimientoRepositorio.obtenerEstadoCuenta(fechaDesde, fechaHasta, identificacion);
    }
}
