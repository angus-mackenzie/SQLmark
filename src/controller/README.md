# Controller
Controllers act as an interface between `Model` and `View` components to process all the project logic and incoming requests, manipulate data using the `Model` component and interact with the `Views` to render the final output. 
In the `Controller` file we have included the `Lecturer` and the `Student` classes. These aggregate the different actions for either party.
## Description
The `Lecturer` class allows the lecturer to:
* Load in the necessary data
    * Data to be worked with
    * Student Numbers of course students
    * Questions and Answers
* Create an `Assignment` of the data
* Clear a previous `Assignment` and data
* Get the current marks for all students as a `.csv` file

The `Student` class allows a student to:
* View their `Assignment`
* View their dataset
* Submit answers for their `Assignment`
* View their mark
* Receive `Feedback`
* View their past `Submissions`

