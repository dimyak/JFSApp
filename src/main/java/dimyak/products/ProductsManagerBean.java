package dimyak.products;

import dimyak.products.domain.ProductEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@LocalBean
@Stateless
public class ProductsManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    //CRUD create-read-update-delete

    public boolean create(Product product){
        if(!checkValid(product)){
            return false;
        }

        //Если уже есть в базе
        ProductEntity existingProduct = entityManager.find(ProductEntity.class, product.getId());
        if(existingProduct!=null){
            return false;
        }

        existingProduct = new ProductEntity();
        existingProduct.fromDto(product);
        entityManager.persist(existingProduct);
        return true;

    }

    public Product read(long id){
        ProductEntity entity = entityManager.find(ProductEntity.class, id);
        if(entity==null){
            return null;
        }
        return entity.toDto();
    }

    public boolean update(Product product){
        if(!checkValid(product)){
            return false;
        }

        ProductEntity existingProduct = entityManager.find(ProductEntity.class, product.getId());
        if(existingProduct==null){
            return false;
        }
        existingProduct.fromDto(product);
        entityManager.merge(existingProduct);
        return true;
    }

    public boolean delete(long id){
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if(productEntity==null){
            return false;
        }
        entityManager.remove(productEntity);
        return true;
    }



    //offset - смещение от начала limit кол елементов
    //примет страница 1 : offset=0 limit=20
    //примет страница 2 : offset=20 limit=20
    //примет страница 3 : offset=40 limit=20
    public List<Product> readList(int offset, int limit){
        if(offset<0||limit<1){
            return Collections.emptyList();
        }
        TypedQuery<ProductEntity> query =
                entityManager.createQuery(
                        "select entity from ProductEntity entity ", ProductEntity.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<ProductEntity> entityList = query.getResultList();
        if(entityList==null||entityList.isEmpty()){
            return Collections.emptyList();
        }

        List<Product> productsList = new ArrayList<Product>(entityList.size());
        for(ProductEntity entity : entityList){
            productsList.add(entity.toDto());
        }

        return productsList;
    }

    private boolean checkValid(Product product){

        return product != null &&
                !StringUtils.isEmpty(product.getName()) &&
                product.getPrice() > 0;
    }
}
