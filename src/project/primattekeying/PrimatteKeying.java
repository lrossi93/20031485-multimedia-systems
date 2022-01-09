package project.primattekeying;

import project.Compositing;

public class PrimatteKeying extends Compositing {
    //key color K
    private Point K; //this is the center of s1 and s2

    //keying sphere S1 radius
    private double r1;

    //tolerance sphere S2 radius
    private double r2;

    public void setR1(double r){
        r1 = r;
    }

    public void setR2(double r){
        r2 = r;
    }

    public void setK(int R, int G, int B) {
        checkColorRange(R);
        checkColorRange(G);
        checkColorRange(B);
        K = new Point(R, G, B);
    }

    public double getAlpha(int R, int G, int B){
        return 1 - (((((double)B))/255) - (Math.max((((double)R)/255), ((((double)G)/255)))));
    }

    public void perform() {
        //get if the image is colored or b/w (mine are colored)
        int bands = imageFg.getRaster().getNumBands();

        //both images have the same size
        int h = imageFg.getHeight();
        int w = imageFg.getWidth();

        //utility values
        int R = 0;
        int G = 1;
        int B = 2;

        //cycle over pixels
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                //cycle over color channels
                for (int channel = 0; channel < bands; channel++){
                    //get samples from current foreground pixel
                    int fg = imageFg.getRaster().getSample(x, y, channel);
                    int bg = imageBg.getRaster().getSample(x, y, channel);

                    //get "coordinates" of current pixel
                    int r = imageFg.getRaster().getSample(x, y, R);
                    int g = imageFg.getRaster().getSample(x, y, G);
                    int b = imageFg.getRaster().getSample(x, y, B);
                    Point P = new Point(r, g, b);

                    //distance between this pixel and the center of the spheres
                    double d = Point.distance(P, K);
                    double a;
                    if(d <= r1) {
                        //alpha == 0
                        a = 0;
                    }
                    else if (d > r1 && d <= r2) {
                        //alpha == calculated
                        a = getAlpha(r, g, b);
                    }
                    else {
                        //alpha == 1
                        a = 1;
                    }

                    int C = (int) (a * fg + (1 - a) * bg);
                    checkColorRange(C);
                    imageRes.getRaster().setSample(x, y, channel, C);
                }
            }
        }
    }

    static class Point{
        private double x, y, z;

        Point(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        static double distance(Point p1, Point p2){
            double dx = p1.x - p2.x;
            double dy = p1.y - p2.y;
            double dz = p1.z - p2.z;

            return Math.sqrt(dx*dx + dy*dy + dz*dz);
        }
    }
}
