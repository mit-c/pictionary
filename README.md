Endpoints:
- GET `game/{gameId}/drawing`
- PUT/STREAM `game/{gameId}/drawing`
- GET `game/{gameId}/state`
- PUT `game/{gameId}/guess`

Database structure


Game
- sessionId
- gameId

Drawing:
- sessionId
- drawingId