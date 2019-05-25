/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.up.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Amine
 */
public class imageUpAPI {
    
        public final  String IMAGE_TYPE_JPEG = "jpg";
	public final  String IMAGE_TYPE_GIF = "gif";
	public final  String IMAGE_TYPE_PNG = "png";    
        
       
public List<String> getDirectoryImagesListName(String relativeWebPath){
       File folder = new File(relativeWebPath);
        File[] listOfFiles = folder.listFiles();
        List<String> lstImageName = new ArrayList<String>();
        String fName;
        String format;
         for (int i = 0; i < listOfFiles.length; i++) {
              if (listOfFiles[i].isFile()) {
                   fName = listOfFiles[i].getName();
                  if(fName.length()>4){
                       
                      format = fName.substring(fName.length()-3, fName.length());
                      if(format.equals(IMAGE_TYPE_GIF) || format.equals(IMAGE_TYPE_JPEG) || format.equals(IMAGE_TYPE_PNG))
                         lstImageName.add(fName);
                  }
                 }
            }
        return lstImageName;
  }

public String getRandomImageChooser(String relativeWebPath){

     List<String> lstFiles = getDirectoryImagesListName(relativeWebPath);
     Random n = new Random();
     int rand = n.nextInt(lstFiles.size()) + 1;

  return lstFiles.get(rand);                             
} 

public boolean createDirectory (String relativeWebPath){
    File files = new File(relativeWebPath);
        if (!files.exists()) {
            if (files.mkdirs()) {
                return true;
            } 
        }
    return false;
}
//----------------- 	
public boolean imageResizer(int XDimention, int YDimention, String outFormat, File fs) {
                Dimension dms = new Dimension(XDimention,YDimention);
                BufferedImage buf;
		try {
                    String pictureName = fs.getAbsolutePath().substring(0,fs.getAbsolutePath().length()-4)+"_RS."+outFormat;
                    buf = ImageIO.read(new File(fs.getAbsolutePath()));
		    BufferedImage bufFinal  = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);
                    // Redimensionnement de l'image originale
		    Graphics2D g = (Graphics2D) bufFinal.getGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(buf, 0, 0, dms.width,dms.height, null);
		    g.dispose();
                    
                ImageIO.write(bufFinal, IMAGE_TYPE_PNG, new File(pictureName));
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
	}
}

public boolean imageResizer(int XDimention, int YDimention, String outFormat, File fs, String imageName) {
                Dimension dms = new Dimension(XDimention,YDimention);
                BufferedImage buf;
		try {
                    String ancImageName = fs.getName();
                    System.out.println("ANC : "+ancImageName);
                    String pictureName = fs.getAbsolutePath().substring(0,fs.getAbsolutePath().length()- ancImageName.length());
                    
                    
                    pictureName = pictureName+"\\"+imageName+"."+outFormat;
                    buf = ImageIO.read(new File(fs.getAbsolutePath()));
		    BufferedImage bufFinal  = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);
		    Graphics2D g = (Graphics2D) bufFinal.getGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(buf, 0, 0, dms.width,dms.height, null);
		    g.dispose();
                    
                ImageIO.write(bufFinal, IMAGE_TYPE_PNG, new File(pictureName));
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
	}
}

public boolean imageResizer(int XDimention, int YDimention, String outFormat, String inPutImagePath, String outPutDirectory, String imageName){
      Dimension dms = new Dimension(XDimention,YDimention);
                BufferedImage buf;
		try {
                    createDirectory(outPutDirectory);
                    String pictureName = outPutDirectory+"\\"+imageName+"."+outFormat;
                    File fs = new File(inPutImagePath);
                    buf = ImageIO.read(new File(fs.getAbsolutePath()));
		    BufferedImage bufFinal  = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);
		    Graphics2D g = (Graphics2D) bufFinal.getGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(buf, 0, 0, dms.width,dms.height, null);
		    g.dispose();
                ImageIO.write(bufFinal, IMAGE_TYPE_PNG, new File(pictureName));
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
	}
}
//-----------------
public void primeFacesPhotoReceiver(int XDimention, int YDimention, UploadedFile file, String imageDirectory, String resourcePath, String imageName) throws IOException{
        
            InputStream InPtStream = file.getInputstream(); 
           
            
           String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(resourcePath);
         
           System.out.println("1: "+relativeWebPath);
           relativeWebPath = relativeWebPath+"\\"+imageDirectory;
           createDirectory(relativeWebPath);
           System.out.println("2: "+relativeWebPath);

            OutputStream OtPtStream = new FileOutputStream(new File(relativeWebPath+"\\"+imageName+".jpg"));
            
            String imageURL = relativeWebPath+"\\"+imageName+".jpg";
            
            int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = InPtStream.read(bytes)) != -1) {
			OtPtStream.write(bytes, 0, read);
		}
           
     
		if (InPtStream != null) {
			try {
				InPtStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (OtPtStream != null) {
			try {
				OtPtStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
            File fs = new File(imageURL);
            
            imageResizer(XDimention, YDimention, IMAGE_TYPE_JPEG, fs, imageName);
}

public String getResourcePathJsf(String resourcePath){
    return new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(resourcePath)).getPath();
}





}
