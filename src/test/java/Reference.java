import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Reference {
    public int count = 1118;
    public List<String> pokemonListName;

    public Reference() throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();

        URL resource = getClass().getClassLoader().getResource("PokemonListName.json");
        File file = new File(resource.toURI());

        TypeFactory typeFactory = mapper.getTypeFactory();
        CollectionType collectionType = typeFactory.constructCollectionType(List.class, PokemonListName.class);

        List<PokemonListName> pokemonListNameObj = mapper.readValue(file, collectionType);

        pokemonListName = new ArrayList<String>();

        for(PokemonListName pokemonObj : pokemonListNameObj){
            pokemonListName.add(pokemonObj.getName());
        }
    }
}