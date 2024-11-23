package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
        method: GET
        url: products/id
        req body:
        res: ProductDTO 
    */
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }

    /**
        method: GET
        url: products/id
        req body:
        res: ProductDTO 
    */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable); 
        return result.map(x -> new ProductDTO(x));
    }

    /**
        method: POST
        url: products
        req body: ProductDTO
        res: ProductDTO 
    */
    @Transactional
    public ProductDTO insert(ProductDTO dto){
        
        Product entity = copyDtoToEntity(dto);
        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }
    
    /**
        method: PUT
        url: products/id
        req body: ProductDTO
        res: ProductDTO 
    */
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){

        Product entity = productRepository.getReferenceById(id);
        entity = copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }

    /**
        method: DELETE
        url: products/id
        req body: 
        res: ProductDTO 
    */
    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
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

        return entity;
    }
}
