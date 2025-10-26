package com.barcito.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barcito.dto.MesaDto;
import com.barcito.dto.PedidoDto;
import com.barcito.dto.PedidoRequest;
import com.barcito.entity.Mesa;
import com.barcito.entity.Pedido;
import com.barcito.service.VentaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200") // CORS para Angular
@RestController
@RequestMapping("/api/ventas")
public class VentaController {
	private final VentaService ventaService;

    @Autowired
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // --- Endpoints de Mesa ---

    @GetMapping(value = "/mesas/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MesaDto>> listarMesas() throws Exception {
        List<Mesa> mesas = ventaService.listarMesas();
        List<MesaDto> dtos = new ArrayList<>();
        for (Mesa mesa : mesas) {
            dtos.add(mesa.toDto());
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/mesas/agregar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> crearMesa(@Valid @RequestBody MesaDto mesaDto) throws Exception {
        mesaDto.setId(0); // Aseguramos que sea creación
        ventaService.crearMesa(mesaDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    //no implementado a frontend
    @PostMapping(value = "/mesas/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarMesa(@RequestBody Integer id) throws Exception {
        ventaService.eliminarMesa(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/mesas/reservar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MesaDto> reservarMesa(@PathVariable int id) throws Exception {
        Mesa mesaActualizada = ventaService.reservarMesa(id);
        return new ResponseEntity<>(mesaActualizada.toDto(), HttpStatus.OK);
    }

    // --- Endpoints de Pedido --- (no implementadas algunas)
    
    @PostMapping(value = "/pedidos/agregar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) throws Exception {
        ventaService.crearPedido(pedidoRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/pedidos/cerrar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDto> cerrarPedido(@PathVariable int id) throws Exception {
        Pedido pedidoActualizado = ventaService.cerrarPedido(id);
        return new ResponseEntity<>(pedidoActualizado.toDto(), HttpStatus.OK);
    }
    
    @PostMapping(value = "/mesas/cerrar-venta/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDto> cerrarVentaPorMesa(@PathVariable int id) throws Exception {
        Pedido pedidoCerrado = ventaService.cerrarVentaPorMesa(id);
        return new ResponseEntity<>(pedidoCerrado.toDto(), HttpStatus.OK);
    }
}
