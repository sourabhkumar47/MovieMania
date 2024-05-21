# MovieMania

![Logo](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/th5xamgrr6se0x5ro4g6.png)

Welcome to MovieMania, a cutting-edge movie application built using Kotlin and Jetpack Compose. This app leverages the TMDb API to bring you the latest and most popular movies right at your fingertips. With features such as bookmarking, profile management, and user authentication via Firebase, MovieMania provides a seamless and personalized movie-browsing experience. Explore, bookmark, and manage your favourite movies effortlessly.


## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)

**Technologies**
- Jetpack
  - [Compose](https://developer.android.com/jetpack/compose): Modern toolkit for building native UI.
  - [Navigation](https://developer.android.com/jetpack/compose/navigation): Navigation component for Jetpack Compose.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Store UI-related data that isn't destroyed on UI changes.
  - [Room](https://developer.android.com/jetpack/androidx/releases/room): SQLite object mapping library.
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): Load and display small chunks of data at a time.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Dependency injection library for Android.
- [Retrofit](https://square.github.io/retrofit/): Type-safe HTTP client for Android and Java.
- [Coroutines](https://developer.android.com/kotlin/coroutines): For managing background threads with simplified code and reducing needs for callbacks. with [Flow](https://kotlinlang.org/docs/flow.html).
- [Coil](https://coil-kt.github.io/coil/): Image loading library for Android backed by Kotlin Coroutines.
- [Gson](https://github.com/google/gson): A Java serialization/deserialization library to convert Java Objects into JSON and back.

**Server:** 
- TMDB API
- Firebase
  - Authentication
  - Storage
  - Firestore

## Architecture

Follows the MVVM (Model-View-ViewModel) architecture pattern, which separates the
presentation layer from the business logic and data handling. This architecture pattern helps to
create a modular, scalable, and testable codebase.

## Features

- Latest and Popular Movies: Fetches and displays the latest and trending movies using the TMDb API.
- Bookmarking: Easily bookmark your favourite movies with Preference DataStore for persistent storage.
- Profile Management: Edit and manage your user profile directly within the app.
- User Authentication: Secure login and logout functionality powered by Firebase.


## Getting Started

To deploy this project run

 - Add API key in local.properties file as

```bash
 MOVIE_API_KEY="YOUR_API_KEY" (without quotes)
```



## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |



## Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.


## Authors

- [Sourabh Kumar](https://www.github.com/sourabhkumar47)


