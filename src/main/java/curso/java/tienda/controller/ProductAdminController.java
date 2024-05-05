package curso.java.tienda.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import curso.java.tienda.model.ProductoVO;
import curso.java.tienda.repository.ProductsRepository;

@Controller
public class ProductAdminController {

	@Autowired
	private ProductsRepository productRepository;

	@GetMapping("/productsAdmin")
	public String findAll(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "product-listAdmin";
	}

	@GetMapping("/productsAdmin/form")
	public String getEmptyForm(Model model) {
		model.addAttribute("product", new ProductoVO());
		return "product-formAdmin";
	}

	@GetMapping("/productsAdmin/edit/{id}")
	public String getFormWithProduct(Model model, @PathVariable Integer id) {
		if (productRepository.existsById(id)) {
			productRepository.findById(id).ifPresent(p -> model.addAttribute("product", p));
			return "product-formAdmin";
		} else {
			return "redirect:/productsAdmin/form";
		}

	}
	
	@PostMapping("/productsAdmin")
	public String createProduct(@ModelAttribute ProductoVO producto) {
	    if (producto.getId() != 0) {
	        // Actualización
	        productRepository.findById(producto.getId()).ifPresent(p -> {
	            p.setNombre(producto.getNombre());
	            p.setDescripcion(producto.getDescripcion());
	            p.setPrecio(producto.getPrecio()); 
	            p.setImpuesto(producto.getImpuesto()); 
	            p.setStock(producto.getStock());
	            p.setId_categoria(producto.getId_categoria()); 
	            p.setImagen(producto.getImagen()); 

	            productRepository.save(p);
	        });
	    } else {
	        
	        producto.setFecha_alta(new Date());
	        productRepository.save(producto);
	    }
	    return "redirect:/productsAdmin";
	}
	
	@GetMapping("/productsAdmin/fechaBaja/{id}")
    public String updateProductBaja(@PathVariable Integer id) {
        Optional<ProductoVO> optionalProduct = productRepository.findById(id);
        
        if (optionalProduct.isPresent()) {
            ProductoVO product = optionalProduct.get();
            // Actualizar la fecha de baja a la fecha actual
            product.setFecha_baja(new Date());
            productRepository.save(product);
        } 
        
        return "redirect:/productsAdmin";
    }


	
}
