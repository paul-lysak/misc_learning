#define RED_PIN 8
#define YELLOW_PIN 9
#define GREEN_PIN 10

#define ONE_PIN 11
#define IN_PIN 12

#define PAUSE 300

void setup() {
  pinMode(RED_PIN, OUTPUT);
  pinMode(YELLOW_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  pinMode(ONE_PIN, OUTPUT);
  pinMode(IN_PIN, INPUT);
}

void switchLight(int pin) {
  digitalWrite(RED_PIN, LOW);
  digitalWrite(YELLOW_PIN, LOW);
  digitalWrite(GREEN_PIN, LOW);
  digitalWrite(pin, HIGH);
}


void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(IN_PIN)) {
    switchLight(RED_PIN);
    delay(PAUSE);
    switchLight(YELLOW_PIN);
    delay(PAUSE);
    switchLight(GREEN_PIN);
    delay(PAUSE);
    switchLight(YELLOW_PIN);
    delay(PAUSE);
  }
}
