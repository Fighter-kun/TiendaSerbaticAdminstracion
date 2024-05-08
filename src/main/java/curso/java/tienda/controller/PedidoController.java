package curso.java.tienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import curso.java.tienda.model.CategoriaVO;
import curso.java.tienda.model.DetallePedidoVO;
import curso.java.tienda.model.PedidoVO;
import curso.java.tienda.repository.PedidoRepository;
import curso.java.tienda.repository.DetallePedidoRepository;

@Controller
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private DetallePedidoRepository detallePedidoRepository;


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

            // Recuperar todas las líneas de pedido para este pedido específico
            List<DetallePedidoVO> detallesPedido = detallePedidoRepository.findByIdPedido(id);

            // Calcular el total sumando el valor total de cada línea de pedido
            double totalPedido = detallesPedido.stream()
                    .mapToDouble(detallePedido -> detallePedido.getTotal())
                    .sum();

            // Asignar el total calculado al pedido existente
            existingPedido.setTotal(totalPedido);
            
            // Guardar los cambios en el pedido
            pedidoRepository.save(existingPedido);
        });
        return "redirect:/pedidos";
    }


}
