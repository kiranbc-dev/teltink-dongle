package Server;

public class ImeiValidation {


    public static boolean ImeiIsValid(String imei)
    {
        long n = Long.parseLong(imei);
        int d, sum = 0;
        for(int i=15; i>=1; i--)
        {
            d = (int)(n%10);

            if(i%2 == 0)
            {
                d = 2*d; // Doubling every alternate digit
            }

            sum = sum + sumDig(d); // Finding sum of the digits

            n = n/10;
        }

        System.out.println("Output : Sum = "+sum);

        return sum % 10 == 0;
    }

    private static int sumDig(int n) // Function for finding and returning sum of digits of a number
    {
        int a = 0;
        while(n>0)
        {
            a = a + n%10;
            n = n/10;
        }
        return a;
    }

}
