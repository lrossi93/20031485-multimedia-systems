package project.alphablending;

import project.Compositing;

public class AlphaBlending extends Compositing {
    //alpha parameters
    private double alphaFg;
    private double alphaBg;
    private double alpha;

    //set alpha foreground parameter
    public void setAlphaFg(double alphaFg){
        if(alphaFg >= 0 && alphaFg <= 1)
            this.alphaFg = alphaFg;
        else
            throw new IllegalArgumentException("alphaFg must be in [0, 1] (found alphaFg = " + alphaFg + "!)");
    }

    //set alpha background parameter
    public void setAlphaBg(double alphaBg){
        if(alphaBg >= 0 && alphaBg <= 1)
            this.alphaBg = alphaBg;
        else
            throw new IllegalArgumentException("alphaBg must be in [0, 1] (found alphaBg = " + alphaBg + "!)");
    }

    //calculate and get alpha'
    public double calculateAlpha(){
        this.alpha = alphaFg + (1 - alphaFg) * alphaBg;
        return getAlpha();
    }

    public double getAlpha(){
        return alpha;
    }

    //perform alpha blending
    public void perform(){
        //get if the image is colored or b/w (mine are colored)
        int bands = imageFg.getRaster().getNumBands();

        //both images have the same size - my samples are 512x512
        int h = imageFg.getHeight();
        int w = imageFg.getWidth();

        //if alpha was not calculated, calculate it
        if(alpha == 0.0)
            calculateAlpha();

        //Cycle over channels
        for(int channel = 0; channel < bands; channel++) {

            //Cycle over pixels
            for (int x = 0; x < h; x++) {
                for (int y = 0; y < w; y++) {

                    //here is where the magic happens
                    double fgPixel = imageFg.getRaster().getSample(x, y, channel);
                    double bgPixel = imageBg.getRaster().getSample(x, y, channel);

                    //calculate resulting pixel
                    double blPixel = (alphaFg * fgPixel + (1 - alphaFg) * alphaBg * bgPixel) / getAlpha();

                    //put pixel into new image
                    imageRes.getRaster().setSample(x, y, channel, blPixel);
                }
            }
        }
    }
}
