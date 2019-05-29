# Grabben
Project Grabben has been developed at Malmö University for the course Systemdevelopment and projects for engineers by Linnea Dahlgen, Tove Rumar, Ludvig Juelsson Larsen, Johan Lövberg, Pontus Folke, John Lindahl and Sara Svensson. The system developed uses an android app to send directions to move a claw. The claw is controlled by several servos connected to an arduino. 

## Overview 

This repository contains all the necessary files to run and use the Clawstrophobia system. Info about the main folders.

- Styrenhet: contains the arduino code for the embedded system 
- Android: contains the android studios project for the app
- Server: contains the eclipse project for the server
- Joystick: contains the eclipse project for a client with a joystick

## Getting started

After you have cloned the project the first step to run the system is to start the server. Open the java-project from the *server* folder. Start the server by running the ServerController.java file in the server package. The other package *client* in the project can be used as a testing client. After the server is up and running you can connect the embedded system and the app. To start the embedded system you need to make sure that the IP-adress is correct in the *KloProgram1.01.ino* file. If the IP-adress is correct all you need to do is to transfer the file to the arduino. Make sure that the arduino is connected with ethernet. To get the android-app connected to the right server you can change the IP adress programatically in the MainActivity.java file. Change the private String instance named **IP**. If you already have started the app on your device you can change the IP-adress by entering the settings mode by tapping 5 times on the screen when on the start-page. When you press *play* the device will try to connect to the server and if the timer begins you have succesfully connected to the server and can play. 

To controll the embedded system with the Axis T8311 Joystick you wanna use the Joystick project that can be found in the *Joystick*-folder. Make sure that you have [JInput API](https://gametutorial.bozjatorium.com/article/JInput-Joystick-Test).

## Authors

- **Linnea Dahlgren** - *Android* - [linneadahlgren](https://github.com/linneadahlgren)
- **John Lindahl** - *Styrenhet & Server* - [livetskille](https://github.com/Livetskille)
- **Ludvig Juelsson** - *Styrenhet* - [Ludvigjuelsson]https://github.com/Ludvigjuelsson)
- **Pontus Folke** - *Styrenhet* - [PontusFolke](https://github.com/PontusFolke)
- **Tove Rumar** - *Styrenhet & Server* - [ToveRumar](https://github.com/ToveRumar)
