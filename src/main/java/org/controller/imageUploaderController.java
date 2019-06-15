/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller;

import com.up.image.imageUpAPI;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
public class imageUploaderController {

    
  private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
        
     
        imageUpAPI IUA = new imageUpAPI();
        IUA.primeFacesImageReceiver(500, 500, file, "\\test\\", "", "test");
       
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void upload() throws IOException {
        if(file != null) {
            imageUpAPI IUA = new imageUpAPI();
            IUA.primeFacesImageReceiver(500, 500, file, "\\test\\", "", "test");

            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
     public void handleMultipleFileUpload(FileUploadEvent event) throws IOException {
         
        file = event.getFile();
        imageUpAPI IUA = new imageUpAPI();
        
        IUA.primeFacesMultiImageReceiver(500, 500, file, "\\test\\multiGet\\", "");         
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
      
}
