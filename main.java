import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.stage.Stage;
/* Author: Dilay SAPMAZ
 * ID: 041701032
 * Finish Date: 08.04.2018
 * Explanation: This code finds the weight of many given points and draws a line. 
 */

public class Dilay_Sapmaz extends Application {
	public void start(Stage primaryStage) throws FileNotFoundException{

		Pane pane = new Pane();

		//reading txt 
		File file = new File("coordinates3.txt"); 
		Scanner coordinates = new Scanner(file);   

		if(!file.exists()) {    
			System.out.println("File can not be found! exiting program...");
			System.exit(1);

		}
		//Arraylist for store cordinates's x and y
		ArrayList <PointXY> kordinat=new ArrayList <PointXY> ();

		while(coordinates.hasNext()) {  
			int x= coordinates.nextInt();
			int y= coordinates.nextInt();
			kordinat.add(new PointXY(x,y));
		}	

		int maxX = 0; // finding maximum x value
		int maxY = 0; // finding maximum y value

		// these are for perfect scene parameters. it finds maximum x and y values and creates scene sides
		for(int i=0;i<kordinat.size(); i++) {  
			if(maxX<kordinat.get(i).x) {
				maxX=kordinat.get(i).x;			
			}
			if(maxY<kordinat.get(i).y) {
				maxY=kordinat.get(i).y;
			}
		}

		double sceneWidth = maxX; 
		double sceneHeight = maxY;

		//this is for small dots. Their colors are random.
		for(int i=0; i<kordinat.size(); i++) { 

			Circle circle=new Circle();
			circle.setCenterX(kordinat.get(i).x+8);
			circle.setCenterY((sceneHeight-kordinat.get(i).y));
			circle.setRadius(3);
			circle.setFill(randomColor()); 
			circle.setStroke(randomColor()); 
			pane.getChildren().add(circle); 
			circle.setOpacity(0.7);
		}
		double averageX = 0;
		double averageY = 0;
		double sumx = 0;
		double sumy = 0;


		for(int i=0; i<kordinat.size();i++) {

			sumx = sumx + kordinat.get(i).x; //finds sum of all x
			sumy = sumy + kordinat.get(i).y;//sum of all y
			averageX = sumx / kordinat.size();  //calculates average value
			averageY = sumy / kordinat.size();  // calculates average values

		}
		
		//these are for x-xaverage and y-yaverage
		ArrayList <Double> valueMinusAverageX = new ArrayList <Double>(); //son hali x-xaverage oldu
		ArrayList <Double> valueMinusAverageY = new ArrayList <Double>(); //son hali y-yaverage oldu.

		double minusx = 0;
		double minusy = 0;

		for(int i=0; i<kordinat.size(); i++) {

			minusx = kordinat.get(i).x - averageX;
			valueMinusAverageX.add(minusx);

			minusy = kordinat.get(i).y - averageY;
			valueMinusAverageY.add(minusy);

		}
		//store: square of (store x-averagex) 
		ArrayList <Double> squareMinusThingX = new ArrayList <Double>(); //x-xaverage ın karesini bir arrayde tutmaya çalıştım.

		double squareMinusX = 0;

		for(int i=0; i<kordinat.size();i++){
			squareMinusX = Math.pow(valueMinusAverageX.get(i), 2);
			squareMinusThingX.add(squareMinusX);
		}
		//(x-xaverage) * (y-yaverage) these are all for formula
		ArrayList <Double> multiplyXandY = new ArrayList <Double>();

		double soncolumn = 0;

		for(int i=0; i<kordinat.size();i++) {

			soncolumn = valueMinusAverageX.get(i) * valueMinusAverageY.get(i);
			multiplyXandY.add(soncolumn);
		}

		double b = 0;
		double a = 0;
		double squareToplamı = 0;
		double multiplyToplamı = 0;

		for(int i=0; i<kordinat.size();i++) {
			squareToplamı = squareToplamı + squareMinusThingX.get(i);
			multiplyToplamı = multiplyToplamı + multiplyXandY.get(i);
		}
		//these are total symbol formula
		a = multiplyToplamı / squareToplamı;
		b = averageY - (a * averageX);
		System.out.println(" max x: " + maxX + " max y: " + maxY + " a: " + a + " b: " + b);
		System.out.println("average y: " + averageY + " average x: "+ averageX + " scene Width " +sceneWidth);

		//this is for drawing line
		Line line= new Line();
		//line's starting x,y and ending x,y points.
		line.setStartX(0);
		line.setStartY(maxY-b);
		line.setEndX(maxX);
		line.setEndY(maxY-(sceneWidth*a+b));

		line.setStroke(Color.BLACK);
		line.setStrokeWidth(2);
		pane.getChildren().add(line);

		//create scene and it's parameters
		Scene scene = new Scene(pane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Line Fitting"); 
		primaryStage.setResizable(false);
		primaryStage.setScene(scene); 
		primaryStage.show();
	}		
	//this method for random color.
	public Color randomColor() { //method that random colors
		Random random=new Random();
		int red=random.nextInt(255);
		int green=random.nextInt(255);
		int blue=random.nextInt(255);
	return Color.rgb(red, green, blue);
	}
	public static void main(String[] args) {
		launch(args);

	}

}

