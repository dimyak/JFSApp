package dimyak.products;

import dimyak.products.domain.ProductEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@LocalBean
@Stateless
public class ProductsManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    //CRUD create-read-update-delete

    public boolean create(ProductEntity productEntity){
        if(!checkValid(productEntity)){
            return false;
        }

        //Если уже есть в базе
        ProductEntity existingProduct = entityManager.find(ProductEntity.class, productEntity.getId());
        if(existingProduct!=null){
            return false;
        }

        entityManager.persist(productEntity);
        return true;

    }

    public ProductEntity read(long id){
        return entityManager.find(ProductEntity.class, id);
    }

    public boolean update(ProductEntity productEntity){
        if(!checkValid(productEntity)){
            return false;
        }

        ProductEntity existingProduct = entityManager.find(ProductEntity.class, productEntity.getId());
        if(existingProduct==null){
            return false;
        }

        entityManager.merge(productEntity);
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
    public List<ProductEntity> readList(int offset, int limit){
        if(offset<0||limit<1){
            return Collections.emptyList();
        }
        TypedQuery<ProductEntity> query =
                entityManager.createQuery(
                        "select entity from ProductEntity entity ", ProductEntity.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    private boolean checkValid(ProductEntity productEntity){
        return productEntity != null &&
                !StringUtils.isEmpty(productEntity.getName()) &&
                productEntity.getPrice() > 0;
    }
}
