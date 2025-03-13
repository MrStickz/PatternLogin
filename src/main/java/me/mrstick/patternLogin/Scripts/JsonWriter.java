package me.mrstick.patternLogin.Scripts;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class JsonWriter {

    private final String path;
    public Gson gson;
    private final Boolean prettyPrinting;

    public JsonWriter(String path, Boolean prettyPrinting) {
        this.path = path;
        this.prettyPrinting = prettyPrinting;
        gson = prettyPrinting ? new GsonBuilder().setPrettyPrinting().create() : new GsonBuilder().create();
    }

    public void Add(String key, String val) {
        addValue(key, val);
    }

    public void Add(String key, int val) {
        addValue(key, val);
    }

    public void Add(String key, boolean val) {
        addValue(key, val);
    }

    public void Add(String key, double val) {
        addValue(key, val);
    }

    public void Add(String key, List<?> val) {
        addValue(key, val);
    }

    private void addValue(String key, Object val) {
        JsonObject jsonObject = readFromFile();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }

        List<String> keys = Arrays.asList(key.split("\\."));
        JsonObject current = jsonObject;

        for (int i = 0; i < keys.size(); i++) {
            String currentKey = keys.get(i);

            if (i == keys.size() - 1) {
                if (val instanceof String) {
                    current.addProperty(currentKey, (String) val);
                } else if (val instanceof Number) {
                    current.addProperty(currentKey, (Number) val);
                } else if (val instanceof Boolean) {
                    current.addProperty(currentKey, (Boolean) val);
                } else if (val instanceof List) {
                    JsonArray jsonArray = new JsonArray();
                    for (Object item : (List<?>) val) {
                        if (item instanceof String) {
                            jsonArray.add((String) item);
                        } else if (item instanceof Number) {
                            jsonArray.add((Number) item);
                        } else if (item instanceof Boolean) {
                            jsonArray.add((Boolean) item);
                        } else {
                            throw new IllegalArgumentException("Unsupported List item type: " + item.getClass());
                        }
                    }
                    current.add(currentKey, jsonArray);
                } else {
                    throw new IllegalArgumentException("Unsupported value type: " + val.getClass());
                }
            } else {
                if (!current.has(currentKey) || !current.get(currentKey).isJsonObject()) {
                    current.add(currentKey, new JsonObject());
                }
                current = current.getAsJsonObject(currentKey);
            }
        }

        writeToFile(jsonObject);
    }

    public void Remove(String key) {
        JsonObject jsonObject = readFromFile();
        if (jsonObject == null) {
            System.out.println("No JSON data found to remove from.");
            return;
        }
        List<String> keys = Arrays.asList(key.split("\\."));
        JsonObject current = jsonObject;
        for (int i = 0; i < keys.size(); i++) {
            String currentKey = keys.get(i);
            if (i == keys.size() - 1) {
                current.remove(currentKey);
            } else {
                if (!current.has(currentKey) || !current.get(currentKey).isJsonObject()) {
                    System.out.println("Key not found: " + currentKey);
                    return;
                }
                current = current.getAsJsonObject(currentKey);
            }
        }
        writeToFile(jsonObject);
    }

    public Object Get(String key) {
        JsonObject jsonObject = readFromFile();
        if (jsonObject == null) {
            return null;
        }

        // If the key is an empty string, return all top-level data as a Map
        if (key.isEmpty()) {
            return toMap(jsonObject);
        }

        String[] keys = key.split("\\.");
        JsonElement current = jsonObject;

        for (String currentKey : keys) {
            if (current.isJsonObject() && current.getAsJsonObject().has(currentKey)) {
                current = current.getAsJsonObject().get(currentKey);
            } else {
                return null; // Key not found
            }
        }

        return convertJsonElement(current);
    }

    private Object convertJsonElement(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return primitive.getAsString();
            } else if (primitive.isNumber()) {
                return primitive.getAsNumber();
            } else if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            }
        } else if (element.isJsonArray()) {
            return toList(element.getAsJsonArray());
        } else if (element.isJsonObject()) {
            return toMap(element.getAsJsonObject());
        }

        return null; // Unsupported type
    }

    private List<Object> toList(JsonArray jsonArray) {
        List<Object> list = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            list.add(convertJsonElement(element));
        }
        return list;
    }


    // Helper method to find the first array in the JsonObject
    private JsonArray findFirstArray(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonElement value = entry.getValue();

            if (value.isJsonArray()) {
                return value.getAsJsonArray();
            } else if (value.isJsonObject()) {
                JsonArray nestedArray = findFirstArray(value.getAsJsonObject());
                if (nestedArray != null) {
                    return nestedArray;
                }
            }
        }
        return null; // No array found
    }


    private JsonObject readFromFile() {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e);
        }
        return null;
    }

    private void writeToFile(JsonObject jsonObject) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(jsonObject, writer);
            writer.flush();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e);
        }
    }


    // Converters
    public Object toMap(Object jsonElement) {
        if (jsonElement instanceof JsonObject) {
            Map<String, Object> map = new HashMap<>();
            JsonObject jsonObject = (JsonObject) jsonElement;

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                map.put(entry.getKey(), toMap(entry.getValue()));
            }

            return map;
        } else if (jsonElement instanceof JsonArray) {
            List<Object> list = new ArrayList<>();
            JsonArray jsonArray = (JsonArray) jsonElement;

            for (JsonElement element : jsonArray) {
                list.add(toMap(element));
            }

            return list;
        } else if (jsonElement instanceof JsonElement) {
            JsonElement element = (JsonElement) jsonElement;
            if (element.isJsonPrimitive()) {
                if (element.getAsJsonPrimitive().isString()) {
                    return element.getAsString();
                } else if (element.getAsJsonPrimitive().isNumber()) {
                    return element.getAsNumber();
                } else if (element.getAsJsonPrimitive().isBoolean()) {
                    return element.getAsBoolean();
                }
            } else if (element.isJsonNull()) {
                return null;
            }
        }

        return jsonElement; // Fallback for unexpected cases
    }
}