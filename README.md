vba-game-debugger
=================

A project with java bindings to VisualBoyAdvance, and current wrappers for decoding RAM in generation 1 Pokemon games.

The current objective is to rebuild enough of the game state to be used by an AI bot to complete the game. The custom debugger could easily be used to build support for more games in the future. 

![main window](https://raw.githubusercontent.com/jamesread/vba-game-debugger/master/doc/screenshots/pokemonRed/debuggerWithBot.png)

## Status / Roadmap

- Debugger
    - [x] Memory watch window
    - [ ] Memory search
    - [ ] Breakpoints
- Games
    - Pokemon Red
        - [x] Can read text and basic state info from the game
        - [x] Can decode basic map data
        - [ ] Working bot :-)

## Version 1

Written in Java. Started in approx 2014, which is also when all of the work happened. Picked up briefly again in 2016, and now more recently in late 2021. 


