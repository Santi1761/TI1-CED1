package model.graph;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;
    private List<Vertex> exits;
    private Vertex catPosition;

    private Graph() {
        vertices = new ArrayList<>();
        this.catPosition = null;
    }

    public Graph(List<Vertex> vertices) {
        this();
        fromVerticesList(vertices);
        setCatPosition(getVertex(61));
    }

    public Graph(int[][] matrix) {
        this();
        fromAdjacencyMatrix(matrix);
        setCatPosition(getVertex(61));
    }

    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        return vertex.neighbors();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getCatPosition() {
        return catPosition;
    }

    public void setCatPosition(Vertex catPosition) {
        this.catPosition = catPosition;
    }

    public Vertex getVertex(int id) {
        return vertices.stream()
                .filter(v -> v.id() == id)
                .findFirst()
                .orElse(null);
    }

    public void setPolygon(int id, Polygon polygon) {
        getVertex(id).setPolygon(polygon);
    }

    private void fromAdjacencyMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            Vertex vertex = getVertex(i + 1);
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    vertex.addNeighbour(getVertex(j + 1));
                }
            }
        }
    }

    private void fromVerticesList(List<Vertex> vertices) {
        this.vertices = vertices;

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            boolean topEdge = i < 11;
            boolean bottomEdge = i > 109;
            boolean leftEdge = i % 11 == 0;
            boolean rightEdge = i % 11 == 10;

            if (topEdge) {
                if (leftEdge) {
                    vertex.addNeighbour(vertices.get(i + 1));
                    vertex.addNeighbour(vertices.get(i + 11));
                } else if (rightEdge) {
                    vertex.addNeighbour(vertices.get(i - 1));
                    vertex.addNeighbour(vertices.get(i + 11));
                } else {
                    vertex.addNeighbour(vertices.get(i + 1));
                    vertex.addNeighbour(vertices.get(i - 1));
                    vertex.addNeighbour(vertices.get(i + 11));
                    vertex.addNeighbour(vertices.get(i + 10));
                }
            } else if (bottomEdge) {
                if (leftEdge) {
                    vertex.addNeighbour(vertices.get(i + 1));
                    vertex.addNeighbour(vertices.get(i - 11));
                } else if (rightEdge) {
                    vertex.addNeighbour(vertices.get(i - 1));
                    vertex.addNeighbour(vertices.get(i - 11));
                } else {
                    vertex.addNeighbour(vertices.get(i + 1));
                    vertex.addNeighbour(vertices.get(i - 1));
                    vertex.addNeighbour(vertices.get(i - 11));
                    vertex.addNeighbour(vertices.get(i - 10));
                }
            } else if (leftEdge) {
                vertex.addNeighbour(vertices.get(i + 1));
                vertex.addNeighbour(vertices.get(i + 11));
                vertex.addNeighbour(vertices.get(i - 11));
            } else if (rightEdge) {
                vertex.addNeighbour(vertices.get(i - 1));
                vertex.addNeighbour(vertices.get(i + 11));
                vertex.addNeighbour(vertices.get(i - 11));
            } else {
                vertex.addNeighbour(vertices.get(i + 10));
                vertex.addNeighbour(vertices.get(i - 10));
                vertex.addNeighbour(vertices.get(i + 1));
                vertex.addNeighbour(vertices.get(i - 1));
                vertex.addNeighbour(vertices.get(i + 11));
                vertex.addNeighbour(vertices.get(i - 11));
            }
        }
    }

    public void deleteFromNeighbours(int id) {
        for (Vertex vertex : vertices) {
            vertex.neighbors().remove(getVertex(id));
        }
    }

    public List<Vertex> getExits() {
        if (exits == null) {
            exits = new ArrayList<>();
            for (Vertex vertex : vertices) {
                if (vertex.id() % 11 == 0 || vertex.id() % 11 == 10 || vertex.id() < 11 || vertex.id() > 109) {
                    exits.add(vertex);
                }
            }
        }
        return exits;
    }
}