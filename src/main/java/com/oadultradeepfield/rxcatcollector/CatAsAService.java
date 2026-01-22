package com.oadultradeepfield.rxcatcollector;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatAsAService {
    /**
     * The base URL for the Cat as a Service API.
     */
    private static final String BASE_URL = "https://cataas.com";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    /**
     * Retrieves a random cat payload with the specified tag. It returns a cold observable,
     * meaning that the network request is made only when the observer subscribes.
     *
     * @param tag The tag to filter cats by.
     * @return An Observable emitting a CatPayload object.
     */
    public Observable<CatPayload> getRandomCatPayload(String tag) {
        return Observable.fromCallable(() -> {
            System.out.println("[Network IO] Finding random cat with tag: '" + tag + "' cat...");

            String url = BASE_URL + "/cat/" + tag + "?json=true";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new CatNotFoundException.ByTag(tag);
                }
                return gson.fromJson(response.body().string(), CatPayload.class);
            }
        });
    }

    /**
     * Downloads the image of a cat with the specified ID. It returns a cold observable,
     * meaning that the network request is made only when the observer subscribes.
     *
     * @param catId The unique identifier of the cat.
     * @return An Observable emitting a byte array representing the cat image.
     */
    public Observable<byte[]> downloadCatImage(String catId) {
        return Observable.fromCallable(() -> {
            System.out.println("[Network IO] Downloading cat image with ID: '" + catId + "'...");

            String url = BASE_URL + "/cat/" + catId;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new CatNotFoundException.ById(catId);
                }
                return response.body().bytes();
            }
        });
    }
}
