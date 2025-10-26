package com.barcito.dto;

import com.barcito.entity.ProductoPedido;

public class ProductoPedidoDto {
	private int id;
	private int productoId;
	private int cantidad;
	private int precioUnitario;
	private String tipo;
	
    public ProductoPedidoDto() {
    	
    }
    
    public ProductoPedidoDto(int id, int pId, int cant, int pu, String t) {
        this.id = id; this.productoId = pId; this.cantidad = cant; this.precioUnitario = pu; this.tipo = t;
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

	public ProductoPedido toEntity() {
        return new ProductoPedido(getId(), getProductoId(), getCantidad(), getPrecioUnitario(), getTipo());
    }
}
