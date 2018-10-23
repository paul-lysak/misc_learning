#include <Keypad.h>
#include <Key.h>


#include <math.h>

#define RED_PIN 3
#define GREEN_PIN 5
#define BLUE_PIN 6


#define COLOR_NONE 100
#define COLOR_DEFAULT 0

#define OP_NONE 0
#define OP_PLUS 1
#define OP_MINUS 2
#define OP_SET 3

#define MAX_Y 100.0

//Keypad setup: http://www.circuitbasics.com/how-to-set-up-a-keypad-on-an-arduino/, https://playground.arduino.cc/code/keypad

float colsTable[] = {
  0.33, 0.33, 0.33,
  1, 0, 0,
  0, 1, 0,
  0, 0, 1,
  0.8, 0.6, 0.2,
  0, 0.5, 0.5,
  0.5, 0, 0.5,
  0.2, 0.5, 0.3, 
  0.7, 0.2, 0.1,
  0.1, 0.2, 0.7
};

const byte rows = 4; //four rows
const byte cols = 4; //three columns
char keys[rows][cols] = {
  {'1', '2', '3', '+'},
  {'4', '5', '6', '-'},
  {'7', '8', '9', 'S'},
  {'C', '0', '#', '='}
};
//byte rowPins[rows] = {2, 3, 4, 5};
byte rowPins[rows] = {10, 11, 12, 13};
//byte colPins[cols] = {6, 7, 8};
byte colPins[cols] = {14, 15, 16, 17};
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, rows, cols );



float tableR(byte i) {
  return colsTable[i * 3];
}

float tableG(byte i) {
  return colsTable[i * 3 + 1];
}

float tableB(byte i) {
  return colsTable[i * 3 + 2];
}

void setTableColor(byte i, float* r, float* g, float* b) {
  *r = tableR(i);
  *g = tableG(i);
  *b = tableB(i);

  Serial.print(" rgb= ");
  Serial.print(*r);
  Serial.print(",");
  Serial.print(*g);
  Serial.print(",");
  Serial.println(*b);
}

void addTableColor(byte i, float* r, float* g, float* b) {
  float r1 = *r + tableR(i);
  float g1 = *g + tableG(i);
  float b1 = *b + tableB(i);
  float k = 1 / (r1 + g1 + b1);
  *r = r1 * k;
  *g = g1 * k;
  *b = b1 * k;
  Serial.print(" rgb1= ");
  Serial.print(r1);
  Serial.print(",");
  Serial.print(g1);
  Serial.print(",");
  Serial.println(b1);
  Serial.print(" added ");
  Serial.println(tableR(i));
  Serial.println(tableG(i));
  Serial.println(tableB(i));
  Serial.print(" rgb= ");
  Serial.print(*r);
  Serial.print(",");
  Serial.print(*g);
  Serial.print(",");
  Serial.println(*b);
}

void subtractTableColor(byte i, float* r, float* g, float* b) {
  float r1 = max(*r - tableR(i), 0);
  float g1 = max(*g - tableG(i), 0);
  float b1 = max(*b - tableB(i), 0);
  float k = 1 / (r1 + g1 + b1);
  *r = r1 * k;
  *g = g1 * k;
  *b = b1 * k;
  Serial.print("k rgb1= ");
  Serial.print(k);
  Serial.print(" ");
  Serial.print(r1);
  Serial.print(",");
  Serial.print(g1);
  Serial.print(",");
  Serial.println(b1);
  Serial.print(" subtracted ");
  Serial.println(tableR(i));
  Serial.println(tableG(i));
  Serial.println(tableB(i));
  Serial.print(" rgb= ");
  Serial.print(*r);
  Serial.print(",");
  Serial.print(*g);
  Serial.print(",");
  Serial.println(*b);
}

byte selectedColor = 0;
float r, g, b;

byte selectedOp = OP_NONE;

void show(float r, float g, float b) {
  analogWrite(RED_PIN, r*MAX_Y);
  analogWrite(GREEN_PIN, g*MAX_Y);
  analogWrite(BLUE_PIN, b*MAX_Y);
}

void showAcc() {
  show(r, g, b);
}

void showTable(byte i) {
  show(tableR(i), tableG(i), tableB(i));
}

void setup() {
  Serial.begin(9600);
  pinMode(RED_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  pinMode(BLUE_PIN, OUTPUT);

  setTableColor(COLOR_DEFAULT, &r, &g, &b);
  showAcc();
}

void loop() {


  char k = keypad.getKey();

  if (k != 0) {
    Serial.print("k=");
    Serial.println(k);
  }

  switch (k) {
    case 0:
      break;
    case 'C':
      setTableColor(COLOR_DEFAULT, &r, &g, &b);
      selectedOp = OP_NONE;
      selectedColor = COLOR_NONE;
      showAcc();
      break;
    case 'S':
      selectedOp = OP_SET;
      break;
    case '+':
      selectedOp = OP_PLUS;
      break;
    case '-':
      selectedOp = OP_MINUS;
      break;
    case '=':
      if (selectedColor != COLOR_NONE) {
        if (selectedOp == OP_PLUS) {
          addTableColor(selectedColor, &r, &g, &b);
        } else if (selectedOp == OP_MINUS) {
          subtractTableColor(selectedColor, &r, &g, &b);
        }
      }
      showAcc();
      break;
    default:
      selectedColor = k - '0';
      if (selectedOp == OP_SET) {
        setTableColor(selectedColor, &r, &g, &b);
      }
      if (selectedOp != OP_NONE) {
        showTable(selectedColor);
      }
      break;
  }
}
