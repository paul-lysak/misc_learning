import org.junit.Test;

import java.util.function.Function;

/**
 * Created by Paul Lysak on 15.05.15.
 */
public class OtherTests {
    @Test
    public void testIt() {

    }
}

interface Functor<A, F extends Functor<?, ?>> {
    public <B> F map(Function<A, B> f);
}
