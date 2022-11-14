package com.ejercicio.apirest.repositorio;

import com.ejercicio.apirest.modelo.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {

}
