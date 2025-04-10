package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("recurso não encontrado"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable){
        Page<Product> result = productRepository.searchByName(name, pageable); 
        return result.map(x -> new ProductMinDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        
        Product entity = copyDtoToEntity(dto);
        System.out.println("dto: " + dto);
        System.out.println("entity: " + entity);
        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }
    
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try{
            Product entity = productRepository.getReferenceById(id);
            entity = copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try{
            productRepository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("falha de integridade referencial");
        }
    }


    //CUSTOM METHODS
    /**
     * Receive: Product entity and a ProductDTO
     * Return: received entity instance with dto's values
    */
    private Product copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        
        // Clear existing categories before adding
        entity.getCategories().clear(); 
        
        dto.getCategories().forEach(cat -> {
            //IMPLEMENT DTO.toEntity() method in DTOs
            // entity.getCategories().add(cat.toEntity());
            entity.getCategories().add(
                new Category(
                    cat.getId(), 
                    cat.getName()
                )
            );
        });
        
        return entity;
    }

    /**
     * Receive: ProductDTO
     * Return: new Product instance with dto's values
    */
    private Product copyDtoToEntity(ProductDTO dto){

        Product entity = new Product();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        
        // Clear existing categories before adding
        entity.getCategories().clear(); 
        
        dto.getCategories().forEach(cat -> {
            //IMPLEMENT DTO.toEntity() method in DTOs
            // entity.getCategories().add(cat.toEntity());
            entity.getCategories().add(
                new Category(
                    cat.getId(), 
                    cat.getName()
                )
            );
        });

        return entity;
    }
}
