package com.github.wesleyvaler.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.wesleyvaler.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto,Integer> {
}