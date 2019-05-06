#include <SPI.h>
#include <Ethernet.h>
int pwmXY=5;
int pwmZ=6;
int enableMotors=A0;
int xMotor1=7;
int xMotor2=8;

int yMotor1=9;
int yMotor2=3;
//const byte interruptPinX=2;

float voltageX = 0;
float voltageY = 0;
int directionX = 0;
int directionY = 0;

int sensorPin0 = A0;
int sensorPin1 = A1;
int sensorPin2 = A2;
int sensorPin3 = A3;




//const int halt=0;
const int fast=200;
const int intermediate=150;

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
byte serverIp[] = {192,168,0,5};
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

 // pinMode(interruptPinX,INPUT_PULLUP);
  //attachInterrupt(digitalPinToInetrrupt(interruptPin); 

  pinMode(sensorPin0, INPUT);
  pinMode(sensorPin1, INPUT);
  pinMode(sensorPin2, INPUT);
  pinMode(sensorPin3, INPUT);

  Ethernet.begin(mac);
  Serial.begin(9600);
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
void loop() {
  
  if(directionX == 0){
      int sensorVal = analogRead(sensorPin0);
      voltageX = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageX);
  }else if(directionX == 1){
      int sensorVal = analogRead(sensorPin1);
      voltageX = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageX);
  }

  if(directionY == 0){
      int sensorVal = analogRead(sensorPin2);
      voltageY = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageY);
    }else if(directionY == 1){
      int sensorVal = analogRead(sensorPin3);
      voltageY = sensorVal * (5.0 / 1023.0);
      Serial.println(voltageY);
    }


  
  if(voltageX > 1.0 || voltageX < 0.35){
    if(directionX == 0){
      haltY();
      //delay(200);
    }
    else if(directionX == 1){
      haltY();
      //delay(200);
    }
  }

    if(voltageY > 1.0 || voltageY < 0.35){
    if(directionY == 0){
      haltX();
      //delay(200);
    }
    else if(directionY == 1){
      haltX();
      //delay(200);
    }
  }


  
}
void left (){
  
}
void loop() {

  forward();
  delay(3000);

  backward();
  delay(3000);


  

 if (client.connected() == true) {
    String command = client.readString();
    
    if (command == "UP") {
    Serial.print(command);
    forward();
    }
    if (command == "DOWN") {
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
    }
        Serial.println(command);
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
