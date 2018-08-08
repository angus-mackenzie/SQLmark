# Test Plan
| Test Case | Inputs | Behaviour Tested | Expected Outcomes|
| --- | --- | --- | --- |
| 1 | Valid filename; invalid filename; filename without an extension; no files at all |Application's file input |  With a valid filename the application will run as expected. With an invalid filename the application should output an error message. With a filename without an extension, the application should append the extension and find the file. If there are no files the application should ask the user to input files. | 
| 2 | User input: blank input, incorrect query, correct query | User Input | With blank input the system should realize that it is incorrect and inform the user, with an incorrect query the system should tell the user that their query does not meet the requirements |
| 3 | Incorrect username, incorrect password, blank fields | Student Login | If the username is incorrect the student must be prompted to enter in the correct username. If their password is incorrect, the student must be prompted to enter in the correct password. If the fields are blank the user must be informed. |
| 4 | Incorrect DB location 
| 5 | 