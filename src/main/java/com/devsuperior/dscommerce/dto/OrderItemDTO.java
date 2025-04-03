package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;

public class OrderItemDTO {
    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    
    public OrderItemDTO(OrderItem entity) {
        productId = entity.getProduct().getId();
        name = entity.getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        imageUrl = entity.getProduct().getImgUrl();
    }

    public Long getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public Double getSubTotal() {
        return quantity * price;
    }
}
