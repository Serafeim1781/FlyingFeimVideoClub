# FlyingFeimVideoClub
Video club manager, an educational project for the basics of Spring Boot , maven, and git.

This project is part of a learning quest among two friends.
The two participants develope diferent projects but with similars tasks and on the same weekly schedule(ideally). 
The overall objective is the same for both of them.

# Project Objective
The main objective of this project is to become familiar with some of the technologies used in software development and build confidence with the end goal of applying for junior software developer position.

# FlyingFeim !!???
FlyingFeim as said before is a learning project for spring boot, maven, and git.

The name is a combination of words derived from the participants names and inspired from the catchy nickname given to several quick Finnish athletes.
* Flying from translating the last name of one participant
* and Feim is part of the other participant's name


# Project Tasks
- 1st Task: Create a new spring boot project exposing a get endpoint that replies "Hello Word". Choose Java in language and make sure Maven is selected as the build automation tool. Lastly commit every change and push it to gitHub, don't forget to add the second participant as a contributor for both of you to have access to it.
- 2nd Task: Study some basic concepts of spring boot -spring beans, spring IOC(inversion of control), spring dependency injection - continue with the http protocol, and lastly learn about the REST API. Expand on the project by adding a service layer and a repository layer(Blank files).
  - Task 2.1: Study for 
    - DTOs, 
    - Mappers, 
    - Records, 
    - 3 tier architecture, 
    - Interfaces, 
    - Inheritance, 
    - Composition, 
    - == VS equals, 
    - Equals and hash code, 
    - Streams, 
    - Data structures (PX hashmaps), 
    - Spring annotations PX service, 
    - Component
    - Repository.
- 3rd Task: Make a VideoClub Managing System. Creat a RESTful API with CRUD support for the most common actions to the database. Specs: list the available media for rent, list the rented media with any appropriate additional infoformation (e.g. who rented it and for how long, expacted return date, ect.), and a transaction system (e.g. rent confirmation ect.). For the transaction there can be 2 types of payment either pay upfront (overdue charges may apply on return) or charge by the day (i.e. 2 Euros per day if payed upfrond, 3 Euros/day on return, and 5 Euros per overdue pay). There sould be a confirmation message on success for both rent and return of the media(conrfirming both the correct payment and also the availability of the media) or else, in failure, the apropriate message should be displayed. Every successful action sould update the database accordingly (if an error message is displayed there souldn't be any changes in the db, unless maybe for logs).
(Preferable use PostgeSQL for the db).
 
- 4th Task: Unit Testing. Use Mockito and junit

- 5th Task: (TBA)
