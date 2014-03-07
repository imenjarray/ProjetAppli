import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

public void construireImage(int ordre){
	float pixelsReduits [][]= new float [ordre][ordre];
	int i=0;
	int j=0;
	int intermediare = 0 ;
	while( i<pixels.length){
		for(int k=0; k<i;k++){
			for(int h=0; h<i;h++){
				intermediare=pixels[k][h];
			}
		}
		intermediare=intermediare/ordre;
		pixelsReduits[j][j]=intermediare;		
		i=i+(pixels.length/ordre);
	}
}

protected BufferedImage reduireImage(double d)
{
	// créer une nouvelle image qui contient les mêmes données de l'image source mais ayant //une taille réduite par 2.

	BufferedImage imageReduite = new BufferedImage((int)(monImage.getWidth()*d),(int)( monImage.getHeight()*d), monImage.getType());

	// Une transformation affine effectue un changement d'échelle sur l'image.
	// On doit spécifier le rapport suivant les axes x et y.
	// la valeur 0.5 présente le coefficient de multiplication.

	AffineTransform reduire = AffineTransform.getScaleInstance(0.5, 0.5);

	// AffineTransformOp effectue une transformation géométrique d'une image source pour produire une image 
	// destination. Il faut donc faire une interpolation des pixels pour construire l'image cible.
	// L'entier AffineTransformOp.TYPE_BICUBIC présente un compromis entre le temps de calcul et les 
	// performances.

	int interpolation = AffineTransformOp.TYPE_BICUBIC;

	AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);

	retaillerImage.filter(monImage, imageReduite );

	return imageReduite ;

}

public int [][] matriceInversé(int [][] pixels){
	int x=pixels[0].length-1;
	int y=pixels.length-1;
	int [][] matriceInverse= new int [x][y];
	
	for(int i=0; i<pixels[0].length;i++){
		for(int j=0; j<pixels.length;j++){
			matriceInverse[i][j]=pixels[x][y];
			x--;
		}
		y--;
	}
	return pixels;
}


/*public static void main(String []args) throws IOException{
	Image image= new Image();
	try{
	image.chargerImage("C:/Users/Imen/Pictures/pyramide/64.jpg");
	System.out.println("width ="+ image.monImage.getTileWidth());
	System.out.println("heigt="+image.monImage.getHeight());
	
	image.monImage= image.reduireImage(0.5);
	System.out.println("width ="+ image.monImage.getTileWidth());
	System.out.println("heigt="+image.monImage.getHeight());
	image.imageBinaire();
	image.recupererPixels();
	image.enregistrerImage(new File("C:/Users/Imen/Pictures/pyramide/32.jpg"));
	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
}*/
public static void main(String []args) throws IOException{
	Image image= new Image();
	int [][] pixels={
			{1,1,2,2},
			{4,1,5,1},
			{6,1,0,5},
			{1,2,5,4}
	};
	
	int[][]test= new int[4][4];
	test=image.matriceInversé(pixels);
	for(int e=0; e<test.length;e++){
		for(int f=0; f<test.length;f++){
			
			System.out.print(test[e][f]+" ");
		}
		System.out.println("\n");
	}
}

}
