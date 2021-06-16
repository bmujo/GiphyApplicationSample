# GiphyApplicationSample

Giphy Application is a simple project that uses Giphy API to show trending gifs, to search gifs and to show gif details

## Tech Stack

This project uses Android Architecture Components, recommended architecture for building apps

## Development setup

You require the latest Android Studio 4.2 (stable channel) to be able to build the app.


### Libraries
* [Android Architecture Components][arch]
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for loading gifs
* [Paging][paging] for pagination
* [Hilt][hilt] for dependency injection
* [GSON][gson] for JSON desiralization

[arch]: https://developer.android.com/arch
[paging]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[hilt]: https://developer.android.com/training/dependency-injection/hilt-android
[gson]: https://github.com/google/gson

### API keys

You need to supply API / client key for the service the app uses.

- [GIPHY](https://developers.giphy.com/docs/api/)

Once you obtain the key, you can set them in your apiclient/GiphyApiInterface

