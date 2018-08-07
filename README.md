# SQL Automarker
## Angus Mackenzie, Khaleel  Moodley, Matthew Poulter
### Repository
This is a general repository for our CSC3003S Capstone Project. If you want to contribute, please review the [Contributing](/contributing.md) file, in order to know what specifications to adhere to.
## Brief Project Description
We aim to develop a system to create and mark SQL assignments. A database of questions and answers will be created. Some question-answer pairs will be input by the lecturer and the system will generate more based on these exemplars. It will then generate a set of assignments and assign each student in the class one of the assignments in that set. Finally, it will accept student submissions, and return the student’s mark along with some indication of how their output differs from expected output.

### Further Information
#### Basic Functionality
1. Lecturer inputs CSV files of:
    * Student numbers
    * Data for DB
    * Sample Questions, where each row has 3 values
        * The question
        * The answer
        * The difficulty level
2. Student interface for doing the assignment
    * Student Login
    * System generates random assignment comprising several questions
    * Each question done in turn
    * Each question marked
    * Student told total for the assignment at the end
#### Extra Functionality
1. Add Automation for Lecturer
    * Generate db data based on example
    * Generate questions based on example
    * generate data & questsions with different scenarious
2. Make it harder for students to copy/cheat in anyway
3. Integrate with Vula REST API

**Client** Sonia Berman

**Tutor** Danielle Rose Nagar

## Project Structure
This directory has three folders which are important. [src](/src), [test](/test) and [docs](/docs). The `src` directory, contains all the code for our project. It is laid out in the MVC architectural pattern. The `test` directory includes the tests for our code, and the `docs` directory contains our documentation for our code. 