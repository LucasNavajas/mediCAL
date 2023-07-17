package com.example.medical.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.time.LocalDateTime; // Importa la clase LocalDateTime
import java.time.format.DateTimeFormatter; // Importa la clase DateTimeFormatter
import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Usa el nuevo adaptador para LocalDateTime
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.241:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    // Nueva clase para manejar el formato de LocalDateTime
    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value != null) {
                out.value(formatter.format(value));
            } else {
                out.nullValue();
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            } else {
                String dateString = in.nextString();
                return LocalDateTime.parse(dateString, formatter);
            }
        }
    }
}

