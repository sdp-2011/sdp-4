import worldstate_pb2
import threading

class WorldState(object):
    
    ball = {
	"position" : {
	    "x": 0,
	    "y": 0
	},
	"velocity" : {
	    "direction": 0,
	    "magnitude": 0
	}
    }

    blue = {
	"position" : {
	    "x": 0,
	    "y": 0
	},
	"velocity" : {
	    "direction": 0,
	    "magnitude": 0
	},
	"rotation" : 0
    }

    yellow = {
	"position" : {
	    "x": 0,
	    "y": 0
	},
	"velocity" : {
	    "direction": 0,
	    "magnitude": 0
	},
	"rotation" : 0
    }

    lock = threading.Lock()
