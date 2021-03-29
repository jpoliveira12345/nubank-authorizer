package br.com.nubank.authorizer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility functions to work with JSON
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T toObject(final String json, final Class<T> type) {
        try{
            return mapper.readerFor(type).readValue(json);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> String toJson(final T obj) {
        try{
            return mapper.writeValueAsString(obj);
        }catch (Exception e){
            return null;
        }
    }

}
