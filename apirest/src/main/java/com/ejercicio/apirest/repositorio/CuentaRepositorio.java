package com.ejercicio.apirest.repositorio;

import com.ejercicio.apirest.modelo.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepositorio extends CrudRepository<Cuenta, String> {
    Optional<Cuenta> findByNumeroCuentaAndCliente_Id(String numeroCuenta, Integer clienteId);
}
