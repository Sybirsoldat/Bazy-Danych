package com.example.library.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Orders_books")
public class OrdersBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;

    private Boolean issued;

    // Gettery i Settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Orders getOrder() { return order; }
    public void setOrder(Orders order) { this.order = order; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public Boolean getIssued() { return issued; }
    public void setIssued(Boolean issued) { this.issued = issued; }
}
