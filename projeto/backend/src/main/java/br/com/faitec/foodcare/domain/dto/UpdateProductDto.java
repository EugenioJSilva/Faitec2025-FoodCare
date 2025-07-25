package br.com.faitec.foodcare.domain.dto;

import br.com.faitec.foodcare.domain.Product;
import lombok.Data;

@Data
public class UpdateProductDto {
    private int id;
    private String name;
    private String productType;
    private int stock;
    private boolean isActive;
    private int basketQuantity;
    private int categoryId;

    public Product toProduct(){
        final Product entity = new Product();
        entity.setId(id);
        entity.setName(name);
        entity.setProductType(productType);
        entity.setStock(stock);
        entity.setActive(isActive);
        entity.setBasketQuantity(basketQuantity);
        entity.setCategoryId(categoryId);

        return entity;
    }


}
