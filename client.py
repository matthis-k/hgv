import socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host ="localhost"
port =12345
s.connect((host,port))
s.send('{type: "MoveNode", id: 1, coordinate: {phi:0, r:4}}\n'.encode())
s.send('{type: "Render", graphId: 1}\n'.encode())
s.close ()