package com.barcito.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoRequest {
	@NotNull private int mesaId;
    @NotEmpty private List<@Valid ProductoPedidoRequest> items;
    
    public PedidoRequest() {
    	
    }

	public PedidoRequest(@NotNull int mesaId, @NotEmpty List<@Valid ProductoPedidoRequest> items) {
		super();
		this.mesaId = mesaId;
		this.items = items;
	}

	public int getMesaId() {
		return mesaId;
	}

	public void setMesaId(int mesaId) {
		this.mesaId = mesaId;
	}

	public List<ProductoPedidoRequest> getItems() {
		return items;
	}

	public void setItems(List<ProductoPedidoRequest> items) {
		this.items = items;
	}
    
    
}
