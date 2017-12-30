# AndroidOnboardingBaseKt

This repository holds a base foundation for providing onboarding to your app (to be used in conjunction with 
Vontech's BaseServerNP), written in Kotlin. It uses two activities (`RegisterActivity` and `LoginActivity`) for UI management, and
a few classes to manage the saving of authenticated information and calling the API.

This project was originally a derivative of the Rollout project, and as such the naming for most of the items
in this project uses "Rollout". Feel free to refactor the project to your own naming needs.

Note that almost all strings, dimensions, and colors are named appropriately in their respective resource files,
so one should be able to customize the UI solely through the manipulation of these resources.

Finally, note that more recent Android Studio versions may be needed to build this project, due to newer features
used such as Kotlin and automatic font downloads.

If you have any questions, feel free to contact aaron@vontech.org
