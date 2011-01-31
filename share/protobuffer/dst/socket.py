# Echo server program
import socket
import os

s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
try:
    os.remove("/tmp/socketname")
except OSError:
    pass
s.bind("/tmp/socketname")
s.listen(1)
conn, addr = s.accept()
while 1:
    conn.send(data)
conn.close()
