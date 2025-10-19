package com.gencal.testmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseConfig {
    private String host = "localhost";
    private int port = 5432;
    private String database = "testmod_db";
    private String username = "testmod_user";
    private String password = "testmod_password";
    private String connectionTimeout = "30000";
    private String maxPoolSize = "20";

    public static DatabaseConfig load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("testmod_database.json");

        if (Files.exists(configPath)) {
            try (BufferedReader reader = Files.newBufferedReader(configPath)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, DatabaseConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DatabaseConfig defaultConfig = new DatabaseConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("testmod_database.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(configPath.toFile())) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConnectionTimeout() { return connectionTimeout; }
    public void setConnectionTimeout(String connectionTimeout) { this.connectionTimeout = connectionTimeout; }

    public String getMaxPoolSize() { return maxPoolSize; }
    public void setMaxPoolSize(String maxPoolSize) { this.maxPoolSize = maxPoolSize; }
}