package project.primattekeying;

public class Test {
    //MAIN
    public static void main(String[] args){
        PrimatteKeying pk = new PrimatteKeying();
        final String pathSrc = "sources/";
        final String pathDst = "results/";
        int R = 150;
        int G = 150;
        int B = 150;
        int R1 = 190;
        int R2 = 200;
        //double alpha = 1;

        pk.load(pathSrc + "sample-fg.jpg", pathSrc + "sample-bg.jpg");
        pk.setK(R, G, B);
        pk.setR1(R1);
        pk.setR2(R2);
        //pk.setAlpha(alpha);

        try{
            pk.perform();
            pk.showAll();
            pk.save(pathDst + "primatte-key-R" + R + "G" + G + "B" + B + "-R1-" + R1 + "-R2-" + R2 + "-result.jpg");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
