# Test Plan
| Test Case | Inputs | Behaviour Tested | Expected Outcomes|
| --- | --- | --- | --- |
| 1 | Valid filename; invalid filename; filename without an extension; no files at all |Application's file input |  With a valid filename the application will run as expected. With an invalid filename the application should output an error message. With a filename without an extension, the application should append the extension and find the file. If there are no files the application should ask the user to input files. | 
| 2 | 