#include <math.h>

#define RED_PIN 10
#define GREEN_PIN 11
#define BLUE_PIN 12

void setup() {
  Serial.begin(9600);
  pinMode(RED_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  pinMode(BLUE_PIN, OUTPUT);
}

void loop() {


 double lightness = 0.3;
 double saturation = 0.9;
 double r1;
 double g1;
 double b1;

//HSL to RGB formulas from https://en.wikipedia.org/wiki/HSL_and_HSV

 for(int hue = 0; hue < 360; hue++) {
   double h1 = hue/60.0;
   double c = (1 - abs(2*lightness - 1))*saturation;
   double x = c * (1 - abs(fmod(h1, 2) - 1));

   double m = lightness - c / 2;

   if(0 <= h1 && h1 <= 1) {
    r1 = c; g1 = x; b1 = 0;
   } else if(1 <= h1 && h1 <=2) {
    r1 = x; g1 = c; b1 = 0;


    
   } else if( 2 <= h1 && h1 <= 3) {
    r1 = 0; g1 = c; b1 = x;
   } else if(3 <= h1 && h1 <= 4) {
    r1 = 0; g1 = x; b1 = c;
   } else if(4 <= h1 && h1 <= 5) {
    r1 = x; g1 = 0; b1 = c;
   } else if(5 <= h1 && h1 < 6) {
    r1 = c; g1 = 0; b1 = x;
   } else {
    r1 = 0; g1 = 0; b1 = 0;
   }

   double r = r1 + m;
   double g = g1 + m;
   double b = b1 + m;

//    Serial.print("h1 ");
//    Serial.println(h1);
//    Serial.print("rgb ");
//    Serial.print(r1);
//    Serial.print(" ");
//    Serial.print(g1);
//    Serial.print(" ");
//    Serial.println(b1);
   

   int k = 200;
   
   analogWrite(RED_PIN, r*k);
   analogWrite(GREEN_PIN, g*k);
   analogWrite(BLUE_PIN, b*k);
   
   delay(40);  
 }
}
