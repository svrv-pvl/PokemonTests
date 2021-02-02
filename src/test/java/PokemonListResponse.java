import java.net.URL;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonListResponse {
    private int count;
    private URL next;

    private URL previous;

    @JsonProperty("results")
    private List<PokemonListNameUrlPair> PokemonListNameUrlPairList;

    PokemonListResponse() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public URL getNext() {
        return next;
    }

    public void setNext(URL next) {
        this.next = next;
    }

    public URL getPrevious() {
        return previous;
    }

    public void setPrevious(URL previous) {
        this.previous = previous;
    }

    public List getPokemonListNameUrlPairList() {
        return PokemonListNameUrlPairList;
    }

    public void setPokemonListNameUrlPairList(List<PokemonListNameUrlPair> pokemonListNameUrlPairList) {
        PokemonListNameUrlPairList = pokemonListNameUrlPairList;
    }
}
