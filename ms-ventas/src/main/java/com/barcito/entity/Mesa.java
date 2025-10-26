package com.barcito.entity;

import com.barcito.dto.MesaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "mesas")
public class Mesa {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
    
	@Column(unique = true) 
    private int numero;
	
	@Column
	private String estado = "libre";
	
	public Mesa() {
		
	}

	public Mesa(int id, int numero, String estado) {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public MesaDto toDto() { 
		return new MesaDto(getId(), getNumero(), getEstado()); 
	}
}
