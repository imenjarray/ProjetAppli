import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Image {
int resolution;
BufferedImage monImage = null;
ImageIO Imageio;
String url;
int pixels [][];
public Image(){

}

public void chargerImage(String url) throws IOException{
	monImage=Imageio.read(new File(url));
	

}

protected void imageBinaire()
{   	
	BufferedImage imgBinaire = new BufferedImage(monImage.getWidth(), monImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	Graphics2D surfaceImg = imgBinaire.createGraphics();
	surfaceImg.drawImage(monImage, null, null);      
	monImage = imgBinaire;

}

protected void enregistrerImage(File fichierImage) throws IOException
{
	String format ="JPG";
	BufferedImage image = monImage;
	ImageIO.write(image, format, fichierImage);   
}

public void recupererPixels(){
	Raster tramePixel = monImage.getRaster();
	ColorModel modeleCouleur = monImage.getColorModel();
	 pixels= new int [monImage.getHeight()][monImage.getTileWidth()];
	 for(int y=0;y< monImage.getHeight(); y++){
		 System.out.println("");
		 for(int x=0; x<monImage.getTileWidth(); x++){
			 //System.out.println("ligne numéro:"+x);
		Object objCouleur   = tramePixel.getDataElements(x, y, null);
		if(modeleCouleur.getRed(objCouleur)==255){
			pixels[y][x]=1;
			System.out.print(pixels[y][x]);
		}
		else {
			pixels[y][x]=0;
			System.out.print(pixels[y][x]);
		}
		
		}
	}
	
	}

public static void main(String []args) throws IOException{
	Image image= new Image();
	try{
	image.chargerImage("C:/Users/Imen/Pictures/fcbk/image5.jpg");
	System.out.println("width ="+ image.monImage.getTileWidth());
	System.out.println("heigt="+image.monImage.getHeight());
	image.imageBinaire();
	image.recupererPixels();
	image.enregistrerImage(new File("C:/Users/Imen/Pictures/fcbk/test.jpg"));
	
	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
}

}
