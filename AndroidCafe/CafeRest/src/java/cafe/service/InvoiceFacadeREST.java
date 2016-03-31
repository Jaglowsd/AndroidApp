/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe.service;

import cafe.Invoice;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
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
@Path("invoice")
public class InvoiceFacadeREST extends AbstractFacade<Invoice> {
    @PersistenceContext(unitName = "CafeServicePU")
    private EntityManager em;

    public InvoiceFacadeREST() {
        super(Invoice.class);
    }

    @POST
    @Consumes({"application/json"})
    public String createOrder(Invoice entity) {
        super.create(entity);
        em.flush();
        String id = entity.getIdinvoice().toString();
        return id;
    }

    @PUT
    @Consumes({"application/json"})
    public String editCustomer(Invoice entity) {
        super.edit(entity);
        em.flush();
        String idinvoice  = entity.getIdinvoice().toString();
        return idinvoice;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}


