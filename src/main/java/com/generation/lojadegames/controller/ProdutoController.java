package com.generation.lojadegames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.lojadegames.model.Produto;
import com.generation.lojadegames.repository.CategoriaRepository;
import com.generation.lojadegames.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado"));
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		return categoriaRepository.findById(produto.getCategoria().getId())
				.map(existingCategory -> {
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(produtoRepository.save(produto));
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!"));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
	    return produtoRepository.findById(produto.getId())
	            .map(existingProduct -> {
	                if (categoriaRepository.existsById(produto.getCategoria().getId()))
	                    return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
	    
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!");
	                
	            })
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não existe!"));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		produtoRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado"));

	    produtoRepository.deleteById(id);
	}
	
	@GetMapping("/menorque/{preco}")
	public List<Produto> listarProdutosMenoresQue(@PathVariable double preco) {
		return produtoRepository.findByPrecoLessThan(preco);
	}
	
	@GetMapping("/maiorque/{preco}")
	public List<Produto> listarProdutosMaioresQue(@PathVariable double preco) {
		return produtoRepository.findByPrecoGreaterThan(preco);
	}
}
