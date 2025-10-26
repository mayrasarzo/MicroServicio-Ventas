package com.barcito.entity;

import com.barcito.dto.ProductoPedidoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "productos_pedido")
public class ProductoPedido {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private int productoId;
	private int cantidad;
	private int precioUnitario;
	private String tipo;
	
	public ProductoPedido(){
		
	}

	public ProductoPedido(int id, int productoId, int cantidad, int precioUnitario, String tipo) {
		super();
		this.id = id;
		this.productoId = productoId;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(int precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public ProductoPedidoDto toDto() {
		return new ProductoPedidoDto(id, productoId, cantidad, precioUnitario, tipo); 
	}
}
