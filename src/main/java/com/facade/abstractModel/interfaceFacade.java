/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade.abstractModel;

import java.util.List;

/**
 *
 * @author Amine
 */
public interface interfaceFacade<T> {
   public boolean create(T entity);
   public void edit(T entity);
   public T find(Object id);
   public List<T> findAll();
   public boolean remove(T entity);
}
