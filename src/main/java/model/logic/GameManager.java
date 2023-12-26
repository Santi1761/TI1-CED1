package model.logic;

import algorithms.Algorithm;
import algorithms.PathFindingAlgorithms;
import exceptions.AlgorithmTypeMismatchException;
import exceptions.NotSuchAlgorithmException;
import model.entity.User;
import model.factory.GraphFactory;
import model.graph.Graph;
import model.graph.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static GameManager instance;
    private final Graph graph;
    private boolean isGameFinished;
    private User root;
    private User current;

    public GameManager() {
        graph = GraphFactory.createGraph(GraphFactory.GraphType.LIST);
        this.isGameFinished = false;
      
        try {
            importData();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
      
        root = null;
        current = null;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public void importData() throws FileNotFoundException, IOException, ClassNotFoundException {
        File source = new File("data/status.mc");

        if (source.exists()) {
            System.out.println("El archivo existe");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(source));
            GameManager aux = (GameManager) ois.readObject();
            ois.close();
            this.root = aux.root;
            this.current = aux.current;
        } else {
            System.out.println("El archivo no existe");
            root = null;
            current = null;
        }
    }

    public void exportData() throws IOException {
        File source = new File("src/data/status.mc");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(source));
        oos.writeObject(this);
        oos.close();
    }

    public void close() throws FileNotFoundException, IOException {
        exportData();
    }

    public void startNewGame(String username) {
        current = new User(username);
    }

    public void addUser() {
        if (root == null) {
            if (current != null) {
                root = current;
            }
        } else {
            root.add(current);
        }
    }

    public List<User> getUsersList() {
        ArrayList<User> usersList = new ArrayList<>();

        if (root != null) {
            usersList = (ArrayList<User>)root.listUsers(usersList);
            usersList.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    if (o1.getScore() > o2.getScore()) return -1;
                    else if (o1.getScore() < o2.getScore()) return 1;
                    return 0;
                }
            });

            for (int i = 0; i < usersList.size(); i++) {
                usersList.get(i).setPosition(i+1);
            }
        }
        return usersList;
    }

    public void deleteUser() {
        if (current != null) {
            if (current == root) {
                root = null;
                current = null;
            } else {
                root.remove(current);
            }
        }
    }

    public User getRoot() {
        return root;
    }

    public void setRoot(User root) {
        this.root = root;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public void setLeaderboardTV() {
    }

    public void moveCat() {
        Vertex current = graph.getCatPosition();
        if(isCatInExit(current)) {
            setGameFinished(true);
            return;
        }

        Vertex next;

        try {
            next = PathFindingAlgorithms.findPath(Algorithm.BFS, graph, current, nearestExit(current));
        } catch (AlgorithmTypeMismatchException | NotSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        graph.setCatPosition(next);
    }

    private boolean isCatInExit(Vertex v) {
        List<Vertex> exits = graph.getExits();

        for(Vertex exit : exits) {
            if(distance(v, exit) < 10) {
                return true;
            }
        }

        return false;
    }

    public Vertex nearestExit(Vertex v) {

        List<Vertex> exits = graph.getExits();

        Vertex nearest = null;
        double minDist = Double.MAX_VALUE;

        for(Vertex exit : exits) {
            double dist = distance(v, exit);
            if(dist < minDist){
                minDist = dist;
                nearest = exit;
            }
        }

        return nearest;
    }

    private double distance(Vertex a, Vertex b) {
        double xDist = a.getPolygon().getLayoutX() - b.getPolygon().getLayoutX();
        double yDist = a.getPolygon().getLayoutY() - b.getPolygon().getLayoutY();
        return Math.hypot(xDist, yDist);
    }
}
