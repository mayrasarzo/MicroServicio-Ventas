package com.barcito.service;

import java.util.List;

import com.barcito.dto.MesaDto;
import com.barcito.dto.PedidoRequest;
import com.barcito.entity.Mesa;
import com.barcito.entity.Pedido;

public interface VentaService {
	// --- Métodos de Mesa ---
    
    public void crearMesa(MesaDto mesaDto) throws Exception;
    
    public Mesa reservarMesa(int mesaId) throws Exception;
    
    public Mesa buscarMesaPorId(int id) throws Exception;

    public List<Mesa> listarMesas() throws Exception;
    
    public void eliminarMesa(int id) throws Exception;

    public Pedido cerrarVentaPorMesa(int mesaId) throws Exception;
    
    // --- Métodos de Pedido ---

    public void crearPedido(PedidoRequest pedidoRequest) throws Exception;

    public Pedido cerrarPedido(int pedidoId) throws Exception;
    
    public Pedido buscarPedidoPorId(int id) throws Exception;
}
