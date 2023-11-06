package com.github.rhyrak;

import com.github.rhyrak.model.NISTEntry;
import com.github.rhyrak.model.Root;
import com.github.rhyrak.model.Vulnerability;
import com.github.rhyrak.sorter.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("SpellCheckingInspection")
public class Main {
    static final String baseUrl = "https://services.nvd.nist.gov/rest/json/cves/2.0/";
    static ArrayList<NISTEntry> vulnerabilities = new ArrayList<>(50000);
    // date format: https://stackoverflow.com/a/26631632
    static Gson parser = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    public static void main(String[] args) {
        loadVulnerabilities();

        Sorter<NISTEntry> Heapsorter = new HeapSort<>();
        Sorter<NISTEntry> AVLSorter = new AVLSort<>();
        Sorter<NISTEntry> InsertionSorter = new InsertionSort<>();
        Sorter<NISTEntry> QuickSorter = new QuickSort<>();
        Sorter<NISTEntry> MergeSorter = new MergeSort<>();

        NISTEntry[] copy = vulnerabilities.toArray(new NISTEntry[50000]);

        NISTEntry[] Heapcopy = copy.clone();
        NISTEntry[] AVLcopy = copy.clone();
        NISTEntry[] Insertioncopy = Arrays.copyOf(copy, 10000);
        NISTEntry[] Quickcopy = copy.clone();
        NISTEntry[] Mergecopy = copy.clone();

        long start,finish,timeSpent;

        start = System.currentTimeMillis();
        Heapsorter.sort(Heapcopy);
        finish = System.currentTimeMillis();
        timeSpent = finish - start;
        System.out.println("Heap Sorting complete. Time Spent : " + timeSpent + " ms");
        
        start = System.currentTimeMillis();
        AVLSorter.sort(AVLcopy);
        finish = System.currentTimeMillis();
        timeSpent = finish - start;
        System.out.println("AVL Sorting complete. Time Spent : " + timeSpent + " ms");
        
        start = System.currentTimeMillis();
        InsertionSorter.sort(Insertioncopy);
        finish = System.currentTimeMillis();
        timeSpent = finish - start;
        System.out.println("Insertion Sorting complete. Time Spent : " + timeSpent + " ms");
        
        start = System.currentTimeMillis();
        QuickSorter.sort(Quickcopy);
        finish = System.currentTimeMillis();
        timeSpent = finish - start;
        System.out.println("Quick Sorting complete. Time Spent : " + timeSpent + " ms");
        
        start = System.currentTimeMillis();
        MergeSorter.sort(Mergecopy);
        finish = System.currentTimeMillis();
        timeSpent = finish - start;
        System.out.println("Merge Sorting complete. Time Spent : " + timeSpent + " ms");

/*
        System.out.println(Heapcopy.length);
        System.out.println(AVLcopy.length);
        System.out.println(Insertioncopy.length);
        System.out.println(Quickcopy.length);
        System.out.println(Mergecopy.length);

        System.out.println(Arrays.equals(AVLcopy, Heapcopy));
        System.out.println(Arrays.equals(AVLcopy, Mergecopy));
        System.out.println(Arrays.equals(AVLcopy, Quickcopy));

        for(int p = 0; p < Heapcopy.length/50; p++){
            System.out.println(Heapcopy[p]);
            System.out.println(Mergecopy[p]);
            System.out.println(AVLcopy[p]);
            System.out.println(Quickcopy[p]);
            System.out.println();
        }
*/

    }

    static void loadVulnerabilities() {
        try {
            for (int i = 0; i < 50000; i += 2000) {
                boolean res = fetchData("2000", String.valueOf(i));
                for (int tries = 0; !res && tries < 3; tries++) {
                    System.out.println("[RETRYING]");
                    res = fetchData("2000", String.valueOf(i));
                }
                System.out.println();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Loaded " + vulnerabilities.size() + " entries.");
    }

    static boolean fetchData(String resultsPerPage , String startIndex) throws URISyntaxException, IOException, InterruptedException {
        String jsonData = null;
        boolean wasCached = false;
        File cachedFile = new File("cache/rpp" + resultsPerPage + "si" + startIndex + ".json");
        URI apiUri = new URI(baseUrl +
                "?resultsPerPage=" + resultsPerPage + "&startIndex=" + startIndex);

        if (cachedFile.exists()) {
            System.out.println("[HIT] Loading from disk: " + apiUri);
            jsonData = loadFromDisk(cachedFile);
            wasCached = true;
        } else {
            System.out.println("[MISS] Fetching: " + apiUri);
            jsonData = getFromAPI(apiUri);
        }

        // try parsing jsonData using gson
        try {
            Root parsed = parser.fromJson(jsonData, Root.class);
            for (Vulnerability v : parsed.getVulnerabilities())
                vulnerabilities.add(new NISTEntry(v));
            System.out.println("[SUCCESS] Entries are loaded");
        } catch (Exception e) {
            if (wasCached) {
                System.out.print("[FAILED] Deleting cached file...");
                if (cachedFile.delete())
                    System.out.println(" (deleted)");
                else
                    System.out.println(" (delete failed)");
            } else {
                if (jsonData.length() == 3) {
                    System.out.println("[FAILED] Server returned " + jsonData);
                    System.out.println("[INFO] Sleeping 10 seconds");
                    Thread.sleep(10000);
                }
                else
                    System.out.println("[FAILED] Parse error");
            }
            return false;
        }

        if (!wasCached)
            cacheBody(cachedFile.getName(), jsonData);
        return true;
    }


    private static String loadFromDisk(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return new String(fileInputStream.readAllBytes());
        }
    }

    private static String getFromAPI(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(HttpRequest.newBuilder()
                        .GET()
                        .uri(uri)
                        .build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.US_ASCII));
        if (response.statusCode() != 200)
            return String.valueOf(response.statusCode());

        return response.body();
    }

    private static void cacheBody(String file, String resBody) {
        File f = new File("cache");
        f.mkdir();
        f = new File("cache/" + file);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(resBody.getBytes(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
