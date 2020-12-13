package com.example.movieratingapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtractDataTest {
    @Test
    public void addition_Correct() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parsingFilmData() {
        try {
            String jsonString = "{\n" +
                    "   \"title\":\"Little Women\",\n" +
                    "   \"image\":\"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTfmqfPBu_4-lbAiQnydRIgp-O56dEPvo3yfaWImBzAfbAnooTz\",\n" +
                    "   \"country\":\"USA\",\n" +
                    "   \"year\":2019,\n" +
                    "   \"synopsis\":\"Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women, each determined to live life on her own terms.\",\n" +
                    "   \"release\":\"2019.12.07\",\n" +
                    "   \"cast_crew\":[\n" +
                    "      {\n" +
                    "         \"director\":\"Greta Gerwig\",\n" +
                    "         \"dirImage\":\"https://m.media-amazon.com/images/M/MV5BNDE5MTIxMTMzMV5BMl5BanBnXkFtZTcwMjMxMDYxOQ@@._V1_UX214_CR0,0,214,317_AL_.jpg\",\n" +
                    "         \"cast1\":\"Saoirose Roman\",\n" +
                    "         \"cast1Image\":\"https://m.media-amazon.com/images/M/MV5BMjExNTM5NDE4NV5BMl5BanBnXkFtZTcwNzczMzEzOQ@@._V1_UX214_CR0,0,214,317_AL_.jpg\",\n" +
                    "         \"cast2\":\"Emma Watson\",\n" +
                    "         \"cast2Image\":\"https://m.media-amazon.com/images/M/MV5BMTQ3ODE2NTMxMV5BMl5BanBnXkFtZTgwOTIzOTQzMjE@._V1_UY317_CR21,0,214,317_AL_.jpg\"\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}";

            JSONObject currentFilm = new JSONObject(jsonString);
            String expected = "Little Women";
            String result = QueryUtils.FilmAsyncTask.parsingJsonObj(currentFilm).getTitle();
            assertEquals(expected, result);

        } catch(JSONException e) {
            Log.e("QueryUtils", "Problem parsing the film JSON results", e);
        }

    }
}

