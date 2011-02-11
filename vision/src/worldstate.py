import worldstate_pb2
import threading
import time

class WorldState(object):
    
    ball = {
        "position": {
            "x": 0,
            "y": 0
        },
        "velocity": {
            "direction": 0,
            "magnitude": 0
        }
    }

    blue = {
        "position": {
            "x": 0,
            "y": 0
        },
        "velocity": {
            "direction": 0,
            "magnitude": 0
        },
        "rotation": 0
    }

    yellow = {
        "position": {
            "x": 0,
            "y": 0
        },
        "velocity": {
            "direction": 0,
            "magnitude": 0
        },
        "rotation": 0
    }

    lock = threading.Lock()

    @staticmethod
    def serialise():
        response = worldstate_pb2.WorldStateResponse()

        WorldState.lock.acquire()

        # OH GOD, THE HUGE MANATEE.
	response.changed = True
	response.timestamp = int(time.time()*1000)
        response.ball.position.x = WorldState.ball["position"]["x"]
        response.ball.position.y = WorldState.ball["position"]["y"]
        response.ball.velocity.direction = WorldState.ball["velocity"]["direction"]
        response.ball.velocity.magnitude = WorldState.ball["velocity"]["magnitude"]
 
        response.blue.position.x = WorldState.blue["position"]["x"]
        response.blue.position.y = WorldState.blue["position"]["y"]
        response.blue.velocity.direction = WorldState.blue["velocity"]["direction"]
        response.blue.velocity.magnitude = WorldState.blue["velocity"]["magnitude"]
        response.blue.rotation = WorldState.blue["rotation"]

        response.yellow.position.x = WorldState.yellow["position"]["x"]
        response.yellow.position.y = WorldState.yellow["position"]["y"]
        response.yellow.velocity.direction = WorldState.yellow["velocity"]["direction"]
        response.yellow.velocity.magnitude = WorldState.yellow["velocity"]["magnitude"]
        response.yellow.rotation = WorldState.yellow["rotation"]

        WorldState.lock.release()
        return response.SerializeToString()
