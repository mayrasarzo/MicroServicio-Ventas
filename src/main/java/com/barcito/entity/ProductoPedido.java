package com.barcito.entity;

import com.barcito.dto.ProductoPedidoDto;
import com.barcito.enums.TipoProducto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "productos_pedido")
public class ProductoPedido {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private Integer productoId;
	private int cantidad;
	private int precioUnitario;
	@Enumerated(EnumType.STRING)
    private TipoProducto tipo;
	private String nombrePromocion;
	
	public ProductoPedido(){
		
	}

	public ProductoPedido(int id, Integer productoId, int cantidad, int precioUnitario, TipoProducto tipo,
			String nombrePromocion) {
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

	public ProductoPedidoDto toDto() {
		return new ProductoPedidoDto(id, productoId, cantidad, precioUnitario, tipo, nombrePromocion); 
	}
}
