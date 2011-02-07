import worldstate_pb2
import threading

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

    @classmethod
    def serialise():
        response = worldstate_pb2.WorldStateResponse()

        lock.acquire()

        # OH GOD, THE HUGE MANATEE.
        response.ball.position.x = ball["position"]["x"]
        response.ball.position.y = ball["position"]["y"]
        response.ball.velocity.direction = ball["velocity"]["direction"]
        response.ball.velocity.magnitude = ball["velocity"]["magnitude"]
 
        response.blue.position.x = blue["position"]["x"]
        response.blue.position.y = blue["position"]["y"]
        response.blue.velocity.direction = blue["velocity"]["direction"]
        response.blue.velocity.magnitude = blue["velocity"]["magnitude"]
        response.blue.rotation = blue["rotation"]

        response.yellow.position.x = yellow["position"]["x"]
        response.yellow.position.y = yellow["position"]["y"]
        response.yellow.velocity.direction = yellow["velocity"]["direction"]
        response.yellow.velocity.magnitude = yellow["velocity"]["magnitude"]
        response.yellow.rotation = yellow["rotation"]

        lock.release()
        return response.SerializeToString()
