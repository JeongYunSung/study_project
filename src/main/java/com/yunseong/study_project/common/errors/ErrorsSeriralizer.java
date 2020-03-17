package com.yunseong.study_project.common.errors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent
public class ErrorsSeriralizer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        value.getFieldErrors().stream().forEach(errors -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("field", errors.getField());
                gen.writeStringField("objectName", errors.getObjectName());
                gen.writeStringField("code", errors.getCode());
                gen.writeStringField("defaultMessage", errors.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        value.getGlobalErrors().stream().forEach(errors -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("objectName", errors.getObjectName());
                gen.writeStringField("code", errors.getCode());
                gen.writeStringField("defaultMessage", errors.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gen.writeEndArray();
    }
}
