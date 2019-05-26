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
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Amine
 */
public class imageUpAPI {
    
    // Format D'image
    
        public final  String IMAGE_TYPE_JPEG = "jpg";
	public final  String IMAGE_TYPE_GIF = "gif";
	public final  String IMAGE_TYPE_PNG = "png";    
        
// Liste des images dans un répertoire       
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

// Choix d'image au hazard dans un répertoire
public String getRandomImageChooser(String relativeWebPath){

     List<String> lstFiles = getDirectoryImagesListName(relativeWebPath);
     Random n = new Random();
     int rand = n.nextInt(lstFiles.size()) + 1;

  return lstFiles.get(rand);                             
} 

// Creation de chemin
private boolean createDirectory (String relativeWebPath){
    File files = new File(relativeWebPath);
        if (!files.exists()) {
            if (files.mkdirs()) {
                return true;
            } 
        }
    return false;
}


public int getNextImageNumberName(String relativeWebPath){
    List<String> lstImgName = getDirectoryImagesListName(relativeWebPath);
    List<Integer> lstIntName = new ArrayList<Integer>();
    int x = 1;
    for(String str : lstImgName){
        System.out.println("voila 1 : "+str);
        str = str.substring(0, str.length()-4);
        System.out.println("voila 2 : "+str);
        try {
            x = Integer.parseInt(str);
            lstIntName.add(x);
        } catch (Exception e) { }
    }
    return numManquantOrNextInt(lstIntName);
}
//---------------------------------------------------------------------

// Changement de la taille d'image extraction sous le meme répertoire avec la mention _RS
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

// Changement de la taille d'image extraction sous le meme répertoire avec un nom différent
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

// Changement de la taille d'image extraction sous un répetoire différent avec un nom différent
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

// Changement de la taille d'une liste images extraction sous le meme répertoire avec les nom (1, 2 ... nombre d'images)
public boolean imageListResizer(int XDimention, int YDimention, String outFormat, File[] fs, String imageName) {
                Dimension dms = new Dimension(XDimention,YDimention);
                BufferedImage buf;
		try {
                    
                    for(int i = 1 ; i<fs.length; i++ ){
                        String ancImageName = fs[i].getName();
                            System.out.println("ANC : "+ancImageName);
                            String pictureName = fs[i].getAbsolutePath().substring(0,fs[i].getAbsolutePath().length()- ancImageName.length());


                            pictureName = pictureName+"\\"+i+"."+outFormat;
                            buf = ImageIO.read(new File(fs[i].getAbsolutePath()));
                            BufferedImage bufFinal  = new BufferedImage(dms.width,dms.height, BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = (Graphics2D) bufFinal.getGraphics();
                            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g.drawImage(buf, 0, 0, dms.width,dms.height, null);
                            g.dispose();

                        ImageIO.write(bufFinal, IMAGE_TYPE_PNG, new File(pictureName));
                    }                    
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
	}
}

//----------------------------------------------------------------------

// ne fonctionne que pour JSF {réception d'image recherche du dossier resources ou target(pour maven) creation d'un sous répertoire et changement et sauvegarde d'image}.
public void primeFacesImageReceiver(int XDimention, int YDimention, UploadedFile file, String imageDirectory, String resourcePath, String imageName) throws IOException{
        
           InputStream InPtStream = file.getInputstream(); 
           String relativeWebPath = getResourcePathJsf(resourcePath);
          
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

// ne fonctionne que pour JSF {réception d'image recherche du dossier resources ou target(pour maven) creation d'un sous répertoire et changement et sauvegarde d'image}.
public void primeFacesMultiImageReceiver(int XDimention, int YDimention, UploadedFile file, String imageDirectory, String resourcePath) throws IOException{
        
           InputStream InPtStream = file.getInputstream(); 
           String relativeWebPath = getResourcePathJsf(resourcePath);
          
           relativeWebPath = relativeWebPath+"\\"+imageDirectory;
           createDirectory(relativeWebPath);
           System.out.println("2: "+relativeWebPath);
           String imageName = ""+getNextImageNumberName(relativeWebPath);
           
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


// ne fonctionne que pour JSF récupération du chemin du fichier d'exécution.
public String getResourcePathJsf(String resourcePath){
    return FacesContext.getCurrentInstance().getExternalContext().getRealPath(resourcePath);
}

//--------------------------------------------------------

/*
public static void main (String args[]){
   imageUpAPI UAP = new imageUpAPI();
   List <Integer> lstint = new ArrayList<Integer>();
    lstint.add(1);
    lstint.add(5);
    lstint.add(7);
    lstint.add(2);
    lstint.add(3);
   System.out.println(UAP.numManquantOrNextInt(lstint));
}
*/

private int numManquantOrNextInt(List<Integer> lstint){
    List <Integer> lstIntOr = intListOrder(lstint);
    int n = 1;
    for(int i = 0; i<lstIntOr.size(); i++){
        n = lstIntOr.get(i);
        if(n != i+1){
            return i+1;
        }
    }
    return n;
}

private List<Integer> intListOrder(List<Integer> lstInt){
     List<Integer> lstIntOrd = new ArrayList<Integer>();
      while (lstInt.size()>0){
          interneObj obj = petitElmListInteger(lstInt);
          lstIntOrd.add(obj.valeur);
          lstInt.remove(obj.index);
      }
     return lstIntOrd;
}

private interneObj petitElmListInteger(List<Integer> lstInt){
    int x  = 2147483647;
    int y  = 0;
    int ind= 0;
    interneObj obj = new interneObj();
    if(lstInt.isEmpty()){
        obj.setIndex(0);
        obj.setValeur(1);
     
    }else{
        for(int i:lstInt){
                            if(x>i){ x = i; ind = y;}
                            y++;       
        }
        obj.setIndex(ind);
        obj.setValeur(x);
    }
 
    return obj;
}

class interneObj{
    private int valeur;
    private int index;

        public int getValeur() {
            return valeur;
        }

        public void setValeur(int valeur) {
            this.valeur = valeur;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
}

}
