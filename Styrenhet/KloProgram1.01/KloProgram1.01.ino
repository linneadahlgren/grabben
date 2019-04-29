#include <SPI.h>
#include <Ethernet.h>
int pwmXY=D5;
int pwmZ=D6;
int enableMotors=A0;
int xMotor1=A1;
int xMotor2=A2;


byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
byte serverIp[] = {192,168,20,195};
int port = 5000;
int x = 0;
String forward = "Forward";
String identification = "E";
EthernetClient client;

void setup() {
  delay(500);
  pinMode (pwmXY, OUTPUT);
  pinMode (enableMotors, OUTPUT);
  pinMode (xMotor1, OUTPUT);
  pinMode (xMotor2, OUTPUT);
  analogWrite(pwmXy,255);

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
 void loop() {
 if (client.connected() == true) {
    String command = client.readString();
    if (command == "UP") {
    Serial.print(command);
    
      digitalWrite(LED1, HIGH);
      //delay (1000);
     // digitalWrite(LED1, LOW);
     // delay(1000);
    }
    if (command == "DOWN") {
      digitalWrite(LED2, HIGH);
      delay (1000);
      digitalWrite(LED2, LOW);
      delay(1000);
    }
      if (command == "LEFT") {
      digitalWrite(LED3, HIGH);
      delay (1000);
      digitalWrite(LED3, LOW);
  //    delay(100);
  //    digitalWrite(LED1, HIGH);
  //    delay (200);
  //    digitalWrite(LED1, LOW);
      delay(1000);
    }
      if (command == "RIGHT") {
      digitalWrite(LED4, HIGH);
  //    digitalWrite(LED1, HIGH);
      delay (1000);
      digitalWrite(LED4, LOW);
  //    digitalWrite(LED1, LOW);
      delay(1000);
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
