package kit.pse.hgv.dataGateway;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGateway {
    public static final String PHI_REGEX = "[\\s|\\t]*<data\\skey=\"phi\">([0-9]*[.]?[0-9]+)</data>";
    public static final String RADIUS_REGEX = "[\\s|\\t]*<data\\skey=\"radius\">([0-9]*[.]?[0-9]+)</data>";
    public static final String META_REGEX = "[\\s|\\t]*<data\\skey=\"([0-9a-zA-Z]+)\">(.*)</data>";
    public static final String NODE_REGEX = "[\\s|\\t]*<node\\sid=\"(\\d+)\">";
    public static final String EDGE_REGEX  = "[\\s|\\t]*<edge\\sid=\"[0-9]+\"\\ssource=\"([0-9]+)\" target=\"([0-9]+)\">";
    public static final Pattern PHI_PATTERN = Pattern.compile(PHI_REGEX);
    public static final Pattern RADIUS_PATTERN = Pattern.compile(RADIUS_REGEX);
    public static final Pattern META_PATTERN = Pattern.compile(META_REGEX);
    public static final Pattern NODE_PATTERN = Pattern.compile(NODE_REGEX);
    public static final Pattern EDGE_PATTERN = Pattern.compile(EDGE_REGEX);
    public static final String NODE_START_FORMAT = "\n\t<node\\sid=\"%d\">";
    public static final String NODE_END_FORMAT = "\n\t</node>";
    public static final String PHI_FORMAT = "\n\t\t<data\\skey=\"phi\">%f</data>";
    public static final String RADIUS_FORMAT = "\n\t\t<data\\skey=\"radius\">%f</data>";
    public static final String METADATA_FORMAT = "\n\t\t<data\\skey=\"%s\">%s</data>";
    public static final String EDGE_START_FORMAT = "\n\t<edge\\sid=\"%d\"\\ssource=\"%d\"\\starget=\"%d\">";
    public static final String EDGE_END_FORMAT = "\n\t</edge>";
    public static final String GRAPH_START_FORMAT = "\n\n\t<graph\\sedgedefault=\"undirected\">";
    public static final String GRAPH_END_FORMAT = "\n\t</graph>";
    public static final String KEY_FORMAT = "\n\\s\\s<key\\s id=\"%s\"\\sfor=\"%s\"\\sattr.name=\"%s\"\\sattr.type=\"%s\"/>";
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
    private static Locale LOCALE = Locale.ENGLISH;

    /**
     * Adds the last opened Graph into a file
     * @param path path of the last opened Graph
     */
    public static void addlastOpened(String path){
        File lastOpenedFile = new File("src/main/resources/lastOpenedFile.txt");
        try{
            FileWriter writer = new FileWriter(lastOpenedFile, true);
            writer.write(path);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //TODO
        }
    }

    /**
     * Returns the last five opened Graphs
     *
     * @return List of the five last opened Graphs
     */
    public static List<String> getlastOpenedGraphs(){
        List<String> lastOpened = new ArrayList<>();
        File lastOpenedFile = new File("src/main/resources/lastOpenedFile.txt");
        if(lastOpenedFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(lastOpenedFile));
                String path;
                path = reader.readLine();
                while (lastOpened.size() < 5 && path != null) {
                    for(int i = 0; i < lastOpened.size(); i++) {
                        if (lastOpened.get(i).equals(path)){
                            path = "";
                        }
                    }
                    if (!(path == null) && !path.equals("")){
                        lastOpened.add(path);
                    }
                    path = reader.readLine();
                }
                reader.close();
            } catch (IOException e){
                //TODO
            }
        }
        return lastOpened;
    }

    /**
     * Loads a Graph from a file
     *
     * @param path path of the Graph file
     * @param graphID which graphID
     * @throws IllegalFormatException if the File doesn't have the right format
     * @throws FileNotFoundException if the File is non-existent
     * @throws OverflowException if there are too many added ids
     */
    public static void loadGraph(String path, int graphID) throws IllegalFormatException, FileNotFoundException, OverflowException {
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

    /**
     * Checks if the new Element is a new Node
     *
     * @param line new Element
     * @return true, if the new Element is a node, false if it isn't
     */
    private static boolean isNewNode(String line) {
        return line.matches(NODE_REGEX);
    }

    /**
     * Reads the Graphml-String and converts it into a node
     *
     * @param NodeLine new Node as a graphml String
     * @param graphID given Graph
     * @throws OverflowException if there are too many added ids
     */
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

    /**
     * Reads the Graphml-String and converts it into an edge
     *
     * @param currentLine new Edge as String
     * @param graphID given Graph
     * @throws OverflowException if there are too many added ids
     */
    private static void readEdge(String currentLine, int graphID) throws OverflowException {
        Matcher matcher = EDGE_PATTERN.matcher(currentLine);
        if (!matcher.matches()) {
            return;
        }
        int[] nodes = {0,0};
        matcher.reset();
        boolean found = matcher.find();
        if(found) {
            nodes[0] = nodeIDs.get(Integer.parseInt(matcher.group(1)));
            nodes[1] = nodeIDs.get(Integer.parseInt(matcher.group(2)));
        }
        int edgeID = graphSystem.addElement(graphID, nodes);
        HashMap<String,String> metadata = new HashMap<>();
        currentLine = scanner.nextLine();
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

    /**
     * Saves a Graph into a file
     *
     * @param graphId given graph
     * @param path path where to save
     * @return if the save was successful
     * @throws IOException if writing on the file failed
     */
    public static boolean saveGraph(int graphId, String path) throws IOException {
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

        bufferedWriter.append(String.format(LOCALE, KEY_FORMAT,PHI,"node",PHI,"Double"));
        bufferedWriter.append(String.format(LOCALE, KEY_FORMAT,RADIUS,"node",RADIUS,"Double"));

        for(String s : graphSystem.getAllMetadataByID(graphId)) {
            bufferedWriter.append(String.format(LOCALE, KEY_FORMAT,s,"all",s,"String"));
        }
        bufferedWriter.append(GRAPH_START_FORMAT);

        int id = 0;
        nodeIDs.clear();
        for(Node node : graphSystem.getGraphByID(graphId).getNodes()) {
            nodeIDs.put(id,node.getId());
            PolarCoordinate coord = node.getCoord().toPolar();
            bufferedWriter.append(String.format(LOCALE, NODE_START_FORMAT,id));
            bufferedWriter.append(String.format(LOCALE, PHI_FORMAT,coord.getAngle()));
            bufferedWriter.append(String.format(LOCALE, RADIUS_FORMAT,coord.getDistance()));
            for(String s : node.getAllMetadata()) {
                bufferedWriter.append(String.format(LOCALE, METADATA_FORMAT,s,node.getMetadata(s)));
            }
            bufferedWriter.append(String.format(LOCALE, NODE_END_FORMAT));
            id++;
        }
        id = 0;
        for(Edge edge : graphSystem.getGraphByID(graphId).getEdges()) {
            Node tempNodes[] = edge.getNodes();
            int tempIds[] = new int[2];
            tempIds[0] = nodeIDs.getKey(tempNodes[0].getId());
            tempIds[1] = nodeIDs.getKey(tempNodes[1].getId());
            bufferedWriter.append(String.format(LOCALE, EDGE_START_FORMAT,id,tempIds[0],tempIds[1]));
            for(String s : edge.getAllMetadata()) {
                bufferedWriter.append(String.format(LOCALE, METADATA_FORMAT,s,edge.getMetadata(s)));
            }
            bufferedWriter.append(String.format(LOCALE, EDGE_END_FORMAT));
            id++;
        }

        bufferedWriter.append(String.format(LOCALE, GRAPH_END_FORMAT));
        bufferedWriter.append(String.format(LOCALE, "\n</graphml>"));
        bufferedWriter.flush();
        bufferedWriter.close();
        return true;
    }
}
