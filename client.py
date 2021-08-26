import socket
import time
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host ="localhost"
port =12345
s.connect((host,port))
while 1==1:
    s.send('{type: "MoveNode", id: 1, coordinate: {phi: 0, r: 3} }\n'.encode())
    s.send('{type: "Render", graphId: 1}\n'.encode())
    time.sleep(1)
    s.send('{type: "MoveNode", id: 1, coordinate: {phi: 0, r: 5} }\n'.encode())
    s.send('{type: "Render", graphId: 1}\n'.encode())
    time.sleep(1)
s.close ()