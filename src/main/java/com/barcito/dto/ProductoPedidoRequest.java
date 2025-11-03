package com.barcito.dto;

import com.barcito.enums.TipoProducto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoPedidoRequest {
	private Integer productoId;
    @NotNull @Positive private int cantidad;
    
    private String nombrePromocion; // Para "Descuento Menú"
    private Integer precioUnitario; // Para el precio (ej: -10)
    private TipoProducto tipo;
    
    public ProductoPedidoRequest() {
    	
    }

	public ProductoPedidoRequest(Integer productoId, @NotNull @Positive int cantidad, String nombrePromocion,
			Integer precioUnitario, TipoProducto tipo) {
		super();
		this.productoId = productoId;
		this.cantidad = cantidad;
		this.nombrePromocion = nombrePromocion;
		this.precioUnitario = precioUnitario;
		this.tipo = tipo;
	}

	public Integer getProductoId() {
		return productoId;
	}

	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombrePromocion() {
		return nombrePromocion;
	}

	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	public Integer getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Integer precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}
    
}
