package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;

    @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
    private String description;

    @Positive(message = "Preço deve ser positivo")
    private double price;

    private String imgUrl;
    
    public ProductDTO(Long id, String name, double price, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        description = entity.getDescription();
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
    public String getDescription(){
        return description;
    }
    public String getImgUrl(){
        return imgUrl;
    }
}
