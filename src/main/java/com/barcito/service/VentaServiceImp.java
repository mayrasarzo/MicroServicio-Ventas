package com.barcito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.barcito.dto.MesaDto;
import com.barcito.dto.ProductoDto;
import com.barcito.dto.ProductoPedidoRequest;
import com.barcito.dto.PedidoRequest;
import com.barcito.entity.Mesa;
import com.barcito.entity.Pedido;
import com.barcito.entity.ProductoPedido;
import com.barcito.enums.EstadoMesa;
import com.barcito.enums.EstadoPedido;
import com.barcito.enums.TipoProducto;
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
        nuevaMesa.setEstado(EstadoMesa.LIBRE);
        mesaRepository.save(nuevaMesa);
    }
    
    @Override
    public Mesa reservarMesa(int mesaId) throws Exception {
        Mesa mesa = this.buscarMesaPorId(mesaId);
        
        if (mesa.getEstado() != EstadoMesa.LIBRE) {
            throw new Exception("La mesa no está libre");
        }
        
        mesa.setEstado(EstadoMesa.RESERVADA);
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

	@Override
    public Pedido cerrarVentaPorMesa(int mesaId) throws Exception {
        Mesa mesa = this.buscarMesaPorId(mesaId);

        if (mesa.getEstado() != EstadoMesa.RESERVADA) {
            throw new Exception("La mesa no está reservada, no se puede cerrar venta.");
        }

        List<Pedido> todosLosPedidos = new ArrayList<>();
        pedidoRepository.findAll().forEach(todosLosPedidos::add);

        Pedido pedidoAbierto = null;
        for (Pedido p : todosLosPedidos) {
            if (p.getMesaId() == mesaId && p.getEstado() == EstadoPedido.ABIERTO) {
                pedidoAbierto = p;
                break;
            }
        }

        if (pedidoAbierto == null) {
            throw new Exception("No se encontró un pedido abierto para la mesa con id: " + mesaId);
        }

        return this.cerrarPedido(pedidoAbierto.getId());
    }
	
	// --- Implementación de Métodos de Pedido ---
	
	@Override
    public void crearPedido(PedidoRequest pedidoRequest) throws Exception {
        Mesa mesa = this.buscarMesaPorId(pedidoRequest.getMesaId());

        if (mesa.getEstado() == EstadoMesa.CERRADA) {
             throw new Exception("La mesa está cerrada y no acepta pedidos");
        }

        Pedido pedido = new Pedido();
        pedido.setMesaId(mesa.getId());
        pedido.setEstado(EstadoPedido.ABIERTO);
        pedido.setItems(new ArrayList<>());

        for (ProductoPedidoRequest itemRequest : pedidoRequest.getItems()) {
            
            ProductoPedido pp = new ProductoPedido();
            pp.setCantidad(itemRequest.getCantidad());

            // Verificamos si es un Producto Real o una Promo
            if (itemRequest.getProductoId() != null) {
                // Es un producto real, hacemos la lógica de siempre
                String url = PRODUCTOS_API_URL + "buscar/" + itemRequest.getProductoId();
                ProductoDto producto;
                
                try {
                    producto = restTemplate.getForObject(url, ProductoDto.class);
                     if (producto == null) {
                        throw new Exception("Producto no encontrado con ID: " + itemRequest.getProductoId());
                    }
                } catch (Exception e) {
                    throw new Exception("Error al consultar producto con ID: " + itemRequest.getProductoId() + ". " + e.getMessage());
                }

                pp.setProductoId(producto.getId());
                pp.setPrecioUnitario(producto.getPrecio()); // Precio real
                pp.setTipo(producto.getTipo());
                // nombrePromocion es null (está bien)

            } else {
                // Es un item de Promoción/Descuento
                if (itemRequest.getPrecioUnitario() == null || itemRequest.getNombrePromocion() == null) {
                    throw new Exception("El item de promoción es inválido.");
                }
                
                pp.setProductoId(null);
                pp.setPrecioUnitario(itemRequest.getPrecioUnitario()); // El precio de la promo (ej: -10)
                pp.setTipo(TipoProducto.PROMO); // Usamos el nuevo Enum
                pp.setNombrePromocion(itemRequest.getNombrePromocion());
            }

            pedido.getItems().add(pp);
        }

        if (mesa.getEstado() == EstadoMesa.LIBRE) {
            mesa.setEstado(EstadoMesa.RESERVADA);
            mesaRepository.save(mesa);
        }

        pedidoRepository.save(pedido);
    }

	@Override
    public Pedido cerrarPedido(int pedidoId) throws Exception {
        Pedido pedido = this.buscarPedidoPorId(pedidoId);
        
        if (pedido.getEstado() == EstadoPedido.CERRADO) {
            throw new Exception("El pedido ya está cerrado");
        }

        int totalCalculado = 0;
        if (pedido.getItems() != null) {
             for (ProductoPedido item : pedido.getItems()) {
                totalCalculado += item.getPrecioUnitario() * item.getCantidad();
            }
        }

        pedido.setTotal(totalCalculado);
        pedido.setEstado(EstadoPedido.CERRADO);

        Mesa mesa = this.buscarMesaPorId(pedido.getMesaId());
        mesa.setEstado(EstadoMesa.CERRADA);
        mesaRepository.save(mesa);

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
}
