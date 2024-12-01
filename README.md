# CSC207 Final Project - Weekly Planner

Weekly Planner enables users to create and manage weekly schedules by automatically allocating time to tasks based on 
priority, deadlines, and estimated completion time. Users can input both flexible tasks, which are scheduled around 
other events, and fixed tasks, which must occur at specific times. The program helps users adjust their schedules 
dynamically, allowing for reprioritization and rescheduling as new tasks or changes arise. For instance, users can 
remove canceled events or change task priorities, and the program will automatically update the schedule. Additionally, 
the program offers the ability to schedule breaks, estimate time allocations, and provides options to switch between 
weekly and monthly calendar views, offering flexibility for comprehensive time management.

## Team members:

- Chris Cao - utcjcao
- Ryan Fu - rf216
- Vennise Ho - venniseho
- Elaine Liu - 3laine3
- Royce Luo - RoyceLuo1216

## User Stories

### 1. [Schedule Event - Team Story] 
Given a list of tasks, I will use the auto-scheduler to create a weekly schedule to manage my time. I can input a fixed 
task, which is a task that must happen at a set day and time. I can also input a flexible task which is a task that 
will be chunked into time-blocks that will happen throughout the week. The auto-scheduler outputs a schedule created 
using the information given about these tasks. When adding this new event, if there is already another event scheduled 
at the time, I will get an error message and not be able to schedule at that time.

### 2. [Event Conflicts - Elaine’s Story] 
Jennie has a CSC207 assignment due on Friday, and she would like to know when she has time for completing this task. To do this, she asks the scheduler chatbot if she can schedule a work period from 2-4 PM on Thursday. The chatbot then replies with whether or not Jennie if free during this time. If Jennie has already scheduled Lunch from 2-3 PM and a Math class from 3-4 PM, the chatbot will let Jennie know that she cannot put her studying event from 2-4 PM because she has Lunch and a Math class during that time.

### 3. [Edit Event - Vennise’s Story] 
On Monday, Alex uses the scheduler to add a study block from 6-9 PM on Friday for their statistics final. However, 
on Wednesday, they learn that their favourite artist is holding a last-minute pop-up concert at the same time as their 
scheduled study block. Alex decides to move their study block to 4-7 PM of the next day and edits the scheduler accordingly.
The scheduler outputs a new schedule according to Alex's changes.

### 4. [Delete Event - Royce’s Story]
Tom is a busy individual who likes to keep his schedule organized using his calendar app. Recently, Tom scheduled a badminton practice event for Monday from 7:00 PM to 8:00 PM. However, due to unexpected circumstances, he can no longer attend the practice session. Tom needs to delete this event from his calendar to keep his schedule accurate and clutter-free. To do this he can use our delete event feature to delete the event from his planner using a series of button clicks from the schedule view. 

### 5. [Repeat Event - Ryan’s Story]
Edward has orchestra practice every Tuesday from 8-10. He wants to add this as a repeating event so he doesn't have to 
add the event every single week. If practice is missed, he can manually delete the event and specify if it’s a 
permanent delete (he quits forever), or a one time delete.

### 6. [Time Allocation - Chris’s Story]
George is a single father of two who works from 9-5, needs to send the kids to soccer practice, buy groceries, and come 
home to make dinner. Today, George also has a date at 9pm, and is wondering how long his tasks would take him. George 
can use the time allocation feature of the auto scheduler to find out. By inputting his tasks: a) work from 9 to 5 
(fixed), b) send kids to soccer practice (fixed), c) buy groceries (flexible from 6-8), and d) make dinner (flexible 
from 6-8), the auto scheduler will estimate how much time each task would take using a Cohere API call.


