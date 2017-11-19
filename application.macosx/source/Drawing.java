import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Drawing extends PApplet {



ControlP5 cp5;

//Starting Rotation of A and B
float thetaA = 0.0f;
float thetaB = 0.0f;
//Radius of A and B
float radiusA = 3.0f;
float radiusB = 3.0f;
//Length of Pen Arms
float lenA = 3.0f;
float lenB = 3.0f;
//Distance Between Circle Centers
float cDist = 12.0f; //Might also jsut assign the y values of the plot?
//Rotation Speeds of A and B
float spinA = 100;
float spinB = 2;
//Points?
final int numPts = 20000;

//Initialize Global Variables
float c1, c2, c3, c4, c5, c6;
float[] Px, Py, Nx, Ny;

int i = 1;
int count;

public void setup() {
  Px = new float[numPts];
  Nx = new float[numPts];
  Py = new float[numPts];
  Ny = new float[numPts];
  
  
  background(255);
  stroke(2);
  textSize(15);
  fill(20, 20, 100);
  
  cp5 = new ControlP5(this);
  cp5.addButton("Graph")
     .setValue(0)
     .setPosition(50,50)
     .setSize(200,19)
     ;
     
     cp5.addSlider("spinA")
     .setPosition(300,50)
     .setRange(0,1)
     .setSize(200,19)
     ;
     
     cp5.addSlider("spinB")
     .setPosition(550,50)
     .setRange(0,1)
     .setSize(200,19)
     ;
     
     cp5.addSlider("radiusA")
     .setPosition(50,550)
     .setRange(01,10)
     .setSize(200,19)
     .setNumberOfTickMarks(10)
     ;
     
     cp5.addSlider("radiusB")
     .setPosition(550,550)
     .setRange(1,10)
     .setSize(200,19)
     .setNumberOfTickMarks(10)
     ;
    
}  

public void controlEvent(ControlEvent theEvent) {
  println(theEvent.getController().getName());
}

public void spinA(float newSpin) {
  spinA = newSpin;
}

public void spinB(float newSpin) {
  spinB = newSpin;
}

public void radiusA(float newRadius) {
  radiusA = newRadius;
}

public void radiusB(float newRadius) {
  radiusB = newRadius;
}
 
public void Graph() {
  i = 1;
  background(255);
  print();
}
  

public void draw() {
  
  for(count = 1; count < numPts - 1; count++) {

  //Set the points
  float Ax = radiusA * cos(thetaA);
  float Ay = (radiusA * sin(thetaA)) + cDist;
  float Bx = radiusB * cos(thetaB);
  float By = (radiusB * sin(thetaB)) + cDist;
  
  
  //Assign c values
    c1 = (Ay - By)/(Bx - Ax);
    c2 = (pow(lenA,2) - pow(lenB,2) - pow(Ay,2) + pow(By,2) - pow(Ax,2) + pow(Bx,2)) / (2*(Bx - Ax));
    c3 = pow(c1,2) + 1;
    c4 = 2 * (c1*c2 - c1*Ax - Ay);
    c5 = pow(c2,2) + Ax - 2*c2*Ax + pow(Ay,2) - pow(lenA,2);
    c6 = c4 / 2;
    
  //Storing Positive Case Solutions
    //Cheating with absolute value - try making a catch error thing that will replace imaginary values with 00
    Py[count] = (-c6 + sqrt(abs(pow(c6,2) - c3*c5)))/c3;
    Px[count] = c1*Py[count] + c2;
    
    Ny[count] = (-c6 - sqrt(abs(pow(c6,2) - c3*c5)))/c3;
    Nx[count] = c1*Ny[count] + c2;
    
   
  
  //Update Rotation
  thetaA = spinA * count;
  thetaB = spinB * count;
  }

  text("Spin A", 300, 40);
  text("Spin B", 550, 40);
  text("Radius A", 50, 540);
  text("Radius B", 550, 540);
  
}

public void print() {
  for(int j = 1; j < count; j++){
   ellipse(Px[j] * 30 + 400, (Py[j] * 30) - 50, 1, 1);
   ellipse(Nx[j] * 30 + 400, (Ny[j] * 30) - 50, 1, 1);
  }
   if(i < numPts - 1) {
     i++;
   }
}
  public void settings() {  size(800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Drawing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
