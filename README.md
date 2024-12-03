# CSC207 Final Project - Weekly Planner

This project is a weekly schedule builder paired with an AI assistant that helps users efficiently manage their time. 
The program allows users to input both fixed (scheduled at a set time) and repeated events, and the AI chatbot assists 
by checking for event conflicts and providing time estimates.

The project was made to simplify time management, especially for users with multiple tasks and busy schedules. 
It automates scheduling, helps prevent conflicts, and allows for quick adjustments to a user’s schedule.

This tool is especially useful for teams and individuals who need to manage multiple tasks, prioritize events, 
and get real-time updates on their availability—whether for students, professionals, or anyone looking to organize 
their time more effectively.

## Team members:

- Chris Cao - utcjcao
- Ryan Fu - rf216
- Vennise Ho - venniseho
- Elaine Liu - 3laine3
- Royce Luo - RoyceLuo1216

## Table of Contents

- [User Stories](#user-stories)
    * [1. [Schedule Event - Team Story]](#1--schedule-event---team-story-)
    * [2. [Edit Event - Vennise’s Story]](#2--edit-event---vennise-s-story-)
    * [3. [Delete Event - Royce’s Story]](#3--delete-event---royce-s-story-)
    * [4. [Repeat Event - Ryan’s Story]](#4--repeat-event---ryan-s-story-)
    * [5. [Event Conflicts - Elaine’s Story]](#5--event-conflicts---elaine-s-story-)
    * [6. [Time Allocation - Chris’s Story]](#6--time-allocation---chris-s-story-)
- [Features](#features)
- [Installation Instructions](#installation-instructions)
- [Usage Guide](#usage-guide)
- [License](#license)
- [Feedback](#feedback)
- [Contributions](#contributions)

## User Stories

### 1. [Add Event - Team Story] 
Ryan wants to stay organized by using the weekly planner system. This week, he is meeting a friend for lunch on Tuesday from 12:00 PM - 2:00 PM.
To keep track of this, he adds the event to the weekly planner system. The weekly planner system stores the information for future access.

### 2. [Edit Event - Vennise’s Story] 
On Monday, Alex uses the scheduler to add a study block from 6-9 PM on Friday for their statistics final. However, 
on Wednesday, they learn that their favourite artist is holding a last-minute pop-up concert at the same time as their 
scheduled study block. Alex decides to move their study block to 4-7 PM of the next day and edits the scheduler accordingly.

### 3. [Delete Event - Royce’s Story]
Tom is a busy individual who likes to keep his schedule organized using his calendar app. Recently, Tom scheduled a badminton practice event for Monday from 7:00 PM to 8:00 PM. However, due to unexpected circumstances, he can no longer attend the practice session. Tom needs to delete this event from his calendar to keep his schedule accurate and clutter-free. To do this he can use our delete event feature to delete the event from his planner using a series of button clicks from the schedule view. 

### 4. [Repeat Event - Ryan’s Story]
Edward has orchestra practice every Tuesday from 8-10. He wants to add this as a repeating event so he doesn't have to 
add the event every single week. If practice is missed, he can manually delete the event and specify if it’s a 
permanent delete (he quits forever), or a one time delete.

### 5. [Event Conflicts - Elaine’s Story] 
Jennie has a CSC207 assignment due on Friday, and she would like to know when she has time for completing this task. To do this, she asks the scheduler chatbot if she can schedule a work period from 2-4 PM on Thursday. The chatbot then replies with whether or not Jennie if free during this time. If Jennie has already scheduled Lunch from 2-3 PM and a Math class from 3-4 PM, the chatbot will let Jennie know that she cannot put her studying event from 2-4 PM because she has Lunch and a Math class during that time.

### 6. [Time Estimation - Chris’s Story]
George is a single father of two who works from 9-5, needs to send the kids to soccer practice, buy groceries, and come 
home to make dinner. Today, George also has a date at 9pm, and is wondering how long his tasks would take him. George 
can use the time estimation feature of the chatbot to find out. By inputting his tasks: a) work from 9 to 5 
b) send kids to soccer practice, c) buy groceries, and d) make dinner. The chatbot will estimate how much time each 
task would take using a Cohere API call.

## Features

1. Schedule Event: Allows users to input fixed and repeated events. Conflicts with existing events result in an error message.

2. Edit Event: Users can modify the time or details of an event, with the scheduler reflecting changes after update.

3. Delete Event: Provides an option to remove an event from the schedule, keeping the calendar accurate and clutter-free.

4. Repeat Event: Allows users to set recurring events, simplifies repetitive scheduling and enhancing ease of use.

5. Event Conflicts: A chatbot checks if a user can schedule an event by comparing it against existing events and notifying the user of any conflicts.

6. Time Estimation: A chatbot estimates how long tasks will take by inputting a user query, using a Cohere call.

## Installation Instructions

To install and run,

1. Fork the repo on Github.
2. Clone the repo.
3. Navigate to your project folder.
4. Run:

`java -jar WeeklyPlanner.jar`

## Usage Guide

Once donwloaded, run the program.

The main panel contains your current schedule, while the side panel contains a list of options.

To add an event, click the add event button and enter the paramters you want (the system will warn you if the event already exists or the times don't line up). Once done, press save and you will be redirected back to schedule. Press back at any time to return to schedule.

To use the chatbots, press any of the chatbot buttons on the right side. Each will take you to their respective bots where you type questions and enter or press ask to ask the bot. Press back to return to the schedule at any time.

To edit or delete an event, click on the event in the schedule. The event information will be printed out into the option panels. If you want to edit, simply change the paramters in each option panel and press update, or press delete to delete the event entirely.

## License

MIT License

Copyright (c) 2024 RoyceLuo1216

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Feedback

We welcome any feedback on the project! Your input helps us improve the software and make it more useful for everyone.

How to Provide Feedback:

You can share your thoughts, suggestions, or report issues by filling out our 
[Feedback Form](https://forms.gle/hCTSkCqv26yXeYS68).

What Counts as Valid Feedback:
1. Bug reports or technical issues.
2. Suggestions for new features or improvements.
3. Any usability concerns or ideas for enhancing the user experience.

What to Expect After Submitting Feedback:
1. If we implement your suggestion or resolve the issue, you may be notified in future updates.

We appreciate your time and contribution in helping us improve the project!

## Contributions

We welcome contributions to the Weekly Planner project! To contribute:

1. Click the "Fork" button at the top of the repository. 
2. Clone the repository to your local machine and make a new branch:

`git clone https://github.com/your-username/weekly-planner.git`

`git checkout -b your-feature-branch`

Guidelines for Merge Requests

1. Write clear commit messages.
2. Ensure your changes don’t break existing functionality and add tests if applicable.
3. Update documentation as needed.

Reviewing Contributions

1. Submit a pull request to the main branch.
2. A maintainer will review, suggest changes, and merge if approved.

Thank you for helping improve this project!
