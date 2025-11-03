package com.barcito.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barcito.entity.Mesa;

@Repository
public interface MesaRepository extends CrudRepository<Mesa, Integer>{

}
