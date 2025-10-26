package com.barcito.entity;

import java.util.ArrayList;
import java.util.List;

import com.barcito.dto.PedidoDto;
import com.barcito.dto.ProductoPedidoDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name = "pedidos")
public class Pedido {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
    private int mesaId; 
    private String estado; 
    private int total = 0;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id") 
    private List<ProductoPedido> items = new ArrayList<>();
    
    public Pedido() {
    	
    }
    
    public Pedido(int id, int mesaId, String estado, int total, List<ProductoPedido> items) {
        this.id = id;
        this.mesaId = mesaId;
        this.estado = estado;
        this.total = total;
        this.items = items != null ? items : new ArrayList<>(); // Aseguramos que no sea null
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMesaId() {
		return mesaId;
	}

	public void setMesaId(int mesaId) {
		this.mesaId = mesaId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ProductoPedido> getItems() {
		return items;
	}

	public void setItems(List<ProductoPedido> items) {
		this.items = items;
	}

	public PedidoDto toDto() {
        List<ProductoPedidoDto> itemsDto = new ArrayList<>();
        if (this.items != null) {
            for (ProductoPedido itemEntity : this.items) {
                itemsDto.add(itemEntity.toDto());
            }
        }
        return new PedidoDto(getId(), getMesaId(), getEstado(), getTotal(), itemsDto);
    }
}
