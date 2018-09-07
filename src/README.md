# Source Files
For this project we used the `Model-View-Controller` (MVC) architectural pattern. 

## Why?
MVC divides the application into three interconnected parts. The MVC design decouples major components allowing for code reuse, and parallel development. Furthermore, it is useful for developing user interfaces. Thus, it seemed the logical choice in order to streamline our group's workflow.

## Components
### Model
The model component corresponds to all the data-related logic that the user works with. This cna represent the data that is being transferred between the `View` and `Controller` components or any other project logic-related data.

|Model Class| Description |
|---|---|
|Answer|Stores a student's answer|
|Assignment|Stores the Student's assignment questions|
|CSV|Reads/Writes CSV files|
|Database|Communicates with the DB|
|Dataset|Used to compare DB results|
|Error|Custom Exception Handling|
|Question|Stores a question, and target answer|
|Runner|Used for running OS commands|
|Student|Handles a student's data|
|Submission|Handles an ongoing student's assignment|
|WorkingData|Allows access to DB data securely|

### View
The view component is used for all the UI logic of the application. In this case, mainly the interface the student's use.

|View Item| Description |
|---|---|
|Lecturer| CLI for the Lecturer|
|Student|Initialiser for the StudentViews|
|StudentAssignment|View for ongoing assignments|
|StudentMain|Main view for students|
### Controller
Controllers act as an interface between `Model` and `View` components to process all the project logic and incoming requests, manipulate data using the `Model` components and interact with the `View` to render the final output. 

|Controller|Description|
|---|---|
|Lecturer|Handles information from View to models|
|Student|Handles information from View to models|

**Sources** 
* [Wikipedia](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
* [Tutorialspoint](https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm) 
