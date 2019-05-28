/*
 * Author: Ludvig Juelsson Larsen, John Lindahl, Pontus Folke, Johan Lövberg, Tove Rumar, Linnea Dahlgren, Sara Svensson
 * 
 * 
 * Version: 1.0
 * Date: 2019-05-28
 */


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
float voltageZ = 0;
int directionX = 0;
int directionY = 0;
int directionZ = 0;

int sensorPin0 = A0;
int sensorPin1 = A1;
int sensorPin2 = A2;
int sensorPin3 = A3;
int sensorPin4 = A4;

const int buttonPin=2;
volatile int buttonState = 0;

HX711 scale (DOUT,CLK);
String on_scale="";

Servo zServo;
Servo kloServo;

const int fast=200;
const int intermediate=150;

//IP and MAC-adress to the arduino
byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
byte ipAdress[] = {192,168,0,30};
//IP and Port to the server that should be connected to
byte serverIp[] = {192,168,0,60};
int port = 5000;

String identification = "E";
EthernetClient client;


void setup() {
  delay(1000);
//attach all motors to a given pin
  pinMode (pwmXY, OUTPUT);
  pinMode (enableMotors, OUTPUT);
  pinMode (xMotor1, OUTPUT);
  pinMode (xMotor2, OUTPUT);
  analogWrite(pwmXY,150);

//attach all sensors to a given pin
  pinMode(sensorPin0, INPUT);
  pinMode(sensorPin1, INPUT);
  pinMode(sensorPin2, INPUT);
  pinMode(sensorPin3, INPUT);
  pinMode(sensorPin4, INPUT);

//attach the servos to a given pin. Opens the claw-servo and stops the z-axis servo
  zServo.attach(4);
  zServo.write(1550);
  kloServo.attach(6);
  kloServo.write(40);

//Setup for load-cell

  pinMode(buttonPin, INPUT);
  attachInterrupt(5, pin_ISR, RISING);
  reset_load_cell();
 
//setup for the ethernet-client  
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

//sends a message to the server
void sendMsg(String msg) {
   if (client) {
     client.println(msg);
     client.flush();
    }
   }

//stops all motors
 void halt (){
   analogWrite(pwmXY,200);
   digitalWrite(xMotor1,LOW);
   digitalWrite(xMotor2,LOW);
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,LOW);  
 }
//stops all x-axis motors
 void haltX() {
   digitalWrite(xMotor1,LOW);
   digitalWrite(xMotor2,LOW);
 }
//stops all y-axis motors
 void haltY() {
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,LOW);
}
//Run the x-axis motor in positive polarity
 void forward (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,HIGH);
  digitalWrite(xMotor2,LOW);
  directionY = 1;
 }
//Run the x-axis motor in negative polarity
 void backward (){
  analogWrite(pwmXY, 200);
  digitalWrite(xMotor1,LOW);
  digitalWrite(xMotor2,HIGH);
  directionY = 0;
 }
//Run the y-axis motor in positive polarity
void right (){
  analogWrite(pwmXY,200);
  digitalWrite(yMotor1,HIGH);
  digitalWrite(yMotor2,LOW);
  directionX = 0;
}
//Run the y-axis motor in negative polarity
void left (){
   analogWrite(pwmXY,200);
   digitalWrite(yMotor1,LOW);
   digitalWrite(yMotor2,HIGH);
   directionX = 1;
}
//Runs the z-axis servo down
void down(){
  zServo.write(1000);
  directionZ = 0;
}
//Runs the z-axis servo up
void up(){
  zServo.write(2000);
  directionZ = 1;
}
//Stops the z-axis servo
void zHalt(){
  zServo.write(1550);
}

//Method to send the claw down to try to pick up an object, leave it in the drop-zone and returns to the middle.
void grab(){
  down();
  delay(4800);
  zHalt();
  delay(1000);
  closeClaw();
  delay(500);
  maxUp();
  toBox();

//loops until the claw reaches the drop-zone
  while(directionX == 1 || directionY == 0){
    int sensorVal = analogRead(sensorPin2);
    voltageY = sensorVal * (5.0 / 1023.0);

    sensorVal = analogRead(sensorPin1);
    voltageX = sensorVal * (5.0 / 1023.0);

    if(voltageY > 1.0 || voltageY < 0.30){
      haltX();
      directionY = 1;
    }
    if(voltageX > 1.0 || voltageX < 0.30){
      haltY();
      directionX = 0;
    }
  }
  
  openClaw();
  delay(500);
  toCenter();
}
//Run the claw-servo 40° to open the claw
void openClaw(){
  kloServo.write(40);
  read_load_cell();
}
//Run the claw-servo 110° to close the claw
void closeClaw(){
  kloServo.write(110);
}
//Run the x- and y-axis motors to go to the drop-zone
void toBox(){
  backward();
  left();
}
//Run the x-and y-axis motors to go to the middle of the box
void toCenter(){
  forward();
  right();
  delay(2300);
  halt();
  forward();
  delay(400);
  halt();
}
//Reads the weight from the load-cell
void read_load_cell(){
  delay(1000);
  scale.set_scale(-822386.75);
  float weight=(scale.get_units()*1000);
  on_scale=String(weight);
  sendMsg(on_scale);
  
}
//Resets the load-cell
void reset_load_cell(){
  scale.set_scale();
  scale.tare();
}
void pin_ISR() {
  buttonState = digitalRead(buttonPin);
  reset_load_cell();
}

//Run the y-axis servo upwards until the servo stops it
void maxUp(){
  up();
  while(directionZ == 1){
      int sensorVal = analogRead(sensorPin4);
      voltageZ = sensorVal * (5.0 / 1023.0);
      if(voltageZ > 1.0 || voltageZ < 0.30){
        zHalt();
        directionZ = 0;
      }
  }
}

void loop() {
//Decides which sensor to read and calculates the voltage from it
  if(directionX == 0){
      int sensorVal = analogRead(sensorPin0);
      voltageX = sensorVal * (5.0 / 1023.0);
  }else if(directionX == 1){
      int sensorVal = analogRead(sensorPin1);
      voltageX = sensorVal * (5.0 / 1023.0);
  }

  if(directionY == 0){
      int sensorVal = analogRead(sensorPin2);
      voltageY = sensorVal * (5.0 / 1023.0);
    }else if(directionY == 1){
      int sensorVal = analogRead(sensorPin3);
      voltageY = sensorVal * (5.0 / 1023.0);
    }

//If the motors are at their max-range the sensor tells them to stop
  if(voltageX > 1.0 || voltageX < 0.30){
      haltY();
  }

  if(voltageY > 1.0 || voltageY < 0.30){
      haltX();
  }

//Sensor to decide if the claw is at is max
   if(directionZ == 1){
      int sensorVal = analogRead(sensorPin4);
      voltageZ = sensorVal * (5.0 / 1023.0);
      if(voltageZ > 1.0 || voltageZ < 0.33){
          zHalt();
      }
   }

//Handles the communication with the servo to accept commands and send messages
  if (client.connected() == true) {
    String command = client.readString();
    client.setTimeout(5);
    
    if (command == "FORWARD") {
    forward();
    }
    if (command == "BACK") {
      
    backward();
    }
    if (command == "LEFT") {
    left();
    }
    if (command == "RIGHT") {
      
    right();
    }
    if(command=="RELEASE"){
      
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
//If no client is available the ethernet-client will disconnect
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();
    while (true) {
      delay(1);
    }
  }
}
