package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.CategoriaVO;
import curso.java.tienda.model.ProductoVO;
import curso.java.tienda.model.UsuarioVO;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaVO, Integer>{
}
