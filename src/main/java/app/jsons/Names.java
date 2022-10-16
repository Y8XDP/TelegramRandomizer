package app.jsons;

import app.Main;
import app.jsons.names.Name;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Names {

    private static List<Name> names = new ArrayList<>();

    public Names(){
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("json/names.json")))) {
            names = new Gson().fromJson(reader, new TypeToken<ArrayList<Name>>(){}.getType());
        } catch (IOException ignore) {}
    }

    public List<Name> getAll(Boolean isMan){
        return names.stream().filter(p -> p.isMan == isMan).toList();
    }
}
