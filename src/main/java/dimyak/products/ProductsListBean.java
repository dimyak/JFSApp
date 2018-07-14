package dimyak.products;

import dimyak.products.domain.ProductEntity;


import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProductsListBean implements Serializable {
    @EJB
    private ProductsManagerBean productsManagerBean;
    private Product newProduct= new Product();


    public Product getNewProduct(){
        return newProduct;
    }

    public List<Product> getProducts(){
        List<Product> result = new ArrayList<Product>();
        List<ProductEntity> entities = productsManagerBean.readList(0,100);
        for(ProductEntity entity : entities){
            result.add(entity.toDto());
        }
        return result;
    }

    public void createNewProduct(){
        ProductEntity entity= new ProductEntity();
        entity.fromDto(newProduct);
        productsManagerBean.create(entity);
        newProduct = new Product();
    }

    public void deleteProduct(long id){
        productsManagerBean.delete(id);
    }
}
