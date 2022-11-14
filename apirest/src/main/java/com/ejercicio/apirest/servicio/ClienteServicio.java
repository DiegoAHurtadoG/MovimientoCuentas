package com.ejercicio.apirest.servicio;

import com.ejercicio.apirest.excepcion.ClienteNotFoundException;
import com.ejercicio.apirest.modelo.Cliente;
import com.ejercicio.apirest.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Cliente> listarTodos() {
        return (List<Cliente>) clienteRepositorio.findAll();
    }

    public Cliente obtenerEntidad(Integer entidadId) {
        return clienteRepositorio.findById(entidadId).orElseThrow(() -> new NoSuchElementException("Cliente No encontrado clienteId: " + entidadId));
    }

    public Cliente crearActualizar(Cliente cliente) throws Exception {
        try {
            return clienteRepositorio.save(cliente);
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
            clienteRepositorio.deleteById(entidadId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ClienteNotFoundException("Cliente No encontrado clienteId: " + entidadId);
        }
    }
}
