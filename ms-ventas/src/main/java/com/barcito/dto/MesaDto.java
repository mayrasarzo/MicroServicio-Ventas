package com.barcito.dto;

import com.barcito.entity.Mesa;
import com.barcito.enums.EstadoMesa;

import jakarta.validation.constraints.NotNull;

public class MesaDto {
	private int id;
    @NotNull(message = "El número de mesa no puede ser nulo")
    private int numero;
    @NotNull(message = "El estado no puede ser nulo")
    private EstadoMesa estado;

    public MesaDto() {
    	
    }
    
	public MesaDto(int id, @NotNull(message = "El número de mesa no puede ser nulo") int numero,
			@NotNull(message = "El estado no puede ser nulo") EstadoMesa estado) {
		super();
		this.id = id;
		this.numero = numero;
		this.estado = estado;
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

	public EstadoMesa getEstado() {
		return estado;
	}

	public void setEstado(EstadoMesa estado) {
		this.estado = estado;
	}

	public Mesa toEntity() {
        return new Mesa(0, getNumero(), null); 
    }
}
