import socket
import time

class HGV:
    host ="localhost"
    port =12345

    def __init__(self):
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.connect((self.host,self.port))

    def sendCommand(self, cmd):
        self.s.send((cmd + '\n').encode())
        #print(self.getResponse())

    def getResponse(self):
        return self.s.makefile().readline() 

    def moveNodeCmd(self, nodeId, phi, r):
        return '{type: "MoveNode", id: ' + str(nodeId) + ', coordinate: { phi: ' + str(phi) + ', r: ' + str(r) + '} }'
    def moveNode(self, nodeId, phi, r):
        self.sendCommand(self.moveNodeCmd(nodeId, phi, r))
    def renderCmd(self, graphId):
        return '{type: "Render", graphId: ' + str(graphId) + '}n'
    def render(self, graphId):
        self.sendCommand(self.renderCmd(graphId))
    def editMetaCmd(self, nodeId, key, val):
        return '{type: "ChangeMetadata", nodeId: ' + str(nodeId) + ', key: ' + key + ', val: ' + val + '}'
    def editMeta(self, nodeId, key, val):
        self.sendCommand(self.editMetaCmd(nodeId, key, meta))
    def createNodeCmd(self, graphId, phi, r):
        return '{type: "CreateNode", graphId: ' + str(graphId) + ', coordinate: { phi: ' + str(phi) + ', r: ' + str(r) + '} }'
    def createNode(self, graphId, phi, r):
        self.sendCommand(self.createNodeCmd(graphId, phi, r))
    def createEdgeCmd(self, graphId, id1, id2):
        return '{type: "CreateEdge", graphId: ' + str(graphId) + ', id1: ' + str(id1) + ', id2: ' + str(id2) + ' }'
    def createEdge(self, graphId, id1, id2):
        self.sendCommand(self.createEdgeCmd(graphId, id1, id2))
    def delElCmd(self, elId):
        return '{type: "DeleteElement", id: ' + str(elId) + '}'
    def delEl(self, elId):
        self.sendCommand(self.delElCmd(elId))
    def compositeCmd(self, cmds):
        cmd = '{type: "CommandComposite", commands: ['
        for i in range(len(cmds)):
            cmd = cmd + c
            if i != len(cmds) - 1:
                cmd = cmd + ', '        
        cmd = cmd + '] }'
        return cmd
    def composite(self, cmds):
        self.sendCommand(self.compositeCmd(cmds))
    def moveCenterCmd(self, phi, r):
        return '{type: "MoveCenter", coordinate: { phi: ' + str(phi) + ', r: ' + str(r) + '} }'
    def moveCenter(self, phi, r):
        self.sendCommand(self.moveNodeCmd(phi, r))
    def getGraphCmd(self, graphId):
        return '{type: "GetGraph", graphId: ' + str(graphId) + '}'
    def getGraph(self, graphId):
        self.sendCommand(self.getGraphCmd(graphId))
    def pauseCmd(self):
        return '{type: "PauseExtension"}'
    def pause(self):
        self.sendCommand(self.pauseCmd())
    def stopCmd(self):
        return '{type: "StopExtension"}'
    def stop(self):
        self.sendCommand(self.stopCmd())

        


hgv = HGV()


while 1==1:
    hgv.moveNode(1, 0, 3)
    hgv.render(1)
    time.sleep(1)
    hgv.moveNode(1, 0, 5)
    hgv.render(1)
    time.sleep(1)
s.close ()
