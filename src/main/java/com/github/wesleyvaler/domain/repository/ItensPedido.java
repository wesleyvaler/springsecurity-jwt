package com.github.wesleyvaler.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.wesleyvaler.domain.entity.ItemPedido;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer>{

}
