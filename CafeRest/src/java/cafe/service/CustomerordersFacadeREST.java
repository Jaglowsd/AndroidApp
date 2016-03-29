package cafe.service;

import cafe.Customerorders;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author DAVID JAGLOWSKI
 */
@Stateless
@Path("customerorders")
public class CustomerordersFacadeREST extends AbstractFacade<Customerorders> {
    @PersistenceContext(unitName = "CafeServicePU")
    private EntityManager em;

    public CustomerordersFacadeREST() {
        super(Customerorders.class);
    }

    @GET
    @Produces({"application/json"})
    public List<Customerorders> findAll(@QueryParam("ordernumber") int ordernumber) {
        System.out.println("Did we make it?");
        System.out.println("I hope so!");
        return em.createNamedQuery("Customerorders.findByOrdernumber", Customerorders.class).setParameter("ordernumber", ordernumber).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
