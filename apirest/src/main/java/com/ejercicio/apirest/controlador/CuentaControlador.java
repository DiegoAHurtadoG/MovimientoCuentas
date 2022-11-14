package com.ejercicio.apirest.controlador;

import com.ejercicio.apirest.modelo.Cuenta;
import com.ejercicio.apirest.servicio.CuentaServicio;
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
@RequestMapping("/cuentas")
public class CuentaControlador {

    @Autowired
    private CuentaServicio cuentaServicio;

    @GetMapping("")
    public List<Cuenta> listar() {
        return cuentaServicio.listarTodas();
    }

    @GetMapping("/{numCuenta}")
    public Cuenta obtenerCuenta(@PathVariable("numCuenta") String numCuenta) {
        return cuentaServicio.obtenerEntidad(numCuenta);
    }

    @PostMapping("")
    public Cuenta guardar(@RequestBody Cuenta cuenta) throws Exception {
        cuenta.setEstado(Boolean.TRUE);
        cuenta.setSaldoActual(cuenta.getSaldoInicial());
        return cuentaServicio.crearActualizar(cuenta);
    }

    @PutMapping("")
    public Cuenta actualizar(@RequestBody Cuenta cuenta) throws Exception {
        return cuentaServicio.crearActualizar(cuenta);
    }

    @DeleteMapping("/{numCuenta}")
    public ResponseEntity<Object> eliminar(@PathVariable("numCuenta") String numCuenta) throws Exception {
        cuentaServicio.eliminarEntidad(numCuenta);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Eliminación correcta"));
    }
}
