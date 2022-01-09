package project.chromakeying;

public class Test {
    //MAIN
    public static void main(String[] args){
        ChromaKeying ck = new ChromaKeying();
        final String pathSrc = "sources/";
        final String pathDst = "results/";
        final String key = "blue";

        ck.load(pathSrc + "sample-fg-blue.jpg", pathSrc + "sample-bg.jpg");
        ck.setKey(key);
        ck.perform();
        ck.save(pathDst + "chroma-key-" + key + "-result.jpg");
        ck.showAll();
    }
}
