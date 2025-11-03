package com.barcito.dto;

import com.barcito.entity.ProductoPedido;
import com.barcito.enums.TipoProducto;

import jakarta.validation.constraints.NotNull;

public class ProductoPedidoDto {
	private int id;
	private Integer productoId;
	private int cantidad;
	private int precioUnitario;
	@NotNull(message = "El tipo de producto no puede ser nulo")
	private TipoProducto tipo;
	private String nombrePromocion;
	
    public ProductoPedidoDto() {
    	
    }

	public ProductoPedidoDto(int id, Integer productoId, int cantidad, int precioUnitario,
			@NotNull(message = "El tipo de producto no puede ser nulo") TipoProducto tipo, String nombrePromocion) {
		super();
		this.id = id;
		this.productoId = productoId;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.tipo = tipo;
		this.nombrePromocion = nombrePromocion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(int precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}

	public String getNombrePromocion() {
		return nombrePromocion;
	}

	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	public ProductoPedido toEntity() {
        return new ProductoPedido(getId(), getProductoId(), getCantidad(), getPrecioUnitario(), getTipo(), getNombrePromocion());
    }
}
