# Forge server

This is a Spring Boot based web service, which allows to run simulations on a headless server

## Building

Run `./build-server.sh` in the project root folder. Make sure, you have all project prerequisites installed.

## Running

You can run server either directly on host:

`java -jar forge-server/target/forge-server-1.6.54-SNAPSHOT-jar-with-dependencies.jar`

or inside Docker container:

`sudo docker run -it -p 8000:8000 --name forge_server forge/forge-server`


## Server communication

Forge Server has two endpoints:

* Start simulation

Sample request:

```
POST http://localhost:8000/simulation

{
  "games": 10,
  "decks": [
    "[metadata]\r\nName=GAL_6_46_Mono White Deck Generated Deck_21_19\r\n[Main]..."
  ]
}

```

Reponse will contain status and id of new simulation

```
{
    "id": 2,
    "success": true,
    "content": "Simulation started"
}
```

* Get simulation status

Sample request:

```
GET http://localhost:8000/simulation/2
```

Response will contain simulation status and logs:

```
{
    "id": 2,
    "status": "Finished",
    "error": null,
    "log": [
        "Started game # 1",
        "Mulligan: Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19 has kept a hand of 7 cards",
        "Player control: Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19 is controlled by Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19",
        "Game outcome: Turn 0",
        "Game outcome: Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19 has won because all opponents have lost",
        "Match result: Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19: 1 ",
        "Turn: Turn 1 (Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19)",
        "Phase: Ai(0)-GAL_6_46_Mono White Deck Generated Deck_21_19's Untap step",
        "Finished game # 1  elapsed time:  00:00:01.422"
    ],
    "finished": true,
    "success": true
}
```
