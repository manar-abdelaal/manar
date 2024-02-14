public class DogWalker {
    public static void main (String[] args) {
        int n; 
        int x = 0, y = 0; 
        double rand, squaredDistance = 0;  
        n = Integer.parseInt (args[0]);
        System.out.println ("(0,0)");
        for(int i = 0; i < n; i++){
            rand = Math.random();
            if (rand < 0.25 ){
                x=x+1;
            } else if ( rand >= 0.25 && rand < 0.5 ) {
                x= x-1;
            } else if (rand >= 0.5 && rand < 0.75){
                y=y+1;
            } else if (rand >= 0.75){
                y = y-1;
            }
            System.out.println("("+x+","+y+")");
        }
        squaredDistance = x*x + y*y;
        System.out.print("Squared Distance = " + squaredDistance + " ");
    }
}