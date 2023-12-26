package model.factory;

import model.graph.Graph;
import model.graph.Vertex;

import java.util.List;
import java.util.stream.IntStream;

public class GraphFactory {

    public enum GraphType {
        LIST, MATRIX
    }

    private static final int SIZE = 121;

    public static Graph createGraph(GraphType type) {
        return switch (type) {
            case LIST -> new Graph(listGraphTemplate());
            case MATRIX -> new Graph(matrixGraphTemplate());
        };
    }

    private static List<Vertex> listGraphTemplate() {
        return IntStream.range(0, SIZE)
                .mapToObj(i -> new Vertex(i + 1))
                .toList();
    }

    //TODO finish adjacency matrix
    private static int[][] matrixGraphTemplate() {
        int[][] matrix = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 0;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            boolean topEdge = i < 11;
            boolean bottomEdge = i > 109;
            boolean leftEdge = i % 11 == 0;
            boolean rightEdge = i % 11 == 10;

            if (topEdge) {
                if(leftEdge) {
                    matrix[i][i + 1] = 1;
                    matrix[i][i + 11] = 1;
                } else if (rightEdge) {
                    matrix[i][i - 1] = 1;
                    matrix[i][i + 11] = 1;
                } else {
                    matrix[i][i + 1] = 1;
                    matrix[i][i - 1] = 1;
                    matrix[i][i + 11] = 1;
                    matrix[i][i + 10] = 1;
                }
            } else if (bottomEdge) {
                if (leftEdge) {
                    matrix[i][i + 1] = 1;
                    matrix[i][i - 11] = 1;
                } else if (rightEdge) {
                    matrix[i][i - 1] = 1;
                    matrix[i][i - 11] = 1;
                } else {
                    matrix[i][i + 1] = 1;
                    matrix[i][i - 1] = 1;
                    matrix[i][i - 11] = 1;
                    matrix[i][i - 10] = 1;
                }
            } else if (leftEdge) {
                matrix[i][i + 1] = 1;
                matrix[i][i + 11] = 1;
                matrix[i][i - 11] = 1;
            } else if (rightEdge) {
                matrix[i][i - 1] = 1;
                matrix[i][i + 11] = 1;
                matrix[i][i - 11] = 1;
            } else {
                matrix[i][i + 10] = 1;
                matrix[i][i - 10] = 1;
                matrix[i][i + 1] = 1;
                matrix[i][i - 1] = 1;
                matrix[i][i + 11] = 1;
                matrix[i][i - 11] = 1;
            }
        }

        return matrix;
    }

}