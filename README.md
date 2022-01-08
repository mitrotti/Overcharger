# Overcharger

### Introduction

Simple Android App that "announces" (TTS) when the battery changes 10%. The idea is to be notified when your phone has reached the level you desire, in order to prevent overcharging, and improve battery life.

This is not meant to be a "full-blown" Android project, but rather, a showcase of the following Android skills:
- Broadcast Receiver
- Preference saving/storage
- Multi-threading

![screenshot](https://i.imgur.com/4ugReG7.png)

### Future work

Some potential improvement ideas:
- Polish the UI
- On the Android emulator, only one battery change event is broadcast, but on real phones, multiple events are brodcast. There could be an option to let the user choose if they only want one anouncment
- It might be possible/useful to fire the app when the charger is plugged in