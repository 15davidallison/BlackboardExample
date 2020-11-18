# BlackboardExample

Java project to demonstrate the Blackboard pattern.

Assignment for CS3560.

Uses a simulated car engine and accelerator pedal to simulate engine management. ECU (Controller.java) reads knowledge off of the blackboard (Blackboard.java) and determines what is most important to act on next. All knowledge sources (KnowledgeSource.java) can post to this blackboard in any order, the controller will decide importance. The controller takes in all sensor data and attempts to keep the air/fuel ratio (AFR) as close to the stoichiometric ratio (14.7:1) as possible. Controller also monitors for engine faults such as knock events.

### Public Functions accessable to make a driver program:
- <b>Controller.afr.updateVal()</b>: Automatically generate a new air fuel ratio reading based on the status of the engine.
- <b>Controller.rpm.updateVal()</b>: Automatically generate a new engine RPM reading based on the status of the engine.
- <b>Controller.ks.updateVal()</b>: Automatically generate a new (reasonable) knock sensor reading based on the status of the engine.
- <b>Controller.ks.causeDetonationEvent()</b>: Manually generate a knock event regardless of the status of the engine.
- <b>Controller.pps.accelerate()</b>: Increase the car's pedal position by 10%, up to 100%.
- <b>Controller.pps.decelerate()</b>: Decrease the car's pedal position by 10%, down to 0%.
- <b>Controller.selectKS()</b>: Gather the next most important piece of information from the blackboard.
- <b>Controller.executeKS()</b>: Act upon the recently gathered knowledge (call must be preceeded by Controller.selectKS()).
