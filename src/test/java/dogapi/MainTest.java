package dogapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void getNumberOfSubBreedsValidBreed() throws BreedFetcher.BreedNotFoundException {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        try {
            assertEquals(2, Main.getNumberOfSubBreeds("hound", mock));
        }

        catch (BreedFetcher.BreedNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void getNumberOfSubBreedsInvalidBreed() throws BreedFetcher.BreedNotFoundException {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        try {
            assertEquals(0, Main.getNumberOfSubBreeds("cat", mock));
        }

        catch (BreedFetcher.BreedNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void exceptionTypeTest() {
        Exception bfe = new BreedFetcher.BreedNotFoundException("hound");
        assertTrue(bfe instanceof Exception && !(bfe instanceof RuntimeException),
                "BreedFetcher.BreedNotFoundException must be a checked exception.");
    }
}