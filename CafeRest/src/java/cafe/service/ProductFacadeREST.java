/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe.service;

import cafe.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Joe Gregg
 */
@Stateless
@Path("product")
public class ProductFacadeREST extends AbstractFacade<Product> {
    @PersistenceContext(unitName = "CafeServicePU")
    private EntityManager em;

    public ProductFacadeREST() {
        super(Product.class);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Product> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
