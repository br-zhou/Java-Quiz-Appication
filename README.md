# Quiz App
Made by **Brian Zhou** for *CPSC 210*

## What will the application do?
This is a Java app for students wanting to prepare for an exam. This app allows users
to create a quizzes with an arbitrary number of questions. After taking a quiz, users
will get feedback and a score. I choose this project idea because the subject aligns
with my goals - doing well on my tests.

### User Stories:
- As a user, I want to be able to create new quizzes
- As a user, I want to be able to add an arbitrary number of questions per quiz
- As a user, I want the ability to save all of my quizzes to storage
- As a user, I want the ability to retrieve my saved quizzes from storage
- As a user, I want to be able to have different types of question types to choose from
  when making a new question
- As a user, I want to be able to take the quizzes I make
- As a user, I want to be able to edit the quizzes I make
- As a user, I want to be able to add multiple questions to a quiz through the GUI
- As a user, I want to be able to edit existing quizzes through the GUI
- As a user, I want to be able to load and save the state of the application through the GUI

# Instructions for Grader
- to add multiple Quizzes to list of Quizzes, press the "New Quiz" button
- to add multiple Questions to a Quiz, press the "New" button in the Editor menu
- to delete a Question from a Quiz, press the "Delete" button in the Editor menu
- to edit a Question in a Quiz, select the question from the list and change it's fields using the Editor, and press
save changes to commit changes (NOTE: this will not save the data to storage, you must do that manually)
- to save all data, press the "Save Data" button in the main menu
- to load existing data, press the "Load Data" button in the main menu
- the visual component can be found in the main menu

Extra Notes: 
The editor menu can be accessed by either clicking "New Quiz"
Make sure to press "Save Changes" before exiting the Quiz Editor!

### Credit:
I took inspiration from the TellerApp reference project when designing my application.


### Phase 4: Task 2
Event log:<br>
Fri Dec 02 20:41:27 PST 2022<br>
New Quiz Added!<br>
Fri Dec 02 20:41:29 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:30 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:31 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:32 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:32 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:32 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:34 PST 2022<br>
Deleted a question<br>
Fri Dec 02 20:41:34 PST 2022<br>
Deleted a question<br>
Fri Dec 02 20:41:34 PST 2022<br>
Deleted a question<br>
Fri Dec 02 20:41:37 PST 2022<br>
New Quiz Added!<br>
Fri Dec 02 20:41:38 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:38 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:38 PST 2022<br>
Added new question<br>
Fri Dec 02 20:41:38 PST 2022<br>
Added new question<br>

### Phase 4: Task 3
I'm quite happy with my design with the UML. Granted, there was a LOT of refactoring from my original design - I made
many poor choices such as originally using GUI to directly control the models, without the "AppFunctions" class. The
reason I refactored my code was to be able to implement Phase 4: Task 2, which disallowed logging events directly
from the UI. I originally also had another issue: I had a class that contained all the logic, which controlled the
UI and the state. This turned out to be a horrible idea because console UI and graphical GUI had different requirements
and limitations. For example, in the console, the only way a user was able to interact with the program was through
the console. However, in the GUI, there were multiple buttons the user was able to click on, so the console logic class
was not compatible. I ended up refactoring that class as well, since I had to rewrite it anyway. Basically, I had
a lot of refactoring issues, but I fixed them already. 

Right now, the main issue I have is that AppFunctions is very messy, I would like to reduce the coupling that class
has, or at least make the coupling more understandable.