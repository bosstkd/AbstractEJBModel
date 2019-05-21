/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entities.EJBeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import org.entities.model.User;

/**
 *
 * @author Amine
 */
@Stateless
public class UserBean {

    @PersistenceContext(unitName = "testAbstract")
    private EntityManager em;

    public List<User> trouve(){
        String req = "SELECT u FROM User u";
        Query q = em.createQuery(req, User.class);
        
        return q.getResultList();
    }
    
    
    public User findById(int id) {
        return (User) em.find(User.class, id);
    }
    
}
