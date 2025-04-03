package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductMinDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Nome deve ser informado")
    private String name;

    @NotNull(message = "Preço deve ser informado")
    @Positive(message = "Preço deve ser positivo")
    private double price;

    private String imgUrl;
    
    public ProductMinDTO(Long id, String name, double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getImgUrl(){
        return imgUrl;
    }
}
