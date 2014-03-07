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
			//System.out.println("ligne num�ro:"+x);
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
	// cr�er une nouvelle image qui contient les m�mes donn�es de l'image source mais ayant //une taille r�duite par 2.

	BufferedImage imageReduite = new BufferedImage((int)(monImage.getWidth()*d),(int)( monImage.getHeight()*d), monImage.getType());

	// Une transformation affine effectue un changement d'�chelle sur l'image.
	// On doit sp�cifier le rapport suivant les axes x et y.
	// la valeur 0.5 pr�sente le coefficient de multiplication.

	AffineTransform reduire = AffineTransform.getScaleInstance(0.5, 0.5);

	// AffineTransformOp effectue une transformation g�om�trique d'une image source pour produire une image 
	// destination. Il faut donc faire une interpolation des pixels pour construire l'image cible.
	// L'entier AffineTransformOp.TYPE_BICUBIC pr�sente un compromis entre le temps de calcul et les 
	// performances.

	int interpolation = AffineTransformOp.TYPE_BICUBIC;

	AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);

	retaillerImage.filter(monImage, imageReduite );

	return imageReduite ;

}

public int [][] matriceInvers�(int [][] pixels){
	//int x=pixels[0].length-1;
	//int y=pixels.length-1;
	int [][] matriceInverse= new int [pixels[0].length][pixels.length];
	int y=0;
	for(int i=0; i<pixels[0].length;i++){
		System.out.println("entre dans 1er for i "+i);
		int x=pixels[0].length-1;
		for(int j=0; j<pixels.length;j++){
			System.out.println("entre dans 1er for j "+j);
			System.out.println("x="+x);
			matriceInverse[i][j]=pixels[x][y];
			System.out.println("pixels[x][y] "+pixels[x][y]);
			System.out.println("matriceInverse[i][j]"+matriceInverse[i][j]);
			x=x-1;
		}

		System.out.println("y="+y);
		y++;
	}
	return matriceInverse;
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
	int [][] pixeles={
			{1,1,2,2},
			{4,1,5,1},
			{6,1,0,5},
			{1,2,5,4}
	};
	System.out.println("pixels.length[0] "+pixeles[0].length);
	System.out.println("pixels.length "+pixeles.length);
	
	int[][]test= new int[4][4];
	test=image.matriceInvers�(pixeles);
	for(int e=0; e<test.length;e++){
		for(int f=0; f<test.length;f++){
			
			System.out.print(test[e][f]+" ");
		}
		System.out.println("\n");
	}
}

}
