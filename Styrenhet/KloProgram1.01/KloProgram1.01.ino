#include <SPI.h>
#include <Ethernet.h>
#include<Servo.h>
#include "HX711.h"
#define DOUT  40  
#define CLK  41

int pwmXY=5;

int enableMotors=A0;
int xMotor1=7;
int xMotor2=8;

int yMotor1=9;
int yMotor2=3;


float voltageX = 0;
float voltageY = 0;
int directionX = 0;
int directionY = 0;

int sensorPin0 = A0;
int sensorPin1 = A1;
int sensorPin2 = A2;
int sensorPin3 = A3;

const int buttonPin=2;
const int ledPin =  13;
volatile int buttonState = 0;

HX711 scale (DOUT,CLK);

Servo zServo;
Servo kloServo;

//const int halt=0;
const int fast=200;
const int intermediate=150;

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
byte ipAdress[] = {192,168,0,30};
byte serverIp[] = {192,168,0,50};
int port = 5000;
int x = 0;

String identification = "E";
EthernetClient client;


void setup() {
  delay(500);
  pinMode (pwmXY, OUTPUT);
  pinMode (enableMotors, OUTPUT);
  pinMode (xMotor1, OUTPUT);
  pinMode (xMotor2, OUTPUT);
  analogWrite(pwmXY,150);


  pinMode(sensorPin0, INPUT);
  pinMode(sensorPin1, INPUT);
  pinMode(sensorPin2, INPUT);
  pinMode(sensorPin3, INPUT);

  zServo.attach(4);
  zServo.write(1550);
  kloServo.attach(6);
  kloServo.write(40);

//Setup for load-cell

  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT);
  attachInterrupt(5, pin_ISR, RISING);
  reset_load_cell();
  
  Ethernet.begin(mac, ipAdress);
  Serial.begin(9600);
  delay(1000);
  
  Serial.println(Ethernet.localIP());

  Serial.println("connecting...");

// if you get a connection, report back via serial:
  int temp = client.connect(serverIp, port);
  Serial.println(temp);
  
  if (temp == 1) {
    delay(1000);
    Serial.println("connected");
    sendMsg(identification);
  } else {
   Serial.println(port);
   Serial.println("connection failed");
  }
}

void sendMsg(String msg) {
   if (client) {
     client.println(msg);
     client.flush();
    }
   }

 void halt (){
   analogWrite(pwmXY,200);
   digitalWrite(xMotor1,LOW);
   digitalWrite(xMotor2,LOW);
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,LOW);  
 }

 void haltX() {
   digitalWrite(xMotor1,LOW);
   digitalWrite(xMotor2,LOW);
 }

 void haltY() {
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,LOW);

 }

 void forward (){
  Serial.println("in forward-methid");
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,HIGH);
  digitalWrite(xMotor2,LOW);
  directionY = 1;
 }
 void backward (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,LOW);
  digitalWrite(xMotor2,HIGH);
  directionY = 0;
 }

 void right (){

  analogWrite(pwmXY,200);
  digitalWrite(yMotor1,HIGH);
  digitalWrite(yMotor2,LOW);
  directionX = 0;
}
void left (){
   analogWrite(pwmXY,200);
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,HIGH);
   directionX = 1;
}
void down(){
  zServo.write(1000);
}
void up(){
  zServo.write(2000);
}
void zHalt(){
  zServo.write(1550);
}
void grab(){
  down();
  delay(3000);
  zHalt();
  delay(1000);
  closeClaw();
  up();
  delay(3000);
  zHalt();
  toBox();
  delay(5000);
  openClaw();
  delay(1000);
  toCenter();
}
void openClaw(){
  kloServo.write(40);
  read_load_cell();
  //delay(1000);
}
void closeClaw(){
  kloServo.write(110);
  delay(1000);
}

void toBox(){
  backward();
  left();
}

void toCenter(){
  forward();
  right();
  delay(2300);
  halt();
  forward();
  delay(400);
  halt();
}
void read_load_cell(){
  delay(1000);
  scale.set_scale(-822386.75);
  float weight=(scale.get_units()*1000);
  Serial.println("Vikt: ");
   Serial.println( weight);
}
void reset_load_cell(){
  scale.set_scale();
  scale.tare();
}
void pin_ISR() {
  buttonState = digitalRead(buttonPin);
  digitalWrite(ledPin, 1);
 reset_load_cell();
}

void loop() {
 
  
  if(directionX == 0){
      int sensorVal = analogRead(sensorPin0);
      voltageX = sensorVal * (5.0 / 1023.0);
      //Serial.println(voltageX);
  }else if(directionX == 1){
      int sensorVal = analogRead(sensorPin1);
      voltageX = sensorVal * (5.0 / 1023.0);
      //Serial.println(voltageX);
  }

  if(directionY == 0){
      int sensorVal = analogRead(sensorPin2);
      voltageY = sensorVal * (5.0 / 1023.0);
      //Serial.println(voltageY);
    }else if(directionY == 1){
      int sensorVal = analogRead(sensorPin3);
      voltageY = sensorVal * (5.0 / 1023.0);
      //Serial.println(voltageY);
    }


  
  if(voltageX > 1.0 || voltageX < 0.30){
    if(directionX == 0){
      haltY();
    }
    else if(directionX == 1){
      haltY();
    }
  }

  if(voltageY > 1.0 || voltageY < 0.30){
    if(directionY == 0){
      haltX();
    }
    else if(directionY == 1){
      haltX();
    }
  }

if (client.connected() == true) {
    String command = client.readString();
    client.setTimeout(5);
    
    if (command == "FORWARD") {
    forward();
    }
    if (command == "BACK") {
      Serial.println("command coming in: back");
    backward();
    }
    if (command == "LEFT") {
    left();
    }
    if (command == "RIGHT") {
      Serial.println("command coming in: right");
    right();
    }
    if(command=="RELEASE"){
      Serial.println("command coming in: Release");
    halt();
    zHalt();
    }
     if(command=="UP"){
    up();
    }
     if(command=="DOWN"){
    down();
    }
     if(command=="GRAB"){
    grab();
    }
      if(command=="OPEN"){
    openClaw();
    }
      if(command=="CLOSE"){
   closeClaw();
    }
        
 }
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();
    while (true) {
      delay(1);
    }
  }
}
