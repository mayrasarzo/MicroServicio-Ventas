package com.barcito.dto;

import java.util.ArrayList;
import java.util.List;

import com.barcito.entity.Pedido;
import com.barcito.entity.ProductoPedido;

public class PedidoDto {
	private int id; 
    private int mesaId; 
    private String estado; 
    private int total;
    private List<ProductoPedidoDto> items; 

    public PedidoDto() {
        this.items = new ArrayList<>(); 
    }

    public PedidoDto(int id, int mesaId, String estado, int total, List<ProductoPedidoDto> items) {
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

	public List<ProductoPedidoDto> getItems() {
		return items;
	}

	public void setItems(List<ProductoPedidoDto> items) {
		this.items = items;
	}

	public Pedido toEntity() {
        List<ProductoPedido> itemsEntity = new ArrayList<>();

        if (this.items != null) {
            for (ProductoPedidoDto itemDto : this.items) {
                itemsEntity.add(itemDto.toEntity());
            }
        }

        return new Pedido(getId(), getMesaId(), getEstado(), getTotal(), itemsEntity);
    }
}
