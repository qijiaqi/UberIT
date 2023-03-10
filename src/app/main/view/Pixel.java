package app.main.view;

public class Pixel implements Comparable<Pixel> {
    private int x;
    private int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Pixel p) {
        int pX = p.getX();
        int pY = p.getY();
        return (x * x + y * y - pX * pX - pY * pY);
    }
}
