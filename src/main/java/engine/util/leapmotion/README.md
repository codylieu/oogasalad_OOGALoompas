README
=======
Created by Yu Zhou Lee(yl218)
with LeapGameController and gamecontroller package contributed by Austin Lu (aql2)

NOTE: Add the LeapMotion JAR file from leapMotion.lib to build path.
In Eclipse, add either the x64 or x86 as the Leap Motion JAR's native library.
( setup details here: https://developer.leapmotion.com/documentation/java/devguide/Project_Setup.html )

This is a program that allows you to control your game with customizable key bindings using a Leap Motion Controller. 

This util package is adds an additional layer of interactivity between users and the game itself, allowing the user to use hand gestures and movement to interact with the game. 

By running the LeapMotionController class with a LeapMotionConnected, the user is able to control the games with default up, down, left, right and A and B controls (like a gameboy). 
These controls can be dynamically updated in game using the control panel. It currently supports additional action such as tapping and swiping. 

LeapGameController
===================
LeapGameController is a controller designed specifically for game engines such as JGame that have natural game loops,
and allows use of a finger-based mouse with Leap motion gestures for mapping to keyboard and mouse input events.

How to use:
1) Save a leap action->input event mapping with the LeapActionbindTool.java
2) Create an instance of LeapGameController in your game engine, and call its doFrame() at every frame in your
game loop (your saved action map properties file is loaded automatically from the resources folder).
