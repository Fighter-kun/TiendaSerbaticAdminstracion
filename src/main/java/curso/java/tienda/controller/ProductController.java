package curso.java.tienda.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import curso.java.tienda.model.ProductoVO;
import curso.java.tienda.repository.ProductsRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductsRepository productRepository;

	@GetMapping("/products")
	public String findAll(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "product-list";
	}

	@GetMapping("/products/form")
	public String getEmptyForm(Model model) {
		model.addAttribute("product", new ProductoVO());
		return "product-form";
	}

	@GetMapping("/products/edit/{id}")
	public String getFormWithProduct(Model model, @PathVariable Integer id) {
		if (productRepository.existsById(id)) {
			productRepository.findById(id).ifPresent(p -> model.addAttribute("product", p));
			return "product-form";
		} else {
			return "redirect:/products/form";
		}

	}
	
	@PostMapping("/products")
	public String createProduct(@ModelAttribute ProductoVO producto, @RequestParam("imagen") MultipartFile imagen) {
	    if (producto.getId() != 0) {
	        // Actualización
	        productRepository.findById(producto.getId()).ifPresent(p -> {
	            p.setNombre(producto.getNombre());
	            p.setDescripcion(producto.getDescripcion());
	            p.setPrecio(producto.getPrecio()); 
	            p.setImpuesto(producto.getImpuesto()); 
	            p.setStock(producto.getStock());
	            p.setId_categoria(producto.getId_categoria()); 
	            try {
	                p.setImagen(imagen.getBytes()); // Guarda los bytes de la imagen en el campo 'imagen'
	            } catch (IOException e) {
	                // Maneja la excepción apropiadamente
	                e.printStackTrace();
	            }

	            productRepository.save(p);
	        });
	    } else {
	        producto.setFecha_alta(new Date());
	        try {
	            producto.setImagen(imagen.getBytes()); // Guarda los bytes de la imagen en el campo 'imagen'
	        } catch (IOException e) {
	            // Maneja la excepción apropiadamente
	            e.printStackTrace();
	        }
	        productRepository.save(producto);
	    }
	    return "redirect:/products";
	}


	
}
