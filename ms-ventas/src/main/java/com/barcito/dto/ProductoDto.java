package com.barcito.dto;

import com.barcito.enums.TipoProducto;

public class ProductoDto {
	private int id;
	private String nombre;
	private int precio; 
	private TipoProducto tipo;
	
	public ProductoDto(){
		
	}

	public ProductoDto(int id, String nombre, int precio, TipoProducto tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}
	
}
