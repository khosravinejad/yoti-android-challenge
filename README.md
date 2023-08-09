# Solution notes

First of all, thanks for reviewing my solution. I, will provide some notes below to explain my
solution better.

- There were some other options to do as a strategy (like Feature based multi-module or layer based
  multi-module). As the project had some existing code, I felt maybe it's a good option to show how
  can I contribute in an existing project without minimum changes on the current code.
  So please consider in some areas there were better or last updated options to use, but I wanted to
  try best possible
  solution with minimum changes in the existing code. Changes are most related to enhancement or more
  clarity of the code. For example I could implement the UI in Jetpack Compose, but because of lots
  of changes I didn't use it and tried to use current codes with some improvement. For example added
  DiffUtilsCallBack to the recyclerview adapter.
- I'll appreciate it if I can have a time to explain you quickly other solutions, benefits and
  disadvantages of each one.
- Due to ability of using some tools features I had to apply small upgrades in dependencies. But I
  really tried to keep the changes small without any changes needed in the code.
- You can find the brief solution overview below. I'll really open to explain them in details (Also
  other possible solution and why I didn't use it) in the possible meeting.

# Project structure and architecture

The application follows the Clean architecture pattern with MVVM (Model-View-ViewModel) as the
architectural style. SOLID principles tried to be considered during the development. 
The project is organized into the following modules:

- **data**: This module handles data-related tasks such as data models, data mappers, data sources,
  and repository implementations.
- **domain**: The domain module contains entities, repository interfaces, and mappers
  implementations.
- **presentation**: This module is responsible for implementing the ViewModel and related
  components. The UI module utilizes these components to retrieve the required data. For simplicity,
  presentation models are used in the UI module. However, for more complex scenarios, separate UI
  models can be implemented.
- **di**: To handle Dependency Injection by Hilt.
- **ui**: The UI module provides UI implementation.
- **utils**: Provide some Helper classes.

# Tech Stack and Libraries

The application utilizes the following technologies and libraries:

- **Kotlin**: The primary programming language used in the project.

- **Coroutines**: A Kotlin library for handling asynchronous programming, simplifying code
  execution.

- **Flow**: Flow is used for asynchronous data streaming, allowing the passing of data that can be
  computed asynchronously.

- **Dagger-Hilt**: A dependency injection library for Android that simplifies dependency management.

- **JetPack**: Jetpack is a suite of libraries and tools provided by Google for Android app
  development. The following Jetpack components are used in the project:

    - **Lifecycle**: The Lifecycle component is used to observe lifecycle events of activities or
      fragments and perform actions in response to these events.

    - **ViewModel**: ViewModel is used to store UI-related data that persists across configuration
      changes. Implemented viewModels expose the data as StateFlow. I could use LiveData too, but I
      assumed in case of migrating to Jetpack Compose, StateFlow is a strong well-integrated to it.

    - **Room**: Room is an SQLite object mapping library used for local data storage. It serves as
      the Single Source of Truth for cached data.

    - **Navigation**: The Navigation component is used for navigating between screens in the
      application.




# Introduction

Welcome to Yoti's code challenge project. This repository contains an Android application with some
basic functionality
to support the development of the code test required during the hiring process for an Android
developer in Yoti.

# The challenge

A candidate have to add the required code in this project to implement the following feature:

- When the user open the app, we need to display a list of crypto currency assets (see
  CoincapService and AssetsApiData),
  displaying the asset name and symbol.

- When the user tap on an asset, we navigate to a new Fragment where we need to display detailed
  view of one Market (see CoincapService and MarketsApiData)
  that has the selected crypto currency as the "baseId", and has the highest volume transacted in
  the last 24 hours ("volumeUsd24Hr").
  The information to display in this view is:
    + Exchange ID
    + Rank
    + Price
    + Updated date with the format "Day/Month/Year"

# Guideline

The candidate is free to implement the solution in any way that consider appropriated but bear in
mind that we
in Yoti love well designed, clear and simple code. SOLID principles and Clean Architecture are
fundamental concepts in our development philosophy.

Below some points and tips that we evaluate:

- The functionality needs to be testable, and adding different types of tests as the candidate
  consider appropriated is not only a very positive bonus but almost a requirement. The absence of
  tests can be a very strong factor to fail this exercise.
- The presentation layer can be implemented following any of the industry well known patterns: MVVM,
  MVP, etc.
  Although is not mandatory, using one of these paradigms is a positive point in the evaluation.
- Use any third party library not included in the project if needed.
- The webserver API used in this exercise can frequently return errors when many requests (sometimes
  just a few) are performed from the same IP address. Take this into account as part of the exercise
  to treat errors properly.

# How to submit the code challenge to Yoti

- The candidate can checkout the project locally and push to a newly created repo with public
  permissions so we can access it (please DO NOT use Fork button from github). Alternatively, you
  can clone the project and send the recruiter the updated project in a zip file.

# Reference links

- Coincap API: https://docs.coincap.io/

