package model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class User implements Comparable<User>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final static boolean CORRECT = true;

    private String name;
    private int score;
    private int position;

    private User parent, left, right;

    public User(String name, int score, int position) {
        this.name = name;
        this.score = score;
        this.position = position;
    }

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public User(String name) {
        this.name = name;
    }

    public void answer(boolean flag) {
        if (flag == CORRECT) {
            this.score += 10;
        } else {
            this.score -= 10;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getLeft() {
        return left;
    }

    public void setLeft(User left) {
        this.left = left;
    }

    public User getRight() {
        return right;
    }

    public void setRight(User right) {
        this.right = right;
    }

    @Override
    public int compareTo(User o) {
        int v = this.name.compareTo(o.name);
        if (v == 0) {
            return Integer.compare(score, o.score);
        } else return v;
    }

    public void add(User o) {
        if (compareTo(o) > 0) {
            if (left == null) {
                left = o;
                o.parent = this;
            } else {
                left.add(o);
            }
        } else if (compareTo(o)< 0) {
            if (right == null) {
                right = o;
                o.parent = this;
            } else {
                right.add(o);
            }
        }
    }

    public void remove(User o) {
        if (o == this) {
            remove();
        } else if (compareTo(o) > 0) {
            right.remove(o);
        } else {
            left.remove(o);
        }
    }

    private void remove() {
        User s = getSuccessor();

        if (s.parent != null) {
            if (s.parent.left == s) s.parent.left = null;
            else s.parent.right = null;
        }

        s.parent = parent;
        s.left = left;
        s.right = right;

        if(parent != null) {
            if (parent.left == this) parent.left = s;
            else parent.right = s;
        }
    }

    private User getSuccessor() {
        if (left == null) return right;
        else return left.getMax();
    }

    private User getMax() {
        if (right == null) return this;
        else return right.getMax();
    }

    public User search(User o) {
        int dif = compareTo(o);
        if ( dif == 0) return this;
        else if (dif > 0) return left.search(o);
        else return right.search(o);
    }

    public List<User> listUsers(List<User> l){
        if (left == null && right == null) {
            l.add(this);
        } else {
            if (right != null ) l = right.listUsers(l);
            l.add(this);
            if (left != null) l = left.listUsers(l);

        }
        return l;
    }
}
