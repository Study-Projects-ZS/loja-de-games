package com.generation.lojadegames.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "td_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atríbuto nome é obrigátorio")
	private String nome;
	
	private String plataforma;
	
	private double preco;
	
	@Column(length=1000)
	@Size(max=1000, message = "O atríbuto foto pode ter no máximo 1000 caracteres.")
	private String foto;
	
}
