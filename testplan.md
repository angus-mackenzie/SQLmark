# Test Plan
| Test Case | Behaviour Tested | Inputs | Expected Outcomes
|---|---|---|---|
|1|Application’s Filename Input|Valid filename, Invalid filename, Filename w/o extension, No files|Application runs as expected, Application will output an error, Application will append extension and run as expected, Application will prompt for file |
|2|User Query Input|Blank Input, Malformed query, Incorrect Query, Correct Query|Application prompts the user for input, Application informs the user that their query is not formed correctly, Application informs the user that their query results did not meet the requirements, Application informs the user that their result is correct, their mark increments|
|3|Login Functionality|Incorrect username, Blank username, Correct username|User prompted that their username is incorrect. Username field will clear, User prompted to enter a username, User logged in |
|4|CSV Reader|Incorrect input format, Blank file, No comas, Correct input format|Application will output an error, Application will inform the user, Application will output an error, CSV data will be loaded into application|
|5|Database Connection|Invalid DB, Incorrect DB credentials, Correct DB|Application will output an error, Application will prompt user for credentials, Application will connect to database|
