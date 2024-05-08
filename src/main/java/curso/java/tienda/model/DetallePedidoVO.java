package curso.java.tienda.model;


import lombok.Data;


import jakarta.persistence.*;

@Data
@Entity
@Table(name = "detalles_pedido")
public class DetallePedidoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_pedido")
    private int idPedido;

    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "precio_unidad")
    private double precioUnidad;

    private int unidades;

    private float impuesto;

    private double total;
    
    // Constructor vacío
    public DetallePedidoVO() {
    }

    // Constructor con todos los campos
    public DetallePedidoVO(int idPedido, int idProducto, double precioUnidad, int unidades, float impuesto, double total) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.precioUnidad = precioUnidad;
        this.unidades = unidades;
        this.impuesto = impuesto;
        this.total = total;
    }
}

