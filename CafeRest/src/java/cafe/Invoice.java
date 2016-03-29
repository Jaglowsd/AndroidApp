/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joe Gregg
 */
@Entity
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByIdinvoice", query = "SELECT i FROM Invoice i WHERE i.idinvoice = :idinvoice"),
    @NamedQuery(name = "Invoice.findByCustomer", query = "SELECT i FROM Invoice i WHERE i.customer = :customer"),
    @NamedQuery(name = "Invoice.findByPhone", query = "SELECT i FROM Invoice i WHERE i.phone = :phone")})
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinvoice")
    private Integer idinvoice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "customer")
    private String customer;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "phone")
    private String phone;

    public Invoice() {
    }

    public Invoice(Integer idinvoice) {
        this.idinvoice = idinvoice;
    }

    public Invoice(Integer idinvoice, String customer, String phone) {
        this.idinvoice = idinvoice;
        this.customer = customer;
        this.phone = phone;
    }

    public Integer getIdinvoice() {
        return idinvoice;
    }

    public void setIdinvoice(Integer idinvoice) {
        this.idinvoice = idinvoice;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvoice != null ? idinvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.idinvoice == null && other.idinvoice != null) || (this.idinvoice != null && !this.idinvoice.equals(other.idinvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cafe.Invoice[ idinvoice=" + idinvoice + " ]";
    }
    
}
