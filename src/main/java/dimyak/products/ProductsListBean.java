package dimyak.products;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProductsListBean implements Serializable {
    private List<Product> products = new ArrayList<Product>();
    private Product newProduct= new Product();

    @PostConstruct
    private void initialize(){
        Product product;

        product= new Product();
        product.setId("id");
        product.setName("Food");
        product.setPrice(100);
        products.add(product);


        product= new Product();
        product.setId("id");
        product.setName("Computer");
        product.setPrice(10000);
        products.add(product);


        product= new Product();
        product.setId("id");
        product.setName("Telephone");
        product.setPrice(5000);
        products.add(product);
    }

    public Product getNewProduct(){
        return newProduct;
    }

    public List<Product> getProducts(){
        return products;
    }

    public void createNewProduct(){
        products.add(newProduct);
        newProduct = new Product();
    }
}
