package com.ejercicio.apirest.controlador;

import com.ejercicio.apirest.modelo.Cliente;
import com.ejercicio.apirest.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("")
    public List<Cliente> listar() {
        return clienteServicio.listarTodos();
    }

    @GetMapping("/{clienteId}")
    public Cliente obtenerCliente(@PathVariable("clienteId") Integer clienteId) {
        return clienteServicio.obtenerEntidad(clienteId);
    }

    @PostMapping("")
    public Cliente guardar(@RequestBody Cliente cliente) throws Exception {
        cliente.setEstado(Boolean.TRUE);
        return clienteServicio.crearActualizar(cliente);
    }

    @PutMapping("")
    public Cliente actualizar(@RequestBody Cliente cliente) throws Exception {
        return clienteServicio.crearActualizar(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Object> eliminar(@PathVariable("clienteId") Integer clienteId) throws Exception {
        clienteServicio.eliminarEntidad(clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Eliminación correcta"));
    }
}
