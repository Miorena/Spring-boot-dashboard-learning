package com.miorena.dashboard.product;

import java.util.List;
	
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductConotroller {

	private final ProductRepository repository;

	public ProductConotroller(ProductRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Product> findAll() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Product findById(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		product.setId(null);
		return repository.save(product);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody Product product) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable");
		}
		product.setId(id);
		return repository.save(product);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable");
		}
		repository.deleteById(id);
	}
}
