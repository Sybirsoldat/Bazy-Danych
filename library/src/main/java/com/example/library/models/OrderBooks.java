import jakarta.persistence.*;

@Entity
@Table(name = "Orders_books")
public class OrdersBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    private Integer quantity;

    private Double totalPrice;

    private Boolean issued = false;

    // Getters i Setters
}