package com.generation.lojadegames.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atríbuto nome é obrigátorio")
	private String nome;
	
	private String plataforma;

	@DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
	@Digits(integer = 5, fraction = 2)
	private BigDecimal preco;
	
	@Column(length=1000)
	@Size(max=1000, message = "O atríbuto foto pode ter no máximo 1000 caracteres.")
	private String foto;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;
}
