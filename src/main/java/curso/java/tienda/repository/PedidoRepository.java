package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.PedidoVO;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoVO, Integer> {
	
	 List<PedidoVO> findByEstado(String estado);
}
