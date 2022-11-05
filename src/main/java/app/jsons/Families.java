package app.jsons;

import app.Main;
import app.jsons.families.Family;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Families {
    private static List<Family> families = new ArrayList<>();

    public Families(){
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("json/families.json")))) {
            families = new Gson().fromJson(reader, new TypeToken<ArrayList<Family>>(){}.getType());
        } catch (IOException ignore) {}
    }

    public List<Family> getAll(Boolean isMan){
        return families.stream().filter(p -> p.isMan == isMan).toList();
    }
}
