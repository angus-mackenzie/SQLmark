# View
The view component is used for all the UI logic of the application. In this case, mainly the interface the student's use.
This is because our client requested that the `Lecturer` be a commandline interface (CLI).  Nonetheless. the `Student` class allows the
user to interact with the application. At this stage we are working on a better  (see `StudentInput`), however our `Student` view is still
CLI.

## Overview
We used JavaFX in order to create our views. That is what the `.fxml` are, if you want to view/edit them use [this](https://gluonhq.com/products/scene-builder/)

|View Item| Description |
|---|---|
|Lecturer| CLI for the Lecturer|
|Student|Initialiser for the StudentViews|
|StudentAssignment|View for ongoing assignments|
|StudentMain|Main view for students|