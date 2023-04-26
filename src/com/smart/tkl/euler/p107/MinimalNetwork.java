package com.smart.tkl.euler.p107;

import com.smart.tkl.lib.graph.standard.StandardVertex;
import com.smart.tkl.lib.graph.standard.UndirectedEdge;
import com.smart.tkl.lib.graph.standard.UndirectedGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class MinimalNetwork {

    private static final String[][] EXAMPLE_GRAPH =  {
            {"-", "16",  "12",  "21", "-",  "-", "-"},
            {"16", "-",  "-",   "17", "20", "-", "-"},
            {"12", "-",  "-",   "28", "-",  "31", "-"},
            {"12", "17", "28", "-",  "18", "19", "23"},
            {"-",  "20", "-",  "18", "-", "-", "11"},
            {"-", "-",  "31", "19", "-", "-", "27"},
            {"-", "-", "-", "23", "11", "27", "-"}
    };

    private static final String FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p107\\p107_network.txt";

    private final UndirectedGraph graph;

    public static void main(String[] args) throws Exception {
        UndirectedGraph graph = readGraph(EXAMPLE_GRAPH);
        MinimalNetwork minimalNetwork = new MinimalNetwork(graph);
        MinimalNetworkResult minimalNetworkResult = minimalNetwork.solve();
        System.out.println("Minimal network result for example graph: " + minimalNetworkResult);

        long time1 = System.currentTimeMillis();
        UndirectedGraph networkWith40Vertices = readGraph(FILE_PATH);
        minimalNetwork = new MinimalNetwork(networkWith40Vertices);
        minimalNetworkResult = minimalNetwork.solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Minimal network result for network with 40 vertices: " + minimalNetworkResult);
        System.out.println("The solution took ms: " + (time2 - time1));
    }

    public MinimalNetwork(UndirectedGraph undirectedGraph) {
        this.graph = undirectedGraph;
    }

    public MinimalNetworkResult solve() {
        List<UndirectedEdge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparing(UndirectedEdge::getWeight));
        BigDecimal totalWeight = edges.stream().map(UndirectedEdge::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

        DisjointVertexSet disjointVertexSet = new DisjointVertexSet(graph.getVertices());

        BigDecimal minimalWeight = BigDecimal.ZERO;
        Set<UndirectedEdge> minimalEdges = new LinkedHashSet<>();
        for(UndirectedEdge edge : edges) {
            boolean joined = disjointVertexSet.join(edge.getVertex1(), edge.getVertex2());
            if(joined) {
                minimalEdges.add(edge);
                minimalWeight = minimalWeight.add(edge.getWeight());
            }
        }
        return new MinimalNetworkResult(minimalEdges, totalWeight, minimalWeight);
    }

    private static UndirectedGraph readGraph(String[][] graphData) {
        Set<UndirectedEdge> edges = new LinkedHashSet<>();
        for(int i = 0; i < graphData.length; i++) {
            for(int j = i; j < graphData.length; j++) {
                String rawData = graphData[i][j];
                if(!rawData.equals("-")) {
                    BigDecimal weight = BigDecimal.valueOf(Integer.parseInt(rawData));
                    StandardVertex vertex1 = new StandardVertex(i);
                    StandardVertex vertex2 = new StandardVertex(j);
                    edges.add(new UndirectedEdge(vertex1, vertex2, weight));
                }
            }
        }
        return new UndirectedGraph(edges);
    }

    private static UndirectedGraph readGraph(String filePath) throws IOException {
        Set<UndirectedEdge> edges = new LinkedHashSet<>();
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int i = 0;
            while (line != null) {
                String[] parts = line.split(",");
                for(int j = 0; j < parts.length; j++) {
                    String part = parts[j];
                    if(!part.equals("-")) {
                        BigDecimal weight = BigDecimal.valueOf(Integer.parseInt(part.trim()));
                        StandardVertex vertex1 = new StandardVertex(i);
                        StandardVertex vertex2 = new StandardVertex(j);
                        edges.add(new UndirectedEdge(vertex1, vertex2, weight));
                    }
                }
                line = br.readLine();
                i++;
            }
        }

        return new UndirectedGraph(edges);
    }

}
