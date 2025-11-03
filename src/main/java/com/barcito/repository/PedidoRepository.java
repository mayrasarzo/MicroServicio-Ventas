package com.barcito.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barcito.entity.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer>{

}
