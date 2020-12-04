/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.store.model.persist;

import java.sql.ResultSet;

import cat.proven.store.model.Product;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chems
 */
public class ProductDao {

    private ResultSet resultSet;

    public ProductDao() {
        init();
    }

    //Connect database
    private void init() {

        try {
            Connection connection;
            DbConnect.loadDriver();
            connection = DbConnect.getConnection();
            if (connection != null) {
                Statement stmt = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String sql = "SELECT * FROM products";
                resultSet = stmt.executeQuery(sql);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Move first row
     *
     * @return product in first row null in case of error or otherwise
     */
    public Product moveFirst() {

        Product product = new Product();
        try {
            resultSet.first();
            product = resultSetToProduct(resultSet);
        } catch (SQLException ex) {
            product = null;
        }
        return product;

    }

    /**
     * Move previous row
     *
     * @return product in this row null in case of error or otherwise
     */
    public Product movePrevious() {
        Product product = new Product();
        try {
            resultSet.previous();
            product = resultSetToProduct(resultSet);
        } catch (SQLException ex) {
            moveFirst();
            product = null;
        }
        return product;

    }

    /**
     * Move next row
     *
     * @return product in a row null in case of error or otherwise
     */
    public Product moveNext() {

        Product product = new Product();
        try {
            resultSet.next();
            product = resultSetToProduct(resultSet);
        } catch (SQLException ex) {
            moveLast();
            product = null;
        }
        return product;

    }

    /**
     * COnverts data from current row of resulset into a product
     *
     * @param rs the result to extract data from
     * @return product with extracted data
     */
    private Product resultSetToProduct(ResultSet rs) throws SQLException {

        String code = rs.getString("code");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock");
        Product p = new Product(code, description, price, stock);
        return p;

    }

    /**
     * Move last row with last product
     *
     * @return product in a last position null in case of error or otherwise
     */
    public Product moveLast() {

        Product product = new Product();

        try {
            resultSet.last();
            product = resultSetToProduct(resultSet);
        } catch (SQLException ex) {
            product = null;
        }
        return product;

    }

    /**
     * Add row with new product
     *
     * @param product to add in a db
     * @return true if is added false in case of error or otherwise
     */
    public boolean addProduct(Product product) {

        boolean res = false;
        try {

            resultSet.moveToInsertRow();
            resultSet.updateString("code", product.getCode());
            resultSet.updateString("description", product.getDescritpion());
            resultSet.updateDouble("price", product.getPrice());
            resultSet.updateInt("stock", product.getStock());
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            res = true;

            return res;
        } catch (SQLException ex) {
            res = false;
        }
        return res;

    }

    /**
     * Modify row with product
     *
     * @param product with new datas
     * @return true in case of updated false in case of error or otherwise
     */
    public boolean modifyProduct(Product product) {

        boolean res = false;
        try {

            resultSet.updateString("code", product.getCode());
            resultSet.updateString("description", product.getDescritpion());
            resultSet.updateDouble("price", product.getPrice());
            resultSet.updateInt("stock", product.getStock());
            resultSet.updateRow();

            res = true;

            return res;
        } catch (SQLException ex) {
            res = false;
        }
        return res;

    }

    /**
     * Remove row with product to delete
     *
     * @return true if is deleted false in case of error or otherwise
     */
    public boolean removeRow() {
        boolean res = false;
        try {
            resultSet.deleteRow();
            res = true;
        } catch (SQLException ex) {
            res = false;
        }
        return res;

    }

}
