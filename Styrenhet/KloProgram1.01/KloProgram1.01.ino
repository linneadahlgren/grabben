
int pwmXY=5;
int pwmZ=6;
int enableMotors=A0;
int xMotor1=7;
int xMotor2=8;
int yMotor1=9;
int yMotor2=10;
int readSensorPin0 = A0;
int readSensorPin1 = A1;
int readSensorPin2 = A2;
int readSensorPin3 = A3;

float voltageX = 0;
float voltageY = 0;
int directionX = 0;
int directionY = 0;


//const int halt=0;
const int fast=200;
const int intermediate=150;



void setup() {

  pinMode (pwmXY, OUTPUT);
  pinMode (enableMotors, OUTPUT);
  pinMode (xMotor1, OUTPUT);
  pinMode (xMotor2, OUTPUT);
  pinMode (yMotor1, OUTPUT);
  pinMode (yMotor2, OUTPUT);
  analogWrite(pwmXY,150);

  pinMode(readSensorPin0, INPUT);
  pinMode(readSensorPin1, INPUT);
 
  Serial.begin(9600);



  backward();
  right();
}



 void halt (){
  
  
 }

 void forward (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,HIGH);
  digitalWrite(xMotor2,LOW);
  
  directionX = 1;
 }
 void backward (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,LOW);
  digitalWrite(xMotor2,HIGH);

  directionX = 0;
 }

 void right (){
  analogWrite(pwmXY, 200);
  digitalWrite(yMotor1,HIGH);
  digitalWrite(yMotor2,LOW);

  directionY = 1;
  
}
void left (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,LOW);
  digitalWrite(xMotor2,HIGH);

  directionX = 0;
  
}
void loop() {

  switch(directionX){
    case 0:
      int sensorVal = analogRead(readSensorPin0);
      voltageX = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageX);
    break;
    case 1:
      sensorVal = analogRead(readSensorPin1);
      voltageX = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageX);
    break;
  }
  switch(directionY){
    case 0:
      int sensorVal = analogRead(readSensorPin2);
      voltageY = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageY);
      break;
    case 1:
      sensorVal = analogRead(readSensorPin3);
      voltageY = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageY);
     break;
  }

  
  if(voltageX > 1.0){
    if(directionX == 0){
      forward();
      //delay(200);
    }
    else if(directionX == 1){
      backward();
      //delay(200);
    }
  }

    if(voltageY > 1.0){
    if(directionY == 0){
      forward();
      //delay(200);
    }
    else if(directionY == 1){
      backward();
      //delay(200);
    }
  }

  
}
