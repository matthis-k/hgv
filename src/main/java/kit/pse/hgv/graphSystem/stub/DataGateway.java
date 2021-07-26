package kit.pse.hgv.graphSystem.stub;

import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGateway {
    public static final String PHI_REGEX = "[\\s|\\t]*<data\\skey=\"phi\">([0-9]*[.]?[0-9]+)</data>";
    public static final String RADIUS_REGEX = "[\\s|\\t]*<data\\skey=\"radius\">([0-9]*[.]?[0-9]+)</data>";
    public static final String META_REGEX = "[\\s|\\t]*<data\\skey=\"([0-9a-zA-Z]+)\">([0-9a-zA-Z.,#]+)</data>";
    public static final String NODE_REGEX = "[\\s|\\t]*<node\\sid=\"(\\d+)\">";
    public static final String EDGE_REGEX  = "[\\s|\\t]*<edge\\sid=\"[0-9]+\"\ssource=\"([0-9]+)\" target=\"([0-9]+)\">";
    public static final Pattern PHI_PATTERN = Pattern.compile(PHI_REGEX);
    public static final Pattern RADIUS_PATTERN = Pattern.compile(RADIUS_REGEX);
    public static final Pattern META_PATTERN = Pattern.compile(META_REGEX);
    public static final Pattern NODE_PATTERN = Pattern.compile(NODE_REGEX);
    public static final Pattern EDGE_PATTERN = Pattern.compile(EDGE_REGEX);
    public static final String NODE_START_FORMAT = "\n\t<node\sid=%d>";
    public static final String NODE_END_FORMAT = "\n\t</node>";
    public static final String PHI_FORMAT = "\n\t\t<data\skey=\"phi\">%f</data>";
    public static final String RADIUS_FORMAT = "\n\t\t<data\skey=\"radius\">%f</data>";
    public static final String METADATA_FORMAT = "\n\t\t<data\skey=\"%s\">%s</data>";
    public static final String EDGE_START_FORMAT = "\n\t<edge\sid=\"%d\"\ssource=\"%d\"\starget=\"%d\">";
    public static final String EDGE_END_FORMAT = "\n\t</edge>";
    public static final String GRAPH_START_FORMAT = "\n\n\t<graph\sedgedefault=\"%s\">";
    public static final String GRAPH_END_FORMAT = "\n\t</graph>";
    public static final String KEY_FORMAT = "\n\s\s<key\s id=\"%s\"\sfor=\"%s\"\sattr.name=\"%s\"\sattr.type=\"%s\"/>";
    public static final String PHI = "phi";
    public static final String RADIUS = "radius";
    public static final String XML_ENCODING = "\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String SCHEMA_REFERENCE = "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" +
            "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "  xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n" +
            "  http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n";
    public static GraphSystem graphSystem = GraphSystem.getInstance();
    public static BidiMap<Integer,Integer> nodeIDs = new DualHashBidiMap<>();
    public static Scanner scanner;
    public static void loadGraph(String path, int graphID) throws IllegalFormatException, FileNotFoundException, OverflowException {
        //TODO: Correctly implemnt!
        path.endsWith(".graphml");
        File file = new File(path);
        scanner = new Scanner(file);
        String currentLine = scanner.nextLine();
        while(!currentLine.matches("[\\s|\\t]*<graph\\sedgedefault=\"[a-zA-Z]+\">") && scanner.hasNext()) {
            currentLine = scanner.nextLine();
        }
        currentLine = scanner.nextLine();
        while(!currentLine.matches("[\\s|\\t]*</graph>") && scanner.hasNext()) {
            if(isNewNode(currentLine)) {
                readNode(currentLine, graphID);
            } else {
                readEdge(currentLine, graphID);
            }
            currentLine = scanner.nextLine();
        }
        nodeIDs.clear();
    }

    private static boolean isNewNode(String line) {
        return line.matches(NODE_REGEX);
    }

    private static void readNode(String NodeLine, int graphID) throws OverflowException {
        Matcher matcher = NODE_PATTERN.matcher(NodeLine);
        int graphMLID = 0;
        if(matcher.find()) {
             graphMLID = Integer.parseInt(matcher.group(1));
        }
        String currentLine = scanner.nextLine();
        double phi = 0;
        double radius = 0;
        HashMap<String,String> metadata = new HashMap<>();
        while(scanner.hasNext() && !currentLine.matches("[\\s|\\t]*</node>")) {
            if(currentLine.matches(PHI_REGEX)) {
                matcher = PHI_PATTERN.matcher(currentLine);
                if(matcher.find()) {
                    phi = Double.parseDouble(matcher.group(1));
                }
            } else if(currentLine.matches(RADIUS_REGEX)) {
                matcher = RADIUS_PATTERN.matcher(currentLine);
                if(matcher.find()) {
                    radius = Double.parseDouble(matcher.group(1));
                }
            } else if(currentLine.matches(META_REGEX)) {
                    matcher = META_PATTERN.matcher(currentLine);
                if(matcher.find()) {
                    metadata.put(matcher.group(1), matcher.group(2));
                }
            } else {
                throw new UnknownFormatConversionException("File does not follow the demanded pattern");
            }
            currentLine = scanner.nextLine();
        }
        Coordinate coord = new PolarCoordinate(phi, radius);
        int nodeID = graphSystem.addElement(graphID, coord);
        Node node = graphSystem.getNodeByID(graphID, nodeID);
        for(String s : metadata.keySet()) {
            node.setMetadata(s, metadata.get(s));
        }
        nodeIDs.put(graphMLID, nodeID);
    }

    private static void readEdge(String EdgeLine, int graphID) throws OverflowException {
        Matcher matcher = EDGE_PATTERN.matcher(EdgeLine);
        String currentLine = scanner.nextLine();
        int[] nodes = new int[2];
        if(matcher.find()) {
            nodes[0] = nodeIDs.get(Integer.parseInt(matcher.group(1)));
            nodes[1] = nodeIDs.get(Integer.parseInt(matcher.group(2)));
        }
        int edgeID = graphSystem.addElement(graphID, nodes);
        HashMap<String,String> metadata = new HashMap<>();
        while(scanner.hasNext() && !currentLine.matches("[\\s|\\t]*</edge>")) {
            if(currentLine.matches(META_REGEX)) {
                matcher = META_PATTERN.matcher(currentLine);
                if(matcher.find()) {
                    metadata.put(matcher.group(1), matcher.group(2));
                }
            } else {
                throw new UnknownFormatConversionException("File does not follow the demanded pattern");
            }
            currentLine = scanner.nextLine();
        }
        Edge edge = graphSystem.getEdgeByID(graphID, edgeID);
        for(String s : metadata.keySet()) {
            edge.setMetadata(s, metadata.get(s));
        }
    }

    public static boolean safeGraph(int graphId, String path) throws IOException {
        File file = new File(path);
        //try {
        //    file.createNewFile();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        bufferedWriter.close();
        fileWriter.close();
        fileWriter = new FileWriter(file, true);
        bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.append(XML_ENCODING);
        bufferedWriter.append(SCHEMA_REFERENCE);

        bufferedWriter.append(String.format(KEY_FORMAT,PHI,"node",PHI,"Double"));
        bufferedWriter.append(String.format(KEY_FORMAT,RADIUS,"node",RADIUS,"Double"));

        for(String s : graphSystem.getAllMetadataByID(graphId)) {
            bufferedWriter.append(String.format(KEY_FORMAT,s,"all",s,"String"));
        }
        bufferedWriter.append(GRAPH_START_FORMAT);

        int id = 0;
        nodeIDs.clear();
        for(Node node : graphSystem.getGraphByID(graphId).getNodes()) {
            nodeIDs.put(id,node.getId());
            PolarCoordinate coord = node.getCoord().toPolar();
            bufferedWriter.append(String.format(NODE_START_FORMAT,id));
            bufferedWriter.append(String.format(PHI_FORMAT,coord.getAngle()));
            bufferedWriter.append(String.format(RADIUS_FORMAT,coord.getDistance()));
            for(String s : node.getAllMetadata()) {
                bufferedWriter.append(String.format(METADATA_FORMAT,s,node.getMetadata(s)));
            }
            bufferedWriter.append(String.format(NODE_END_FORMAT));
            id++;
        }
        id = 0;
        for(Edge edge : graphSystem.getGraphByID(graphId).getEdges()) {
            Node tempNodes[] = edge.getNodes();
            int tempIds[] = new int[2];
            tempIds[0] = nodeIDs.getKey(tempNodes[0].getId());
            tempIds[1] = nodeIDs.getKey(tempNodes[1].getId());
            bufferedWriter.append(String.format(EDGE_START_FORMAT,id,tempIds[0],tempIds[1]));
            for(String s : edge.getAllMetadata()) {
                bufferedWriter.append(String.format(METADATA_FORMAT,s,edge.getMetadata(s)));
            }
            bufferedWriter.append(String.format(EDGE_END_FORMAT));
        }

        bufferedWriter.append(String.format(GRAPH_END_FORMAT));
        bufferedWriter.append(String.format("\n</graphml>"));
        bufferedWriter.flush();
        System.out.println("Flushed stream");
        bufferedWriter.close();
        return true;
    }

}
