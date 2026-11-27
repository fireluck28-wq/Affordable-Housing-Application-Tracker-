package com.housingtracker.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IncomeApiService {

    private static String lastKnownData = "No cache data available!";

    public String getIncomeData() {

        String endpoint = "https://api.data.gov.my/data-catalogue/?id=hh_income";

        try {

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(endpoint))
                            .GET()
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            int statusCode = response.statusCode();

            if (statusCode >= 200 && statusCode < 300) {

                Gson gson = new Gson();

                Type type = new TypeToken<java.util.List<IncomeRecord>>() {}.getType();

                java.util.List<IncomeRecord> records = gson.fromJson(response.body(), type);

                IncomeRecord latest = records.get(records.size() -1);

                String output = "Latest Household Income Statistics\n\n"
                        + "Date: " + latest.getDate()
                        + "\nMean Income: RM" + latest.getIncome_mean()
                        + "\nMedian Income: RM" + latest.getIncome_median();

                lastKnownData = output;

                return output;

            } else if (statusCode == 404) {

                return "Income statistic could not be found!";

            } else if (statusCode == 500) {

                return "The government income service is currently unavailable. Please try again later!";

            }

            return "Unexpected response: " + statusCode;
        }
        catch(IOException | InterruptedException e) {

            return "API unavailable.\n\nUsing last known data: \n\n" + lastKnownData;
        }
    }
}

