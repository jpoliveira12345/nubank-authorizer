package br.com.nubank.authorizer.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer of a LocalDateTime object, near the ISO_INSTANT java standard.
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    @SuppressWarnings("unused")
    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        var dateStr = "";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'");
        dateStr = formatter.format(value);
        gen.writeString(dateStr);
    }

}
