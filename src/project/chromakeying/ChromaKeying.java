package project.chromakeying;

import project.Compositing;

public class ChromaKeying extends Compositing {
    private String key;

    //calculate alpha according to the provided key
    private double alpha(int r, int g, int b, String key) {
        if (key.equals("green")){
            if(g > r && g > b)
                return 1 - (((double) g) / 255 - Math.max(((double) r) / 255, ((double) b) / 255));
            else return 1;
        }
        if(key.equals("blue")) {
            if(b > r && b > g)
                return 1 - (((double) b) / 255 - Math.max(((double) r) / 255, ((double) g) / 255));
            else return 1;
        }
        else
            throw new IllegalArgumentException("Key must be either 'green' or 'blue' (found " + key + ")!");
    }

    public void setKey(String key){
        if(key.equals("green") || key.equals("blue"))
            this.key = key;
        else
            throw new IllegalArgumentException("Key must be either 'green' or 'blue' (found " + key + ")!");
    }

    //key must be "green" or "blue"
    public void perform(){
        //get if the image is colored or b/w (mine are colored)
        int bands = imageFg.getRaster().getNumBands();

        //both images have the same size - my samples are 512x512
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

                    //get all samples from current foreground pixel for alpha calculations
                    int rF = imageFg.getRaster().getSample(x, y, R);
                    int gF = imageFg.getRaster().getSample(x, y, G);
                    int bF = imageFg.getRaster().getSample(x, y, B);

                    //calculate alpha and perform chroma key on foreground pixel
                    double alpha = alpha(rF, gF, bF, key);
                    int C = (int)(alpha * fg + (1 - alpha) * bg);

                    checkColorRange(C);
                    imageRes.getRaster().setSample(x, y, channel, C);
                }
            }
        }
    }
}
