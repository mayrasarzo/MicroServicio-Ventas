package com.barcito.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoPedidoRequest {
	@NotNull private int productoId;
    @NotNull @Positive private int cantidad;
    
    public ProductoPedidoRequest() {
    	
    }
    
	public ProductoPedidoRequest(@NotNull int productoId, @NotNull @Positive int cantidad) {
		super();
		this.productoId = productoId;
		this.cantidad = cantidad;
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
    
    
}
