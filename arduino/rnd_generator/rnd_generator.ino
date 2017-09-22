#include <LiquidCrystal.h>

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

#define btnRIGHT  0
#define btnUP     1
#define btnDOWN   2
#define btnLEFT   3
#define btnSELECT 4
#define btnNONE   5

int read_LCD_buttons(){               // read the buttons
    int adc_key_in = analogRead(0);       // read the value from the sensor 

    // my buttons when read are centered at these valies: 0, 144, 329, 504, 741
    // we add approx 50 to those values and check to see if we are close
    // We make this the 1st option for speed reasons since it will be the most likely result

    if (adc_key_in > 1000) return btnNONE; 

    // For V1.1 us this threshold
    if (adc_key_in < 50)   return btnRIGHT;  
    if (adc_key_in < 250)  return btnUP; 
    if (adc_key_in < 450)  return btnDOWN; 
    if (adc_key_in < 650)  return btnLEFT; 
    if (adc_key_in < 850)  return btnSELECT;  

   // For V1.0 comment the other threshold and use the one below:
   /*
     if (adc_key_in < 50)   return btnRIGHT;  
     if (adc_key_in < 195)  return btnUP; 
     if (adc_key_in < 380)  return btnDOWN; 
     if (adc_key_in < 555)  return btnLEFT; 
     if (adc_key_in < 790)  return btnSELECT;   
   */

    return btnNONE;                // when all others fail, return this.
}

void setup() {
  lcd.begin(16, 2);
}

int i = 0;

int limit = 6;
int dice = 0;
boolean rolling = false;

void loop() {
   lcd.setCursor(0,0);
   lcd.print("limit: ");
   lcd.print(limit);

   lcd.setCursor(0,1);
   lcd.print("dice: ");
   if(rolling)
     lcd.print("rolling...");
   else if(dice == 0)
     lcd.print("press sel");
   else
     lcd.print(dice);


   lcd.setCursor(6,1);             // move to the begining of the second line
   int lcd_key = read_LCD_buttons();   // read the buttons

   switch (lcd_key) {
       case btnUP:
             if(limit < 100) {
               limit++;
               delay(200);
               lcd.clear();
             }
             break;
       case btnDOWN:
             if(limit > 1) {
               limit--;
               delay(200);
               lcd.clear();
             }
             break;
       case btnLEFT:
       case btnSELECT:
         rolling = true;         
         break;
       default: {
         if(rolling) {
           dice = millis() % limit + 1;
           rolling = false;
           lcd.clear();
         }
       }
   }
}
