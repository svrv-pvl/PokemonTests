import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class PokemonListTestGroup {

    public static class PokemonListTests {

        private Reference reference;

        private void executePreconditions() throws IOException, URISyntaxException {
            reference = new Reference();
        }

        @Test
        public void testPokemonCountIsCorrect() throws IOException, URISyntaxException {
            executePreconditions();

            Response response = get("https://pokeapi.co/api/v2/pokemon");
            PokemonListResponse pokemonListObj = response.getBody().as(PokemonListResponse.class);

            int expectedResult = reference.count;
            int actualResult = pokemonListObj.getCount();

            Assert.assertEquals(expectedResult, actualResult);
        }

        @Test
        public void testFullPokemonListIsCorrect() throws IOException, URISyntaxException {
            executePreconditions();

            Response response = get("https://pokeapi.co/api/v2/pokemon?limit=1200");
            PokemonListResponse pokemonListObj = response.getBody().as(PokemonListResponse.class);

            List<String> expectedResult = reference.pokemonListName;
            List<PokemonListNameUrlPair> actualPokemonListNameUrl = pokemonListObj.getPokemonListNameUrlPairList();

            List<String> actualResult = new ArrayList<>();

            for (PokemonListNameUrlPair pokemonObj : actualPokemonListNameUrl) {
                actualResult.add(pokemonObj.getName());
            }

            assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
        }
    }

    @RunWith(Parameterized.class)
    public static class PokemonListParamTests {
        private int offset;
        private int limit;
        private String next;
        private String previous;

        @Parameterized.Parameters
        public static Collection<Object[]> nextAndPreviousData() {
            List<Object[]> args = new ArrayList<>();
            args.add(new Object[]{50,
                    50,
                    "https://pokeapi.co/api/v2/pokemon?offset=100&limit=50",
                    "https://pokeapi.co/api/v2/pokemon?offset=0&limit=50"});
            return args;
        }

        public PokemonListParamTests(int offset, int limit, String next, String previous) {
            super();
            this.offset = offset;
            this.limit = limit;
            this.next = next;
            this.previous = previous;
        }

        @Test
        public void NextAndPreviousLinksTests() throws IOException, URISyntaxException {

            Response response = get("https://pokeapi.co/api/v2/pokemon?" + "offset=" + offset + "&limit=" + limit);
            PokemonListResponse pokemonListObj  = response.getBody().as(PokemonListResponse.class);

            String actualNext = pokemonListObj.getNext().toString();
            String actualPrevious = pokemonListObj.getPrevious().toString();

            assertThat(actualNext).isEqualTo(next);
            assertThat(actualPrevious).isEqualTo(previous);
        }

    }
}