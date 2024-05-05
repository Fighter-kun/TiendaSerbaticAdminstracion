package curso.java.tienda.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.model.ProductoVO;
import curso.java.tienda.model.UsuarioVO;
import curso.java.tienda.repository.UsuarioRepository;

@Controller
public class UsuarioAdminController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/clientsAdmin")
	public String findAll(Model model) {
		model.addAttribute("clients", usuarioRepository.findByRoles(1,2));
		return "client-listAdmin";
	}
	
	@GetMapping("/clientsAdmin/form")
	public String getEmptyForm(Model model) {
		model.addAttribute("client", new UsuarioVO());
		return "client-formAdmin";
	}

	@GetMapping("/clientsAdmin/edit/{id}")
	public String getFormWithProduct(Model model, @PathVariable Integer id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.findById(id).ifPresent(c -> model.addAttribute("client", c));
			return "client-formAdmin";
		} else {
			return "redirect:/clientsAdmin/form";
		}

	}
	
	@PostMapping("/clientsAdmin")
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
	    	usuario.setClave(encoder.encode(usuario.getClave()));
	        usuarioRepository.save(usuario);
	    }
	    return "redirect:/clientsAdmin";
	}
	
	@GetMapping("/clientsAdmin/fechaBaja/{id}")
    public String updateProductBaja(@PathVariable Integer id) {
        Optional<UsuarioVO> optionalProduct = usuarioRepository.findById(id);
        
        if (optionalProduct.isPresent()) {
            UsuarioVO usuario = optionalProduct.get();
            // Actualizar la fecha de baja a la fecha actual
            usuario.setFecha_baja(new Date());
            usuarioRepository.save(usuario);
        } 
        
        return "redirect:/clientsAdmin";
    }

	
}
