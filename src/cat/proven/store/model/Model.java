package cat.proven.store.model;

import cat.proven.store.model.persist.DbConnect;
import java.util.ArrayList;
import cat.proven.store.model.persist.ProductDao;
import java.util.List;

/**
 *
 * @author Chems
 */
public class Model {

    private ProductDao productDao;

    public Model() {
        productDao = new ProductDao();

    }

    /**
     * Move first row
     * @return product in first row
     */
    public Product moveFirst() {

        Product res = new Product();
        res = productDao.moveFirst();
        //return res;
        return res;
    }


    /**
     * Move previous row
     * @return product in this row
     */
    public Product movePrevious() {

        Product res = new Product();
        res = productDao.movePrevious();

        return res;
    }

    /**
     * Add row with new product
     * @param product to add in a db
     * @return true if is added
     *          false in case of error or otherwise
     */
    public boolean addProduct(Product product) {

        boolean res = false;

        res = productDao.addProduct(product);

        return res;
    }

    /**
     * Move next row
     * @return product in a row
     */
    public Product moveNext() {

        Product res = new Product();
        res = productDao.moveNext();

        return res; //Return new list
    }

    
    /**
     * Move last row with last product
     * @return product in a last position
     */
    public Product moveLast() {

        Product res = new Product();
        res = productDao.moveLast();
        return res;

    }

    /**
     * Update data from a product
     *
     * @param newProduct product with new data
     * @return true if success modified or false in case of error o otherwise
     *
     */
    public boolean modifyProduct(Product newProduct) {

        boolean res = true;
        res=productDao.modifyProduct(newProduct);
        return res;

    }

    /**
     * remove one product from products DB
     *
     * @return true if has removed or false in case of error
     */
    public boolean removeProduct() {

        boolean res = false;
        
        res=productDao.removeRow();

        return res;
    }
}
