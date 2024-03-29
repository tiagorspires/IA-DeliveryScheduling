# Delivery Scheduling

## Overview
This project aims to optimize package delivery scheduling using various heuristic algorithms. It provides a menu-driven interface to configure the optimization process and select the desired algorithm.

## How to Run (Developed on IntelliJ IDEA)
1. Clone the repository to your local machine.
2. Open IntelliJ IDEA.
3. Choose "Open" from the IntelliJ IDEA welcome screen and select the cloned project directory.
4. Once the project is open, navigate to the `Main` class.
5. Right-click on the `Main` class and select "Run Main.main()" from the context menu.
6. Follow the on-screen instructions to navigate the menu and configure the optimization process.

## Usage
- Upon running the application, the menu will display the current configuration, which can be modified as desired.
- Options are provided to change configuration parameters or generate packages.
- Select the desired algorithm from the menu to begin the optimization process.
- Follow the on-screen instructions to configure the algorithm-specific parameters and execute the optimization.

## Development Environment
- This project was developed using IntelliJ IDEA.
- Other development environments can also be used, but the provided instructions are tailored to IntelliJ IDEA.

## Structure
- The project consists of multiple classes, each implementing a different heuristic algorithm for package delivery scheduling.
- Test classes are provided for each heuristic, allowing direct configuration changes and execution.

## Problem Formulation and Solution representation​
Our project aims to solve a package delivery optimization problem using various algorithms​
We are tasked with optimizing the delivery of packages in a given area, considering factors such as urgency, fragility, and distance.​

__Minimize Fragile Damage__  
Fragile packages have a chance of damage (X%) for every kilometer traveled (Y),
incurring a cost of Z for each damaged package. The probability of a package
breaking is calculated as follows:

$$P_{\text{damage}} = 1 - (1 - X)^Y$$
P<sub>damage</sub> is calculated for all fragile objects when they arrive at the destination. Then calculate whether the object is damaged or not.

__Minimize Travel Costs__  
Each kilometer traveled incurs a fixed cost C.

__Adhere to Urgent Delivery Constraints__  
Urgent packages incur a penalty for delivery outside the expected time, penalized by
a fixed amount for each minute of delay. The penalty per minute is equal to the fixed
costs C.

### Constraints:
1. You only have one vehicle available.
2. The delivery locations are specified by their coordinates.
3. Routes between all delivery coordinates are available.
4. The driver drives at 60km per hour and takes 0 seconds to deliver the goods.
5. The cost per km is C=0.3.

### Package Types:
1. Fragile packages: Have a chance of damage during transportation.
2. Normal packages: No risk of damage during transportation.
3. Urgent packages: Incur a penalty for delivery outside the expected time.

### Objective Function
Minimize the total cost, considering fragile damage, travel costs, and urgent delivery
penalties.
   
The solution is represented as a sequence of locations (nodes) that the delivery vehicle visits during its route. Each location corresponds to a package delivery point or depot. The order of these locations determines the delivery sequence. This representation was encoded as an array.​

### Hard constraints​
- Time Window​
- Package Priority​
- You only have one vehicle available.

  
![GIF](delivery.gif)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/e47742c0-966b-4836-a07d-8866de756c76)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/14aaafc2-1f37-40c6-9e93-6378cbd1af20)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/d83bc295-6b53-4d83-bfb1-ddbe94c0b7b7)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/a766ee5d-d0a8-4698-8baa-ae7bad869cf5)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/ad3a336f-9f33-4faa-9bc7-4d58240aa452)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/ddf50ae4-6c8a-4533-ad0a-d5e9741ee598)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/b3b7b928-cba7-41ac-b8a1-c31464ddfcce)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/19028598-f8e8-4b7c-abee-c24131a12218)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/88c4edac-3e56-43f6-b41f-f460664e8ba0)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/9ba5ce36-4b09-4851-9d05-a61eb29fb4a0)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/cd19e57d-6a8a-4582-ba8f-78dab4769f5c)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/3aa2c466-ed40-454b-beec-4d23a8940593)
![image](https://github.com/BlackAlbino17/IA-23-24/assets/61878020/c09a1f7f-9fdc-4127-9111-04e9d41e6ef0)


## Participants
- Naldo Neves Monteiro Delgado (up201808613)
- Ricardo Antonio Pinto Da Cruz (up202008789)
- Tiago Rocha Silveira Pires (up202008790)****

