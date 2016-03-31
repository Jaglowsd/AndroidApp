package cafe;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DAVID JAGLOWSKI
 */
@Entity
@Table(name = "customerorders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customerorders.findAll", query = "SELECT c FROM Customerorders c"),
    @NamedQuery(name = "Customerorders.findByOrdernumber", query = "SELECT c FROM Customerorders c WHERE c.ordernumber = :ordernumber"),
    @NamedQuery(name = "Customerorders.findByName", query = "SELECT c FROM Customerorders c WHERE c.name = :name")})
public class Customerorders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "iditem")
    private int id;
    @Column(name = "ordernumber")
    private int ordernumber;
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;

    public Customerorders() {
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
