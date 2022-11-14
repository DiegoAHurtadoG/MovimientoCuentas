package com.ejercicio.apirest.controlador;

import com.ejercicio.apirest.modelo.Movimiento;
import com.ejercicio.apirest.repositorio.MovimientoRepositorio;
import com.ejercicio.apirest.servicio.MovimientoServicio;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movimientos")
public class MovimientoControlador {

    @Autowired
    private MovimientoServicio movimientoServicio;

    @GetMapping("")
    public List<Movimiento> listar() {
        return movimientoServicio.listarTodos();
    }

    @GetMapping("/{movimientoId}")
    public Movimiento obtenerMovimiento(@PathVariable("movimientoId") Integer movimientoId) {
        return movimientoServicio.obtenerEntidad(movimientoId);
    }

    @PostMapping("")
    public Movimiento guardar(@RequestBody Movimiento movimiento) throws Exception {
        movimiento.setEstado(Boolean.TRUE);
        return movimientoServicio.crearActualizar(movimiento);
    }

    @PutMapping("")
    public Movimiento actualizar(@RequestBody Movimiento movimiento) throws Exception {
        return movimientoServicio.crearActualizar(movimiento);
    }

    @DeleteMapping("/{movimientoId}")
    public ResponseEntity<Object> eliminar(@PathVariable("movimientoId") Integer movimientoId) throws Exception {
        movimientoServicio.eliminarEntidad(movimientoId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Eliminación correcta"));
    }

    @GetMapping("/reportes")
    public List<MovimientoRepositorio.EstadoCuenta> obtenerEstadoCuenta(@RequestParam Map<String, String> parametros) throws ParseException {
        return movimientoServicio.obtenerEstadoCuenta(new SimpleDateFormat("dd-MM-yyyy").parse(parametros.get("fechaDesde"))
                , new SimpleDateFormat("dd-MM-yyyy").parse(parametros.get("fechaHasta")), parametros.get("identificacion"));
    }
}
