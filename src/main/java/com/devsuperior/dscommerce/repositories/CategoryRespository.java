package com.devsuperior.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscommerce.entities.Category;

public interface CategoryRespository extends JpaRepository<Category, Long> {

}
