/**
*  Author: William Pegg
*  Date: 6/17/2015
*  Program: Secret
*  Description: This class will verify if a function (secret) meets the following criteria
*					secret(x+y) = secret(x) + secret(y)
*				for all prime numbers from 1-n where n is input by the user
*  Citations: The Sieve of Eratosthenes In PHP (https://www.livelywebdesign.com/blog/2012/07/the-sieve-of-eratosthenes-in-php/)
*			  PrimeSieve (http://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html)
*/
import java.util.*;

public class Secret {
    public static void main(String[] args) {
		
		/* create infinite loop for program to run continuously. 
		*  This way the user will not have to start the program again to check several values
		*/
		while(true){
			Scanner scanner = new Scanner(System.in);
			String userInput = "";
			try{
				
				System.out.print("Enter a single integer value greater than 1: ");
				int upperBound = 0;
			
				userInput = scanner.nextLine();
				upperBound = Integer.parseInt(userInput);

				if (upperBound < 2) {
					//user did not enter an int > 2
					System.out.println("Error: " + userInput + " is invalid input. A single integer value greater than 1 must be entered.");
				} else {

					// get all prime numbers up to user defined bound
					Integer[] primeNums = getPrimes(upperBound);
					//Print the users input and all of the primes from 1 to n where n is the users input
					System.out.println("\nPrimes under " + upperBound + ": " + Arrays.toString(primeNums));
					
					if (verifySecret(primeNums)) {
						//Print if all of the primes meet the secret(x+y)=secret(x)+secret(y) criteria 
						System.out.println("The function secret() is an additive function for all prime number under " + upperBound + ".");
					} else {
						//Print if 1 or more of the primes do not meet the secret(x+y)=secret(x)+secret(y) criteria 
						System.out.println("The function secret() is not an additive function for all prime number under " + upperBound + ".");
					}
				}
			} 
			// catch if the user inputs a value that is not an int
			catch(InputMismatchException e){
				System.out.println("Error: " + userInput + " is invalid input. A single integer value greater than 1 must be entered.");
			} 
			// catch if the user inputs a value that contains spaces (it is converted to String by Scanner so need to catch correct error
			catch(NumberFormatException e){
				System.out.println("Error: " + userInput + " is invalid input. A single integer value greater than 1 must be entered.");
			}
		}
    }

    /**
     * Accepts an int and will return an int.
     *
     * @param num an integer
     * @return the value passed into the method
     */
    public static int secret(int num) {
        return num;
    }


    /**
     * This Method will then get all of the prime numbers less than the uppserBound.
     *
	 * Based on logic from PrimeSieve
	 * 
     * @param upperBound the upper limit of what the user has specified
     * @return array of Intergers that are prime numbers less than the user specific upperBound
     */
    public static Integer[] getPrimes(int upperBound) {
        ArrayList<Integer> primeNums = new ArrayList<Integer>();
		// assume all integers are prime
        boolean[] isPrime = new boolean[upperBound + 1];
        for (int i = 2; i <= upperBound; i++) {
            isPrime[i] = true;
        }

        // mark non-primes <= N using Sieve of Eratosthenes
        for (int i = 2; i*i <= upperBound; i++) {
            // if i is prime, then mark multiples of i as nonprime
            if (isPrime[i]) {
                for (int j = i; i*j <= upperBound; j++) {
                    isPrime[i*j] = false;
                }
            }
        }

		int primeSize = 0;
        for (int i = 2; i <= upperBound; i++) {
            if (isPrime[i]){
            	//transfer all prime numbers into primNums
				primeNums.add(i);
				primeSize++;
			}
        }
        return primeNums.toArray(new Integer[primeSize]);
    }


    /**
     * This method checks if the additive function  secret(x+y) = secret(x) + secret(y) is met
     * for all prime numbers in the primeNums paramter. If any of the indexes in the array do 
     * not meet the criteria the method will return false.
     * <p>
	 * Based on logic found in The Sieve of Eratosthenes In PHP
	 *
     * @param primeNums an array of Integers containing all prime numbers less than the user specficied bound
     * @return true if all primary numbers meet criteria of secret(x+y) = secret(x) + secret(y). Otherwise return false. 
     */
    private static boolean verifySecret(Integer[] primeNums) {
        ArrayList<Integer> passed = new ArrayList<Integer>();

        for (Integer x : primeNums) {
            for (Integer y : primeNums) {
                int sumLeft = (x + y);
                int secretLeft = secret(sumLeft); // left side - secret(x+y)

                int secretX = secret(x);
                int secretY = secret(y);
                int secretRight = (secretX + secretY); // right side - secret(x) + secret(y)

                if (secretLeft != secretRight) {
                    return false;
                }
            }
            passed.add(x);
        }
        return true;
    }
}