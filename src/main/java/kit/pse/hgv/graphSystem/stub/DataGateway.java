package kit.pse.hgv.graphSystem.stub;

import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGateway {
    public static final String PHI_REGEX = "[\\s|\\t]*<data\\skey=\"phi\">([0-9]*[.]?[0-9]+)</data>";
    public static final String RADIUS_REGEX = "[\\s|\\t]*<data\\skey=\"radius\">([0-9]*[.]?[0-9]+)</data>";
    public static final String META_REGEX = "[\\s|\\t]*<data\\skey=\"([0-9a-zA-Z]+)\">([0-9a-zA-Z.,]+)</data>";
    public static final String NODE_REGEX = "[\\s|\\t]*<node\\sid=\"(\\d+)\">";
    public static final String EDGE_REGEX  = "[\\s|\\t]*<edge\\sid=\"[0-9]+\"\ssource=\"([0-9]+)\" target=\"([0-9]+)\">";
    public static final Pattern PHI_PATTERN = Pattern.compile(PHI_REGEX);
    public static final Pattern RADIUS_PATTERN = Pattern.compile(RADIUS_REGEX);
    public static final Pattern META_PATTERN = Pattern.compile(META_REGEX);
    public static final Pattern NODE_PATTERN = Pattern.compile(NODE_REGEX);
    public static final Pattern EDGE_PATTERN = Pattern.compile(EDGE_REGEX);
    public static GraphSystem graphSystem = GraphSystem.getInstance();
    public static HashMap<Integer,Integer> nodeIDs = new HashMap<>();
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
        while(!currentLine.matches("[\\s|\\t]*</graph>") && scanner.hasNext()) {
            currentLine = scanner.nextLine();
            if(isNewNode(currentLine)) {
                readNode(currentLine, graphID);
            } else {
                readEdge(currentLine, graphID);
            }
        }
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
}
