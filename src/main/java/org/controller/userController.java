package org.controller;

import com.facade.beans.UserFacade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.entities.EJBeans.UserBean;
import org.entities.model.User;
import org.primefaces.event.SelectEvent;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "ucontrole")
@ViewScoped
public class userController {
    
    private int id;
    private String userName;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
//-----------------------------------------    
    
  
    
    @EJB  
    UserFacade bean_b;
    
    @Init
    public void init(){
        bean_b = new UserFacade();
    }
    
    public void insert(){
            User u = new User();
            u.setId(id);
            u.setName(name);
            u.setUsername(userName);
        
            
        try {
             bean_b.create(u);
             message("Insertion effectuer.");
        } catch (Exception e) {
             message("Probleme d'insertion.");
        }
            
         
    }
    
    public void delete(){
            User u = new User();
            u.setId(id);
            u.setName(name);
            u.setUsername(userName);
        
        bean_b.remove(u);
    }
    
    private List<User> listUser;

    public List<User> getListUser() {
        listUser = bean_b.findAll();
        return listUser;
    }

  

    
    
    
     public void onRowSelect(SelectEvent event) {
        String i = ((User) event.getObject()).getId()+"";
        User u = bean_b.find(Integer.parseInt(i));
        
        
        id = u.getId();
        userName = u.getUsername();
        name = u.getName();
        
        message("Vous avez selectionnée: "+u.getName());
        
    }
    
    public void message(String str){
        FacesMessage msg2 = new FacesMessage(str);
        FacesContext.getCurrentInstance().addMessage(null, msg2);
    } 
     
     
}
