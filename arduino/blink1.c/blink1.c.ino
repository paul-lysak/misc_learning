#include <Servo.h>


#define RED_PIN 10
#define YELLOW_PIN 11
#define GREEN_PIN 12

Servo myServo;  // create a servo object

// the setup function runs once when you press reset or power the board
void setup() {
  pinMode(RED_PIN, OUTPUT);
  pinMode(YELLOW_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  myServo.attach(9); // attaches the servo on pin 9 to the servo object
}

void switchLight(int pin) {
  digitalWrite(RED_PIN, LOW);
  digitalWrite(YELLOW_PIN, LOW);
  digitalWrite(GREEN_PIN, LOW);
  digitalWrite(pin, HIGH);
}

// the loop function runs over and over again forever
void loop() {
  switchLight(RED_PIN);
  myServo.write(0);
  delay(5000);
  switchLight(YELLOW_PIN);
  delay(2000);
  switchLight(GREEN_PIN);
  myServo.write(70);
  delay(5000);
  switchLight(YELLOW_PIN);
  delay(2000);
}
