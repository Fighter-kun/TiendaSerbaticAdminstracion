package curso.java.tienda.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.PedidoVO;
import curso.java.tienda.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Map<String, Long> obtenerPedidosPorDia() {
        List<PedidoVO> pedidos = pedidoRepository.findAll();
        
        // Agrupar los pedidos por día y contar la cantidad de pedidos por día
        Map<String, Long> pedidosPorDia = pedidos.stream()
            .collect(Collectors.groupingBy(
                pedido -> obtenerDiaDesdeTimestamp(pedido.getFecha()), // Convertir la fecha a día
                Collectors.counting()
            ));
        
        return pedidosPorDia;
    }

    private String obtenerDiaDesdeTimestamp(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime(); // Convertir Timestamp a LocalDateTime
        LocalDate localDate = localDateTime.toLocalDate(); // Obtener LocalDate
        return localDate.toString(); // Devolver la representación de la fecha como String
    }
}
