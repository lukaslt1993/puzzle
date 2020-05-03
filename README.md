# puzzle
Simple [15-puzzle game](https://en.wikipedia.org/wiki/15_puzzle) with Spring Boot

The user is presented with a generated solvable puzzle, and is able to provide move(s) to the server.
The server couldn't provide the moves to complete the puzzle, because I didn't find an algorithm to solve any generated solvable puzzle
in a logical time.

This is basically a back-end only application. Just for the sake of an easier usage, it accepts GET parameters.

## Instructions
1. Import this as a Maven project and get it running.
2. Go to localhost:8080/puzzle
3. Required GET parameter is __player__, optional - __move__
4. Without specified move, a generated puzzle will be simply shown.
5. If specified, the move will be made and the puzzle updated.

Accepted values of move: __u__ (empty tile goes up), __r__ (right), __d__ (down), __l__ (left)

## Request examples
* localhost:8080/puzzle?player=player1
* localhost:8080/puzzle?player=player2
* localhost:8080/puzzle?player=player1&move=u
* localhost:8080/puzzle?player=player2&move=r

Multiple moves could be made at once, e.g. localhost:8080/puzzle?player=player1&move=uru

### References
- http://theflyingkeyboard.net/java/java-15-puzzle-solver-using-bfs/
- https://stackoverflow.com/a/34570524
