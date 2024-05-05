package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.model.CategoriaVO;
import curso.java.tienda.repository.CategoriaRepository;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/categories")
	public String findAll(Model model) {
		model.addAttribute("categories", categoriaRepository.findAll());
		return "category-list";
	}

	@GetMapping("/categories/form")
	public String getEmptyForm(Model model) {
		model.addAttribute("category", new CategoriaVO());
		return "category-form";
	}

	@GetMapping("/categories/edit/{id}")
	public String getFormWithCategory(Model model, @PathVariable Integer id) {
		if (categoriaRepository.existsById(id)) {
			categoriaRepository.findById(id).ifPresent(c -> model.addAttribute("category", c));
			return "category-form";
		} else {
			return "redirect:/categories/form";
		}

	}

	@PostMapping("/categories")
	public String createCategory(@ModelAttribute CategoriaVO categoria) {
		if (categoria.getId() != 0) {
			// Actualización de un usuario existente
			categoriaRepository.findById(categoria.getId()).ifPresent(existingCategoria -> {
				existingCategoria.setNombre(categoria.getNombre());
				existingCategoria.setDescripcion(categoria.getDescripcion());

				categoriaRepository.save(existingCategoria);
			});
		} else {
			// Creación de un nuevo usuario
			categoriaRepository.save(categoria);
		}
		return "redirect:/categories";
	}

}
