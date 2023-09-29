package com.github.wesleyvaler.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "descricao")
	@NotEmpty(message = "{campo.descricao.obrigatorio }")
	private String descricao;
	
	@Column(name ="preco_unitario")
	@NotNull(message = "{campo.preco.obrigatorio}")
	private BigDecimal preco;

}
