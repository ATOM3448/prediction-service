package ru.tusur.prediction.service.util;

import static ru.tusur.prediction.service.core.error.ErrorCode.INTERNAL_ERROR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.TimeZone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;
import ru.tusur.prediction.service.core.error.ServiceException;

/**
 * Методы для работы с форматом json.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    public static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper()
                    .registerModule(createCustomizedJavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setTimeZone(TimeZone.getDefault());

    public static final ObjectMapper OBJECT_MAPPER_WITH_DEFAULT_JAVA_TIME =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setTimeZone(TimeZone.getDefault());

    /**
     * Сериализация Java объекта в JSON строку, с помощью маппера {@link JsonUtils#OBJECT_MAPPER_WITH_DEFAULT_JAVA_TIME}.
     *
     * @param object Java объект.
     * @return JSON строка.
     */
    public static String serialize(Object object) {
        try {
            return OBJECT_MAPPER_WITH_DEFAULT_JAVA_TIME.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    /**
     * Сериализация Java объекта в JSON строку, с помощью указанного маппера.
     *
     * @param object Java объект.
     * @param objectMapper Маппер, для сериализации.
     * @return JSON строка.
     */
    public static String serialize(Object object, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    /**
     * Десериализация JSON строки в Java объект переданного типа.
     *
     * @param json JSON строка.
     * @param valueTypeRef Тип Java объекта.
     * @return Java объект переданного типа.
     */
    public static <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(json, valueTypeRef);
        } catch (JsonProcessingException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    /**
     * Десериализация JSON строки в Java объект переданного типа.
     *
     * @param json JSON строка.
     * @param tClass Тип Java объекта.
     * @return Java объект переданного типа.
     */
    public static <T> T deserialize(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    /**
     * Чтение содержимого файла в строку.
     *
     * @param pathToFileInClasspath путь к файлу.
     * @return Содержимое файла в строке.
     */
    public static String readFileToString(String pathToFileInClasspath) {
        var path = getFile(pathToFileInClasspath).toPath();
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    private static File getFile(String pathToFileInClasspath) {
        try {
            return ResourceUtils.getFile("classpath:" + pathToFileInClasspath);
        } catch (FileNotFoundException exception) {
            throw new ServiceException(INTERNAL_ERROR, exception);
        }
    }

    private static SimpleModule createCustomizedJavaTimeModule() {
        InstantToMillisSerializer instantSerializer = new InstantToMillisSerializer();

        return new JavaTimeModule().addSerializer(Instant.class, instantSerializer);
    }

    private static class InstantToMillisSerializer extends StdSerializer<Instant> {

        private InstantToMillisSerializer() {
            super(Instant.class);
        }

        @Override
        public void serialize(Instant value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {
            generator.writeNumber(value.toEpochMilli());
        }
    }
}
