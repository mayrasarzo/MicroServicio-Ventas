package com.barcito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.barcito.dto.MesaDto;
import com.barcito.dto.ProductoDto;
import com.barcito.dto.ProductoPedidoRequest;
import com.barcito.dto.PedidoRequest;
import com.barcito.entity.Mesa;
import com.barcito.entity.Pedido;
import com.barcito.entity.ProductoPedido;
import com.barcito.repository.MesaRepository;
import com.barcito.repository.PedidoRepository;

@Service
public class VentaServiceImp implements VentaService{

	// URL base del servicio de productos
    private final String PRODUCTOS_API_URL = "http://localhost:8081/api/productos/";

    private final MesaRepository mesaRepository;
    private final PedidoRepository pedidoRepository;
    private final RestTemplate restTemplate;
	
    @Autowired
    public VentaServiceImp(MesaRepository m, PedidoRepository p, RestTemplate rt) {
        this.mesaRepository = m;
        this.pedidoRepository = p;
        this.restTemplate = rt;
    }
    
	@Override
	public void crearMesa(MesaDto mesaDto) throws Exception {
		Mesa nuevaMesa = mesaDto.toEntity();
        nuevaMesa.setEstado("libre");
        mesaRepository.save(nuevaMesa);
	}

	@Override
	public Mesa reservarMesa(int mesaId) throws Exception {
		Mesa mesa = this.buscarMesaPorId(mesaId);
        if (!mesa.getEstado().equals("libre")) {
            throw new Exception("La mesa no está libre");
        }
        mesa.setEstado("reservada");
        return mesaRepository.save(mesa); 
	}

	@Override
	public Mesa buscarMesaPorId(int id) throws Exception {
		Optional<Mesa> mesaOptional = mesaRepository.findById(id);
        if (mesaOptional.isEmpty()) {
            throw new Exception("Mesa no encontrada con id: " + id);
        }
        return mesaOptional.get();
	}

	@Override
	public List<Mesa> listarMesas() throws Exception {
		List<Mesa> mesas = new ArrayList<>();
        mesaRepository.findAll().forEach(mesas::add); // Agrega cada mesa a la lista
        return mesas;
	}

	@Override
	public void eliminarMesa(int id) throws Exception {
		this.buscarMesaPorId(id); // Verifica si existe antes de borrar
        mesaRepository.deleteById(id);
	}

	// --- Implementación de Métodos de Pedido ---
	
	@Override
	public void crearPedido(PedidoRequest pedidoRequest) throws Exception {
		// 1. Validamos la mesa
        Mesa mesa = this.buscarMesaPorId(pedidoRequest.getMesaId());
        if (mesa.getEstado().equals("cerrada")) {
             throw new Exception("La mesa está cerrada y no acepta pedidos");
        }
        
        // 2. Creamos el pedido
        Pedido pedido = new Pedido();
        pedido.setMesaId(mesa.getId()); 
        pedido.setEstado("abierto");
        pedido.setItems(new ArrayList<>());

        // 3. Procesamos los items
        for (ProductoPedidoRequest itemRequest : pedidoRequest.getItems()) {
            
        	ProductoDto producto;
            String url = PRODUCTOS_API_URL + "buscar/" + itemRequest.getProductoId();
            
            try {
                producto = restTemplate.getForObject(url, ProductoDto.class);
                 if (producto == null) {
                    throw new Exception("Producto no encontrado con ID: " + itemRequest.getProductoId());
                }
            } catch (HttpClientErrorException.NotFound e) {
                 throw new Exception("Producto no encontrado con ID: " + itemRequest.getProductoId());
            } catch (Exception e) {
                throw new Exception("Error al consultar producto con ID: " + itemRequest.getProductoId() + ". Detalles: " + e.getMessage());
            }

            // Creamos el item del pedido con los datos seguros
            ProductoPedido pp = new ProductoPedido();
            pp.setProductoId(producto.getId());
            pp.setCantidad(itemRequest.getCantidad());
            pp.setPrecioUnitario(producto.getPrecio()); // Precio del backend
            pp.setTipo(producto.getTipo());
            
            pedido.getItems().add(pp); // Agregamos el item a la lista del pedido
        }

        // 4. Actualizamos estado de la mesa (si estaba libre)
        if (mesa.getEstado().equals("libre")) {
            mesa.setEstado("reservada");
            mesaRepository.save(mesa);
        }

        // 5. Guardamos el pedido (y sus items por la cascada)
        pedidoRepository.save(pedido);
	}

	@Override
	public Pedido cerrarPedido(int pedidoId) throws Exception {
		// 1. Buscamos el pedido
        Pedido pedido = this.buscarPedidoPorId(pedidoId);
        if (pedido.getEstado().equals("cerrado")) {
            throw new Exception("El pedido ya está cerrado");
        }

        // 2. Calculamos el total
        int totalCalculado = 0;
        if (pedido.getItems() != null) { // Chequeo por si la lista es null
             for (ProductoPedido item : pedido.getItems()) {
                totalCalculado += item.getPrecioUnitario() * item.getCantidad();
            }
        }

        pedido.setTotal(totalCalculado);
        pedido.setEstado("cerrado");

        // 3. Actualizamos la mesa
        Mesa mesa = this.buscarMesaPorId(pedido.getMesaId());
        mesa.setEstado("cerrada");
        mesaRepository.save(mesa); 

        // 4. Guardamos el pedido actualizado y lo devolvemos
        return pedidoRepository.save(pedido);
	}

	@Override
	public Pedido buscarPedidoPorId(int id) throws Exception {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if(pedidoOptional.isEmpty()) {
            throw new Exception("Pedido no encontrado con id: " + id);
        }
        return pedidoOptional.get();
	}

	@Override
	public Pedido cerrarVentaPorMesa(int mesaId) throws Exception {
		// 1. Validamos que la mesa exista y esté reservada
        Mesa mesa = this.buscarMesaPorId(mesaId);
        if (!mesa.getEstado().equals("reservada")) {
            throw new Exception("La mesa no está reservada, no se puede cerrar venta.");
        }

        // 2. Buscamos el pedido ABIERTO asociado a esa mesa
        // (Esta es una forma simple, asume solo UN pedido abierto por mesa)
        List<Pedido> todosLosPedidos = new ArrayList<>();
        pedidoRepository.findAll().forEach(todosLosPedidos::add); // Get all pedidos

        Pedido pedidoAbierto = null;
        for (Pedido p : todosLosPedidos) {
            // Check if it belongs to the table AND is open
            if (p.getMesaId() == mesaId && p.getEstado().equals("abierto")) {
                pedidoAbierto = p;
                break; // Found it
            }
        }

        // 3. Si no se encontró un pedido abierto, lanzamos error
        if (pedidoAbierto == null) {
            throw new Exception("No se encontró un pedido abierto para la mesa con id: " + mesaId);
        }

        // 4. Si se encontró, llamamos a la lógica existente para cerrar ESE pedido
        return this.cerrarPedido(pedidoAbierto.getId());
	}

}
