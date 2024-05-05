package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.model.UsuarioVO;
import curso.java.tienda.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/clients")
	public String findAll(Model model) {
		model.addAttribute("clients", usuarioRepository.findById_Rol(1));
		return "client-list";
	}
	
	@GetMapping("/clients/form")
	public String getEmptyForm(Model model) {
		model.addAttribute("client", new UsuarioVO());
		return "client-form";
	}

	@GetMapping("/clients/edit/{id}")
	public String getFormWithProduct(Model model, @PathVariable Integer id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.findById(id).ifPresent(c -> model.addAttribute("client", c));
			return "client-form";
		} else {
			return "redirect:/clients/form";
		}

	}
	
	@PostMapping("/clients")
	public String createUser(@ModelAttribute UsuarioVO usuario) {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    if (usuario.getId() != 0) {
	        usuarioRepository.findById(usuario.getId()).ifPresent(existingUser -> {
	            existingUser.setNombre(usuario.getNombre());
	            existingUser.setApellido1(usuario.getApellido1());
	            existingUser.setApellido2(usuario.getApellido2());
	            existingUser.setEmail(usuario.getEmail());
	            existingUser.setId_rol(usuario.getId_rol());
	            existingUser.setDireccion(usuario.getDireccion());
	            existingUser.setLocalidad(usuario.getLocalidad());
	            existingUser.setProvincia(usuario.getProvincia());
	            existingUser.setTelefono(usuario.getTelefono());
	            existingUser.setDni(usuario.getDni());

	            usuarioRepository.save(existingUser);
	        });
	    } else {
	    	usuario.setId_rol(1);
	    	usuario.setClave(encoder.encode(usuario.getClave()));
	        usuarioRepository.save(usuario);
	    }
	    return "redirect:/clients";
	}

	
}
