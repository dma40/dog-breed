package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private static final String API_URL = "https://dog.ceo/api/breed";
    public static final String TOKEN = "token";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        ArrayList<String> subbreeds = new ArrayList<>();

        final OkHttpClient breed_client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
            .url(String.format("%s/%s/list", API_URL, breed))
            .addHeader(TOKEN, "token")
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .build();

            try {
                final Response response = breed_client.newCall(request).execute();
                final JSONObject responseBody = new JSONObject(response.body().string());

                if (responseBody.getString("status").equals("success")) {
                    JSONArray breeds = responseBody.getJSONArray("message");
                    int count = breeds.length();

                    for (int i = 0; i < count; i++) {
                        String breedName = breeds.getString(i);
                        subbreeds.add(breedName);
                    }
                }

                else {
                    throw new IOException(responseBody.getString("message"));
                }
            }

            catch (IOException | JSONException event) {
                throw new BreedNotFoundException(event.getMessage());
            }


        return subbreeds;
    }
}