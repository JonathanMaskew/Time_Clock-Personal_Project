# Time Clock - Personal Project

## General Overview
This simple time clock program allows a user to log in to their account to view their time clocked in, clock in, or clock out if still clocked in. The program writes the current time to a file, allowing a user to stop the program and come back later to stop the clock and still show accurate time clocked in. The program creates a new file for each user, making it easy to differentiate between users.

### Purpose of Program
The program is intended to allow a user to start what is essentially a stopwatch to track their time working. This is useful if an individual is paid based on time worked. Once a user stops the clock, it will display the total time they were working in hours and minutes.

### What I Learned
I learned how to use LocalDateTime. This is my first time ever working with real dates and times in my code. I also learned new methods of File in order to create separate files per user. Lastly, while I've used databases before, this is probably one of my more complex databases that I indiependently coded.

### Features
- Users can create and log in to their own account.
- Users can clock in and out with the click of a button.
- Users can view their time clocked in in hours and minutes.
- The program creates a separate file for each user.
- The program will not allow duplicate usernames.
- The program converts times into minutes, calculate the time between, and then convert into 12-hour time format.
- User does not have to leave the program running while clocked in.
- User can leave clock running over a multiple day timespan.
- The program will differentiate between AM and PM.
- If clocked in, the program will show the user the time they clocked in to let them know the clock is still running.

## Class Overview
### TimeClock.java
The TimeClock class handles the user end of the program. All the Graphical User Interface elements are built and populated in the TimeClock class.

### Database.java
The Database class handles all things associated with sorting through users data. This is where we write to and read from the file, calculate the time that a user had been clocked in, determine if a user exists when logging in, and determine if a username is taken when creating a new account. Everytime a new account is created, a new file unique to the user is created. This is where all of that users data is stored in order to easily differentiate between different users data. Additionally, a separate file contains all of the Person class data for each user. This is what we check against when logging in, and new information is added for each new account that is created.

### Person.java
The Person class represents a user in the program and holds their name, username, and password.

## What can be Improved in the Future?
While this is currently a working program, there are always things to improve upon. Here are some ideas that I may implement in the future:
- If the clock is running through the new year (for example, if the clock is started on Dec 31, 2020 and stopped on Jan 1, 2021), there is no handling to calculate the amount of time passed once the days in the year starts over.
- While it currently supports multiple accounts, only one person can be logged on at a time and it must be from the computer running the program. Add functionality to make program work with a server so that multiple people can log on at the same time from anywhere.
- With the server, allow a supervisor or boss access to an employees time entries remotely. They cannot edit the users info, only view.
- Show the total time the user has worked across all their entries.
- Allow a user to remove specific entries.
- Allow a user to edit entries (say they forgot to stop the clock).
- Allow a user to delete their account.
- Allow a user to edit their account information.
- Account for seconds. For example, right now if someone clocks in at 4:15:50pm, one it hits 4:16:00, the program counts this as a minute, even though they were really only clocked in for 10 seconds.

## About the Programmer
Jonathan Maskew, Purdue University Computer Science Student

Began work on program in December 2020. This is a personal project. I completed it for my own benefit.
