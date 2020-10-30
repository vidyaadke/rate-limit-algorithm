
import algo.RateLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateLimitTest {

    RateLimit rateLimit;

    @BeforeEach
    void init(){
        HashMap<Integer, Integer> requestsAllowed = new HashMap<Integer, Integer>();
        requestsAllowed.putIfAbsent(1,4);
        requestsAllowed.putIfAbsent(2,3);
        rateLimit = new RateLimit(requestsAllowed,Long.valueOf(60));
    }


    @Test
    public void rateLimit_singlecustomer_singlerequest() {
        boolean allowed = rateLimit.checkRateLimit(1);
        assert  allowed == true;
    }

    @Test
    public void rateLimit_notexist_customer() {
        boolean allowed = rateLimit.checkRateLimit(4);
        assert  allowed == false;
    }

    @Test
    public void rateLimt_forMoreRequests(){
        rateLimit.checkRateLimit(1);
        rateLimit.checkRateLimit(1);
        rateLimit.checkRateLimit(1);
        rateLimit.checkRateLimit(1);
        boolean allowed = rateLimit.checkRateLimit(1);
        assertEquals(false,allowed);
    }

    @Test
    public void rateLimt_forMoreRequests_forMoreCustomers(){
        //for first customer
        boolean allowedFirst = rateLimit.checkRateLimit(1);
        assert allowedFirst == true;

        boolean allowedSecond = rateLimit.checkRateLimit(1);
        assert allowedSecond == true;

        boolean allowedThird = rateLimit.checkRateLimit(1);
        assert  allowedThird == true;

        boolean allowedForth = rateLimit.checkRateLimit(1);
        assert allowedForth == false;

        //for second customer
        boolean allowedFirstForSecond = rateLimit.checkRateLimit(2);
        assert allowedFirstForSecond == true;

        boolean allowedSecondForSecond = rateLimit.checkRateLimit(2);
        assert allowedSecondForSecond == false;


    }
}