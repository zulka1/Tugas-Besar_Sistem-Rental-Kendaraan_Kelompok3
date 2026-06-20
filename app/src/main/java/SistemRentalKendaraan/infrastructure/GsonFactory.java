package SistemRentalKendaraan.infrastructure;

import com.google.gson.*;
import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.model.Mobil;
import SistemRentalKendaraan.domain.model.Motor;
import java.lang.reflect.Type;

public class GsonFactory {
    public static Gson createGson() {
        return new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(Kendaraan.class, new KendaraanAdapter())
            .create();
    }

    private static class KendaraanAdapter implements JsonSerializer<Kendaraan>, JsonDeserializer<Kendaraan> {
        private final Gson cleanGson = new Gson();

        @Override
        public JsonElement serialize(Kendaraan src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = cleanGson.toJsonTree(src).getAsJsonObject();
            result.addProperty("type", src.getJenis());
            return result;
        }

        @Override
        public Kendaraan deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement typeElement = jsonObject.get("type");
            if (typeElement == null) {
                throw new JsonParseException("Missing type property for Kendaraan");
            }
            String type = typeElement.getAsString();
            if ("Mobil".equalsIgnoreCase(type)) {
                return cleanGson.fromJson(json, Mobil.class);
            } else if ("Motor".equalsIgnoreCase(type)) {
                return cleanGson.fromJson(json, Motor.class);
            }
            throw new JsonParseException("Unknown vehicle type: " + type);
        }
    }
}
