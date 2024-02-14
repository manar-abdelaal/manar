public class AirParticles {
    
    public static double findHighestLevel (double[] communities) {
        double max = communities[0];
        for (int i =0; i<communities.length;i++){
            if (communities[i]>max){
                max = communities[i];
            }
        }
        return max;
    }
    public static void main (String[] args) {

        int  numberOfLines = StdIn.readInt();  
        double[] pm2Levels = new double[numberOfLines];
        
        int i = 0;                            

        while ( i < numberOfLines ) {
            pm2Levels[i] = StdIn.readDouble(); 
            i += 1;
        }
        double highestLevel = findHighestLevel(pm2Levels);   

        StdOut.println("Highest PM2.5 level is " + highestLevel);
    }
}