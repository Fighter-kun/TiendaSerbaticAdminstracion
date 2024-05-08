package curso.java.tienda.repository;

import curso.java.tienda.model.DetallePedidoVO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoVO, Integer> {
	List<DetallePedidoVO> findByIdPedido(Integer idPedido);

}
