import worldstate_pb2

env = worldstate_pb2.WorldStateResponse()

env.changed = True
env.timestamp = 1234567890

env.ball.position.x = 100
env.ball.position.y = 200
env.ball.velocity.direction = 328
env.ball.velocity.magnitude = 0

env.yellow.position.x = 100
env.yellow.position.y = 200
env.yellow.velocity.direction = 328
env.yellow.velocity.magnitude = 45.1
env.yellow.rotation = 328

env.blue.position.x = 300
env.blue.position.y = 12
env.blue.velocity.direction = 328
env.blue.velocity.magnitude = 0
env.blue.rotation = 50

import signal, os

def handler(signum, frame):
    print '\nServer exiting...'
    exit()

# Set the signal handler and a 5-second alarm
signal.signal(signal.SIGINT, handler)

import SocketServer

class WorldStateRequestHandler(SocketServer.BaseRequestHandler):
    def setup(self):
        print self.client_address, 'connected!'

    def handle(self):
        data = self.request.recv(1024)
        request = worldstate_pb2.WorldStateRequest()
        request.ParseFromString(data)
        print request.last_timestamp
        self.request.send(env.SerializeToString())

    def finish(self):
        print self.client_address, 'disconnected!'

#server host is a tuple ('host', port)
server = SocketServer.ThreadingTCPServer(('localhost', 50008), WorldStateRequestHandler)
server.serve_forever()
