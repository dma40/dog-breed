package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private HashMap<String, List<String>> cached = new HashMap<>();
    private BreedFetcher fetcher;

    public CachingBreedFetcher(BreedFetcher fetcher_) {
        this.fetcher = fetcher_;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        List<String> subbreeds;

        // return statement included so that the starter code can compile and run.
        if (cached.containsKey(breed)) {
            return cached.get(breed);
        }

        else {
            callsMade++;
            boolean success;

            try {
                subbreeds = fetcher.getSubBreeds(breed);
                success = true;
            }

            catch (BreedNotFoundException e) {
                throw new BreedNotFoundException(e.getMessage());
            }

            if (success) {
                cached.put(breed, subbreeds);
            }
        }

        return cached.get(breed);
    }

    public int getCallsMade() {
        return callsMade;
    }
}