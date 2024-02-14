public class ISBN {
    public static void calculateISBN10(int[] isbn) {
        int sumOfProducts=0;
        for (int i=0;i<isbn.length;i++){
            int j = (10 - i) * isbn[i];
            sumOfProducts+=j;
        }
         int rem = sumOfProducts%11;
         int diff = 11-rem;
         int checkdigit = diff%11;
         isbn[9]=checkdigit;
    }
    public static int[] calculateISBN13(int[] shortIsbn) {
         int sumOfProducts=0;
       int[] isbn_13 = new int[13];
       isbn_13[0]=9;
       isbn_13[1]=7;
       isbn_13[2]=8;
       for (int i=0; i<shortIsbn.length;i++){
        isbn_13[i+3]=shortIsbn[i];
       }
    for (int i = 0; i<12;i++){
        if (i%2==0){
            sumOfProducts+=isbn_13[i]*1;
        } else {
            sumOfProducts+=isbn_13[i]*3;
        }
    }
         int rem = sumOfProducts%10;
         int checkdigit;
         if (rem==0){
            checkdigit=0;
         }else {
            checkdigit=10-rem;
         }
         isbn_13[12]=checkdigit;
         return isbn_13;
    } 
    public static void main(String[] args) {

        int[] isbn = new int[10];
        int[] isbn_13 = new int[13];

        int numOfBooks = StdIn.readInt(); 

        for ( int i = 0; i < numOfBooks; i++ ) {

            StdIn.readLine();                 
            String name = StdIn.readLine();   
            System.out.println(name);

            // Reads all the 9 integers
            for ( int j = 0; j < 9; j++ ) {
                isbn[j] = StdIn.readInt();    
            }

            calculateISBN10(isbn);            
            System.out.print("ISBN-10 - ");

            for ( int n : isbn ) {
                if ( n == 10 ) {
                    System.out.print("X");
                } else
                    System.out.print(n);
            }
            System.out.println();

            isbn_13 = calculateISBN13(isbn); 
            System.out.print("ISBN-13 - ");

            for ( int n : isbn_13 ) {
                if ( n == 10 ) {
                    System.out.print("0");
                } else {
                    System.out.print(n);
                }
            }
            System.out.println();
        }
    }
}