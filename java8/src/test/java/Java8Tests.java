import com.beust.jcommander.internal.Lists;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Paul Lysak on 12.05.15.
 */
public class Java8Tests {
    @Test
    public void testInterfaceDefaults() {
        Sub s = new Sub();
        s.foo();
    }

    @Test
    public void functionalInterfaceTest() {
        NumInt ni1 = (x) -> 2*x;
        NumInt ni2 = (x) -> {int a=2*x; return a*a;};
        System.out.println(ni1.doIt(10));
        System.out.println(ni2.doIt(10));
    }

    @Test
    public void optionalTest() {
        Optional<String> o = Optional.of("hi there");
        System.out.println(o.map((s) -> s.toUpperCase()).get());
        System.out.println(o.flatMap((s) -> Optional.ofNullable(s.toUpperCase())).get());
    }

    @Test
    public void streamTest() {
        List<String> list = Arrays.asList(new String[]{"hi", "there"});
        List<String> l2 = list.stream().map((s) -> s.toUpperCase()).collect(Collectors.toList());
        list.parallelStream().map((s) -> s.toUpperCase()).count();

        System.out.println(l2);
    }

}

//__________________________________
interface SupMain {
    default void foo() {
        System.out.println("sup_main");
    }
}

interface SupA extends SupMain {
     default void foo() {
        System.out.println("sup_A");
    }
}

interface SupB extends SupMain {
     default void foo() {
        System.out.println("sup_B");
    }
}

class Sub implements  SupA, SupB {
    @Override
    public void foo() {
        SupB.super.foo();
    }
}
//__________________________________

@FunctionalInterface
interface NumInt {
    int doIt(int x);
}
