package Grains.model;

/**
 * Created by Kamil on 2018-05-31.
 */
public class Point {
    public int x;
    public int y;

    Point(int a, int b) {
        this.x = a;
        this.y = b;
    }

    public boolean equals(Point p) {
        if (this.x==p.x && this.y==p.y) return true;
        return false;
    }

    public boolean inR(Point p, int r) {
        if (Math.abs(p.x-this.x)<r && Math.abs(p.y-this.y)<r) return true;
        return false;
    }
}
