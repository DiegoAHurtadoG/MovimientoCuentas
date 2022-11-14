package com.ejercicio.apirest.servicio;

import com.ejercicio.apirest.excepcion.CuentaNotFoundException;
import com.ejercicio.apirest.modelo.Cuenta;
import com.ejercicio.apirest.repositorio.CuentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class CuentaServicio {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private ClienteServicio clienteServicio;

    public List<Cuenta> listarTodas() {
        return (List<Cuenta>) cuentaRepositorio.findAll();
    }

    public Cuenta obtenerEntidad(String entidadId) {
        return cuentaRepositorio.findById(entidadId).orElseThrow(() -> new NoSuchElementException("Cuenta No encontrada numeroCuenta: " + entidadId));
    }

    public Cuenta crearActualizar(Cuenta cuenta) throws Exception {
        try {
            if (obtenerPornumCuentaYclienteId(cuenta.getNumeroCuenta(), cuenta.getCliente().getId()).isPresent()) {
                throw new Exception("La cuenta:" + cuenta.getNumeroCuenta() + " esta asignada a otro cliente");
            }

            return cuentaRepositorio.save(cuenta);
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

    public void eliminarEntidad(String entidadId) throws Exception {
        try {
            cuentaRepositorio.deleteById(entidadId);
        } catch (EmptyResultDataAccessException ex) {
            throw new CuentaNotFoundException("Cuenta No encontrada numeroCuenta: " + entidadId);
        }
    }

    public Optional<Cuenta> obtenerPornumCuentaYclienteId(String numCuenta, Integer clienteId) {
        return cuentaRepositorio.findByNumeroCuentaAndCliente_Id(numCuenta, clienteId);
    }
}
