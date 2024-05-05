package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.model.CategoriaVO;
import curso.java.tienda.model.PedidoVO;
import curso.java.tienda.repository.PedidoRepository;

@Controller
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping("/pedidos")
	public String findAll(Model model) {
		model.addAttribute("pedidos", pedidoRepository.findByEstado("PE"));
		return "pedido-list";
	}


	// Método para cambiar el estado del pedido a "E"
    @GetMapping("/pedidos/cambiarEstado/{id}")
    public String cambiarEstadoPedido(@PathVariable Integer id) {
        pedidoRepository.findById(id).ifPresent(existingPedido -> {
            existingPedido.setEstado("E");
            pedidoRepository.save(existingPedido);
        });
        return "redirect:/pedidos";
    }

}
