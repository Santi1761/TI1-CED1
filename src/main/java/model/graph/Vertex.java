package model.graph;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private final int id;
    private boolean isClosed;
    private boolean visited;
    private List<Vertex> neighbors;
    private Polygon polygon;


    public Vertex(int id) {
        neighbors = new ArrayList<>();
        this.id = id;
    }

    public int id() {
        return id;
    }

    public List<Vertex> neighbors() {
        return neighbors;
    }

    public void addNeighbour(Vertex vertex) {
        neighbors.add(vertex);
    }

    public void setNeighbors(List<Vertex> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
