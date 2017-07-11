import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.security.InvalidKeyException;

/**
 * Created by Paul Lysak on 12.05.15.
 */
public class Java7Tests {
    @Test
    public void testMultipleExceptions() {
        System.out.println("hi there");
        int a = 0;
        try {
//            a = 100 / 0;
            throw new NumberFormatException();
        } catch (NumberFormatException | InvalidPathException e) {
            IllegalArgumentException e1 = e;
            System.out.println(e);

        }

    }
}
