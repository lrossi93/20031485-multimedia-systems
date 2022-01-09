package project.alphablending;

public class Test {
    //MAIN
    public static void main(String[] args){
        AlphaBlending ab = new AlphaBlending();
        final String pathSrc = "sources/";
        final String pathDst = "results/";
        double alphaFg = 0.5;
        double alphaBg = 1;

        ab.load(pathSrc + "sample-1.jpg", pathSrc + "sample-2.jpg");
        ab.setAlphaFg(alphaFg);
        ab.setAlphaBg(alphaBg);
        ab.calculateAlpha();
        ab.perform();
        ab.save(pathDst + "alpha-blend-aF" + (int) (alphaFg*10) + "-aB" + (int) (alphaBg*10) + "-result.jpg");
        ab.showAll();
    }
}
