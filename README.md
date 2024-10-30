# Android GitHub GraphQL

This repository hosts my Native Android app, which explores the GitHub [GraphQL API](https://docs.github.com/en/graphql). It serves as a practice project to sharpen my development skills with modern Android libraries and architecture components, including Jetpack Compose, Kotlin Flow, Room, and more.

This app allows you to browse random GitHub accounts, search by username, and view detailed information about GitHub user profiles.

###
How to run: 
- clone using latest Android Studio
- paste your personal github token (access of Public Repositories is enough) into `local.properties` file
  `GITHUB_TOKEN="your_token"`

### Technology Stack
- Jetpack Compose
- Apollo GraphQL for Android

### Future Enhancements
- Implement a local database to store favorite GitHub accounts (hidden from random results, marked in search & detail views)
- Integrate dependency injection
- Improve error handling
- Add swipe functionality to mark accounts as favorites or fetch the next random account
- Add unit tests
- Add UI tests  
