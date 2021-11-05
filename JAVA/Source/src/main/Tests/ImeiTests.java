import Server.ImeiValidation;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ImeiTests {

    @Test
    public void TestImeiValidationTrue() {

        String imei = "352094087811474";

        Assert.assertTrue(ImeiValidation.ImeiIsValid(imei));

        imei = "79927398713";

        Assert.assertTrue(ImeiValidation.ImeiIsValid(imei));
    }

    @Test
    public void TestImeiValidationFalse() {

        String imei = "799273987135461";

        Assert.assertFalse(ImeiValidation.ImeiIsValid(imei));

        imei = "35209087811474";

        Assert.assertFalse(ImeiValidation.ImeiIsValid(imei));

        imei = "352090087811474";

        Assert.assertFalse(ImeiValidation.ImeiIsValid(imei));
    }

    @Test
    public void TestFloatComma()
    {
        DecimalFormat df = new DecimalFormat("##.####");
        double lat = 546672116/0.01;
        System.out.println(BigDecimal.valueOf(546672116).movePointLeft(7));
    }

}
