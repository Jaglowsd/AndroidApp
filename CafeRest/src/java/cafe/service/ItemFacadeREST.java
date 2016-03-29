package cafe.service;

import cafe.Item;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

@Stateless
@Path("item")
public class ItemFacadeREST extends AbstractFacade<Item> {
    @PersistenceContext(unitName = "CafeServicePU")
    private EntityManager em;

    public ItemFacadeREST() {
        super(Item.class);
    }

    @POST
    @Consumes({"application/json"})
    public String startOrder(Item entity) {
        super.create(entity);
        em.flush();
        return entity.getIditem().toString();
    }

    @GET
    @Produces({"application/json"})
    public List<Item> findAll(@QueryParam("ordernumber") int ordernumber) {
        return em.createNamedQuery("Item.findByOrdernumber", Item.class).setParameter("ordernumber", ordernumber).getResultList();
    }
    
    @DELETE
    @Path("{iditem}")
    public void remove(@PathParam("iditem") Integer id) {
        System.out.println("We are in the DELETE method in item class");
        System.out.println(id); 
        System.out.println(super.find(id)); 
        super.remove(super.find(id));
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
