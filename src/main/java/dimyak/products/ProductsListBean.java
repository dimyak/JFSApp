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
    private long idForDelete;
    private Product editingProduct;

    public long getIdForDelete() {
        return idForDelete;
    }

    public void setIdForDelete(long idForDelete) {
        this.idForDelete = idForDelete;
    }

    public Product getEditingProduct() {
        return editingProduct;
    }

    public void setEditingProduct(Product editingProduct) {
        this.editingProduct = editingProduct;
    }

    public Product getNewProduct(){
        return newProduct;
    }

    public List<Product> getProducts(){
        List<Product> result = productsManagerBean.readList(0,100);
        return result;
    }

    public void createNewProduct(){
        productsManagerBean.create(newProduct);
        newProduct = new Product();
    }

    public void deleteProduct(){
        productsManagerBean.delete(idForDelete);
    }

    public void saveProduct(){
        productsManagerBean.update(editingProduct);
    }
}
