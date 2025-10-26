package com.barcito.dto;

import com.barcito.entity.Mesa;

import jakarta.validation.constraints.NotNull;

public class MesaDto {
	private int id;
    @NotNull(message = "El número de mesa no puede ser nulo")
    private int numero;
    private String estado;

    public MesaDto() {
    	
    }
    
    public MesaDto(int id, int numero, String estado) {
        this.id = id; this.numero = numero; this.estado = estado;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Mesa toEntity() {
        return new Mesa(0, getNumero(), null); 
    }
}
