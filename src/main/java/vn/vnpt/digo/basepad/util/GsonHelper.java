/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author ChiThien
 */
public class GsonHelper {

   // public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    private static final GsonBuilder gsonBuilder = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(ObjectId.class, new JsonSerializer<ObjectId>() {
                @Override
                public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toHexString());
                }
            })
            .registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {
                @Override
                public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new ObjectId(json.getAsString());
                }
            });

    public static Gson getGson() {
        return gsonBuilder.create();
    }

    public static <S extends Object> S copyObject(S s) {
        String jsonString = getGson().toJson(s);
        return (S) getGson().fromJson(jsonString, s.getClass());
    }

    public static <S extends Object, T extends Object> T copyObject(S s, Class<T> type) {
        String jsonString = getGson().toJson(s);
        return getGson().fromJson(jsonString, type);
    }

    public static <T> List<T> copyArray(String s, Class<T[]> type) {
        T[] arr = new Gson().fromJson(s, type);
        return Arrays.asList(arr);
    }

    public static <S extends Object> String getJson(S s) {
        return getGson().toJson(s);
    }

}
