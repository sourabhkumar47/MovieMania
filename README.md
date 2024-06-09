# MovieMania

![Logo](https://github.com/sourabhkumar47/MovieMania/assets/81826285/3884699e-8dae-45e6-bbd0-5a6796aa4109)

Welcome to MovieMania, a cutting-edge movie application built using Kotlin and Jetpack Compose. This app leverages the TMDb API to bring you the latest and most popular movies right at your fingertips. With features such as bookmarking, profile management, and user authentication via Firebase, MovieMania provides a seamless and personalized movie-browsing experience. Explore, bookmark, and manage your favourite movies effortlessly.

### ⚒️ Architecture

MovieMania follows the principles of Clean Architecture.

### 👨‍💻 Tech stack

| Tools                 |                                     Link                                                                  |
|:----------------------|:---------------------------------------------------------------------------------------------------------:|
| 🤖  Language          |                       [Kotlin](https://kotlinlang.org)                                                    |
| 💉  DI                |          [Dagger-Hilt](https://insert-koin.io/docs/reference/koin-mp/kmp/)                                |
| 🧭  Navigation        |        [Compose Nabigation](https://github.com/Tlaster/PreCompose)                                        |
| 🧶  Multi-threading   |     [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)                                  |

## Screenshots

#### Android
- Dark
<table style="width:100%">
  <tr>
    <th>login</th>
    <th>Home</th>
    <th>Home 2</th>
    <th>Detail</th>
    <th>Bookmark</th>
    <th>Profile</th>
  </tr>


  
  <tr>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/fd3f1520-73ca-443f-aa57-5cf433949da5" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/c819e68f-8d4e-4c79-9d06-6c69c1e6f14e" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/dcca4a2f-d89a-4c1f-9daf-dabe591b53ed" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/24186916-d677-4faa-a45b-8774a47c848f" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/eff0c0e5-8482-4d0c-a5ac-8d7c467e89fe" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/77ce7228-51dc-4760-8208-9b90bba2a55f" width=150/></td>
  </tr>
</table>



- Light
<table style="width:100%">
  <tr>
    <th>Splash</th>
    <th>Registration</th>
    <th>Home</th>
    <th>Detail</th>
    <th>Bookmark</th>
    <th>Profile</th>
  </tr>


  <tr>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/e8f0ba5b-62b1-4bea-a272-9b0df73047a7" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/f01cc439-abda-4fbd-9df2-8984c8754bbf" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/9980b955-1fb8-4f2b-8010-3455a2ff9eb1" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/8651532d-8de0-4f58-ab1a-b2df9417b70f" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/d010649e-3146-4731-a53e-e2079b567fa7" width=150/></td>
    <td><img src = "https://github.com/sourabhkumar47/MovieMania/assets/81826285/373ce245-293f-400f-a003-9c954c623b45" width=150/></td>
  </tr>
</table>



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

 - API key is required to run this project. 
 - You can get it from [TMDB](https://www.themoviedb.org/documentation/api)

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


