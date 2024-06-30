package com.example.swen2_ss2024.service;

import com.example.swen2_ss2024.entity.RouteInfo;
import com.example.swen2_ss2024.entity.Tours;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;


public class OpenRouteService {

    private static final String CONFIG_FILE = "/config.properties";
    private static String KEY_API;
    private static String ORS_URL;
    private static String GEO_URL;

    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);

    static {
        try (InputStream input = OpenRouteService.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.error("Not found!");
            }
            Properties properties = new Properties();
            properties.load(input);
            KEY_API = properties.getProperty("api_key");
            ORS_URL = properties.getProperty("base_url");
            GEO_URL = properties.getProperty("geocode_url");
            logger.info("Properties initialized");
        } catch (Exception e) {
            logger.error("Something went wrong!", e);
        }
    }

    private String createHttpRequest(String url) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() != 200){
            logger.error("Request error. HTTP Code: " + connection.getResponseCode());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String output;
        while ((output = bufferedReader.readLine()) != null){
            stringBuilder.append(output);
        }
        connection.disconnect();
        return stringBuilder.toString();
    }

    public double[] getCoordinates(String addr) throws Exception{
        String url = "https://api.openrouteservice.org/geocode/search?api_key=" + KEY_API + "&text=" + URLEncoder.encode(addr, StandardCharsets.UTF_8);
        String request= createHttpRequest(url);

        JsonObject jsonObject= JsonParser.parseString(request).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("features");
        if(array == null || array.isEmpty()){
            logger.error("No coordinates found for addr: {}", addr);
        }
        JsonObject geo = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
        JsonArray coords = geo.getAsJsonArray("coordinates");
        return new double[]{coords.get(1).getAsDouble(), coords.get(0).getAsDouble()};
    }

    public RouteInfo parseRoute(String json){
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if(jsonObject.has("error")){
            String message = jsonObject.get("error").getAsString();
            logger.error("Error with API: " + message);
        }
        JsonArray features = jsonObject.getAsJsonArray("features");
        if(features == null || features.isEmpty()){
            logger.error("No features found in message.");
        }
        JsonObject feature = features.get(0).getAsJsonObject();
        JsonObject properties = feature.getAsJsonObject("properties");
        JsonObject sum = properties.getAsJsonObject("summary");
        if(sum == null){
            logger.error("No summary found in properties message");
        }
        double distance = sum.get("distance").getAsDouble() / 1000;
        double duration = sum.get("duration").getAsDouble() / 60;
        return new RouteInfo(distance, duration);
    }

    public String getRouteFromCoordinates(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, String transport) throws Exception{
        String url = String.format("%s%s?api_key=%s&start=%s,%s&end=%s,%s", ORS_URL, transport, KEY_API, fromLongitude, fromLatitude, toLongitude, toLatitude);
        return createHttpRequest(url);
    }

    public BufferedImage fetchMapForTour(Tours tour, int zoom, int gridSize) throws IOException {
        double[] originCoords = null;
        try {
            originCoords = getCoordinates(tour.getFrom());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double[] destinationCoords = null;
        try {
            destinationCoords = getCoordinates(tour.getTo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MapAssembler mapAssembler = new MapAssembler();
        double centerLat = (originCoords[0] + destinationCoords[0]) / 2;
        double centerLon = (originCoords[1] + destinationCoords[1]) / 2;
        BufferedImage mapImage = mapAssembler.assembleMap(centerLat, centerLon, zoom, gridSize);

        return mapImage;
    }

    public String saveImage(BufferedImage image) throws IOException {
        String randomFileName = UUID.randomUUID().toString() + ".png";
        Path destinationDirectory = Path.of("C:\\Users\\alexa\\IdeaProjects\\swen2_ss2024\\src\\main\\resources\\Images");
        Path destinationPath = destinationDirectory.resolve(randomFileName);

        // Check if the directory exists, if not create it
        if (!Files.exists(destinationDirectory)) {
            Files.createDirectories(destinationDirectory);
        }

        ImageIO.write(image, "png", destinationPath.toFile());
        return destinationPath.toString();
    }
}
