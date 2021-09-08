import sys, getopt
from decimal import Decimal
from argparse import ArgumentParser

#Dieses Skript ist zum Umwandeln eines Graphs aus girgs in eine .graphml Datei für HGV
#Zur Verwendung das Skript mit dem File Name der girgs-Dateien ohne Dateiendung 

class Node:
    def __init__(self, r, phi, id):
        self.r = Decimal(r)
        self.phi = Decimal(phi)
        self.id = int(id)

    def toString(self):
        out = "\t<node id=\"{nid}\">\n\t\t<data key=\"phi\">{nphi}</data>\n\t\t<data key=\"radius\">{nr}</data>\n\t</node>\n"
        return out.format(nid=self.id, nphi=self.phi, nr=self.r)


class Edge:
    def __init__(self, id, source, target):
        self.id = int(id)
        self.source = int(source)
        self.target = int(target)

    def toString(self):
        out = "\t<edge id=\"{id}\" source=\"{source}\" target=\"{target}\">\n</edge>\n"
        return out.format(id=self.id, source=self.source, target=self.target)


inputString = sys.argv[1]
try:
    nodes = open(inputString + ".hyp", "r")
    edges = open(inputString + ".txt", "r")
except:
    exit(0)
try:
    output = open(inputString + ".graphml", "x")
    output.close()
except:
    print("")
output = open(inputString + ".graphml", "w")

nodeList = []
edgeList = []
nodeLines = nodes.readlines()
count = 0
for line in nodeLines:
    temp = line.split(" ")
    node = Node(temp[0], temp[1], count)
    nodeList.append(node.toString())
    count = count + 1
edgeLines = edges.readlines()
edgeCount = 0
for edge in edgeLines:
    if edgeCount >= 2:
        temp = edge.split(" ")
        count = count + 1
        edge = Edge(edgeCount, temp[0], temp[1])
        edgeList.append(edge.toString())
    edgeCount = edgeCount + 1
output.write(
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\nxmlns:xsi=\"\http://www.w3.org/2001/XMLSchema-instance\"\nxsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\nhttp://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n <key id=\"phi\" for=\"node\" attr.name=\"phi\" attr.type=\"double\"/>\n<key id=\"radius\" for=\"node\" attr.name=\"radius\" attr.type=\"double\"/>\n<key id=\"color\" for=\"all\" attr.name=\"color\" attr.type=\"double\"/>\n<graph edgedefault=\"undirected\">\n")
for line in nodeList:
    output.write(line)
for line in edgeList:
    output.write(line)
output.write("</graph>")
