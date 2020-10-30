package algo;

import org.javatuples.Pair;

import java.util.HashMap;

public class RateLimit {

    HashMap<Integer,Integer> requestsAllowedMap = new HashMap<Integer, Integer>();

    HashMap<Integer, Pair<Integer,Long>> numberOfRequestsMap = new HashMap<>();

    long timeAllowedInMilliseconds;

    public RateLimit(HashMap<Integer, Integer> requestsAllowedMap,long timeAllowedInSeconds) {
        this.requestsAllowedMap = requestsAllowedMap;
        this.timeAllowedInMilliseconds = timeAllowedInSeconds * 1000;
    }

    //Function to return rate limit of requests allowed is true/false
    public boolean checkRateLimit(int customerId){
        boolean requestAllowed = false;

        if(numberOfRequestsMap.get(customerId) == null){
            numberOfRequestsMap.put(customerId, new Pair(customerId,System.currentTimeMillis()));
        }else{
            //increment count of request for each customerId
            Integer requestCount = numberOfRequestsMap.get(customerId).getValue0() + 1;
            Long StartTime = numberOfRequestsMap.get(customerId).getValue1();

            numberOfRequestsMap.put(customerId,new Pair(requestCount,StartTime));
        }

        if(!requestsAllowedMap.containsKey(customerId)){
            requestAllowed = false;
        }else{
            int totalRequestsAllowed = requestsAllowedMap.get(customerId);
            Pair<Integer,Long> pair = numberOfRequestsMap.get(customerId);

            Long timeLapsedInMilliseconds = System.currentTimeMillis() - pair.getValue1();

            if(pair.getValue0() < totalRequestsAllowed && timeLapsedInMilliseconds <= timeAllowedInMilliseconds){
                requestAllowed = true;
            }
        }
        return requestAllowed;
    }

}
