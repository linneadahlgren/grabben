#include <SPI.h>
#include <Ethernet.h>
int pwmXY=5;
int pwmZ=6;
int enableMotors=A0;
int xMotor1=A1;
int xMotor2=A2;

//const int halt=0;
const int fast=200;
const int intermediate=150;

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
byte serverIp[] = {192,168,20,195};
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
  analogWrite(pwmXY,255);

  Ethernet.begin(mac);
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
  
  
 }

 void forward (){
  analogWrite(pwmXY, fast);
  digitalWrite(xMotor1,HIGH);
  digitalWrite(xMotor2,LOW);
  
 }
 void backward (){
  
 }

 void right (){
  
}
void left (){
  
}
void loop() {
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
