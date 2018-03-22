package za.co.bmw.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class PrimeNumberService {
    private static final Logger LOG = Logger.getLogger(PrimeNumberService.class.getName());


    public List<Integer> calculatePrimeNumbers(int lowerNumber, int upperNumber) {
        LOG.log(Level.INFO, "calculatePrimeNumbers({0},{1})", new Object[]{lowerNumber, upperNumber});
        List<Integer> primes = new ArrayList<>();

        for (int num = lowerNumber; num <= upperNumber; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }

        return primes;
    }

    public List<Integer> calculatePrimeSieveOfEratosthenes(int lowerNumber, int upperNumber) {
        LOG.log(Level.INFO, "calculatePrimeSieveOfEratosthenes({0},{1})", new Object[]{lowerNumber, upperNumber});
        List<Integer> primes = new ArrayList<>();

        int upperBoundSquareRoot = (int) Math.sqrt(upperNumber);
        boolean[] isPrime = new boolean[upperNumber + 1];
        // initially assume that all integers are prime 
        for (int n=2; n<=upperNumber; n++){
            isPrime[n] = true;
        }
        // apply the sieve algorithm to filter all prime numbers
        for (int m = 2; m <= upperBoundSquareRoot; m++) {
            if (isPrime[m]) {
                for (int k = m*2; k <= upperNumber; k += m) {
                    isPrime[k] = false;
                }
            }
        }
        // add all prime numbers to the list 
        for (int m = lowerNumber; m <= upperNumber; m++) {
            if (isPrime[m]) {
                primes.add(m);
            }
        }
        return primes;
    }

    private boolean isPrime(int number) {
        if (number <= 0) {
            return false;
        }
        // filter out even numbers
        if (number > 2 && number % 2 == 0) {
            return false;
        }
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false; // number has a divisor other than 1 and itself
            }
        }
        return true;
    }

}
