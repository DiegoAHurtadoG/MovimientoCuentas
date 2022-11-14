package com.ejercicio.apirest.repositorio;

import com.ejercicio.apirest.modelo.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepositorio extends CrudRepository<Persona, Integer> {

}
