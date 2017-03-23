package pl.pivovarit.javaslang;

import com.google.common.collect.ImmutableList;
import javaslang.*;
import javaslang.collection.HashSet;
import javaslang.collection.Map;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static javaslang.$.Some;
import static javaslang.API.*;
import static javaslang.Predicates.instanceOf;

/**
 * Grzegorz Piwowarek
 * gpiwowarek@gmail.com
 * @pivovarit
 * https://github.com/pivovarit/LJUG_23_03_2017
 */
public class S00_Javaslang_Test {

    @Test
    public void e00() throws Exception {
        final List<String> collect = Arrays
          .asList("a", "b", "c")
          .stream()
          .map(s -> s.toUpperCase())
          .collect(Collectors.toList());
    }

    @Test
    public void e01() throws Exception {
        /*
        i -> i + 1
        () -> 42
        */
    }

    @Test
    public void e02() throws Exception {
        String example = null;

        if (example != null) {
            if (example.toUpperCase() != null) {
            }
        }

        final String result = Optional.ofNullable(example)  //1
          .map(s -> s.toUpperCase())  //2
          .map(s -> s + "postfix")    //3
          .orElseGet(() -> "default");//4
    }

    @Test
    public void e03() throws Exception {
        final List<String> strings = Arrays.asList("a", "b", "c");

        strings.stream()
          .map(s -> s.toUpperCase())
          .filter(s -> !s.isEmpty())
          .distinct()
          .findAny();
    }

    /**
     * JAVASLANG
     */
    @Test
    public void e04() throws Exception {
        Function2<String, String, String> joiner = (s, s2) -> s + s2;

        Function1<String, Function1<String, String>> curried
          = joiner.curried();

        Function1<String, String> apply = curried.apply("2");

        System.out.println(apply.apply("parameter"));
    }

    @Test
    public void e05() throws Exception {
        Function2<String, String, String> joiner = (s, s2) -> {
            System.out.println("called!");
            return s + s2;
        };

        Function2<String, String, String> memoized = joiner.memoized();

        memoized.apply("", "");
        memoized.apply("", "");
    }

    @Test
    public void e06() throws Exception {
        Function2<Integer, Integer, Integer> divide = (i1, i2) -> i1/i2;
        Function2<Integer, Integer, Option<Integer>> lift = Function2.lift(divide);
    }

    @Test
    public void e07() throws Exception {
        Tuple2<String, Integer> tuple = Tuple.of("Java", 8);

        final String transform = tuple.transform((s, integer) -> s + integer);

        System.out.println(transform);
    }

    @Test
    public void e08() throws Exception {
        final Option<String> option = Option.of("asd");

        Option<String> map = option.map(String::toUpperCase);

        final Option<Option<String>> asd = option.map(s -> Option.of("ASD"));
        final Option<String> asd1 = option.flatMap(s -> Option.of("ASD"));

        System.out.println(Arrays
          .asList(Arrays.asList("1"), Arrays.asList("2", "3"))
          .stream()
          .flatMap(s -> s.stream())
          .collect(Collectors.toList()));

        System.out.println(Arrays
          .asList(Optional.of("42"), Optional.ofNullable(null))
          .stream()
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList()));

        javaslang.collection.List.of(Option.of(42), Option.none())
          .flatMap(Function.identity());

        javaslang.collection.List.of(Option.of(42), Option.none())
          .flatMap(o -> o);
    }

    @Test
    public void e09() throws Exception {
        final Try<String> failableMethod = Try.of(() -> {
            throw new IllegalArgumentException();
        });

        final String default1 = failableMethod
          .map(s -> s.toUpperCase())
          .getOrElse("default");

        failableMethod
          .map(s -> s.toUpperCase())
          .filter(s -> !s.isEmpty())
          .recover(throwable -> {
              return "haha recovered!";
          });
    }

    @Test
    public void e10() throws Exception {
        Supplier<Integer> result = () -> {
            System.out.println("very long computation...");
            return 42;
        };

        result.get();
        result.get();
    }

    @Test
    public void e11() throws Exception {
        final Lazy<Integer> lazy = Lazy.of(() -> {
            System.out.println("very long computation...");
            return 42;
        });

        lazy.get();
        lazy.get();

        lazy.get();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void e12() throws Exception {
        final ImmutableList<Object> immutableList = ImmutableList.of();

        immutableList.add("");
    }

    @Test
    public void e13() throws Exception {
        final HashSet<String> initial = HashSet.of("asd", "42");

        System.out.println("modified " + initial.add("12321"));
        System.out.println("initial " + initial);
    }

    @Test
    public void e14() throws Exception {
        final HashSet<String> initial = HashSet.of("asd", "42", "aa", "aa");

        final Map<Integer, HashSet<String>> tuple2s = initial.groupBy(String::length);

        System.out.println(tuple2s);

    }

    @Test
    public void e15() throws Exception {
        final javaslang.collection.List<String> initial = javaslang.collection.List.of("asd", "42", "aa", "aa");

        System.out.println(initial.combinations());
    }

    @Test
    public void e16() throws Exception {
        Object o = "42";

        final Integer of = Match(o).of(
          Case("42_", 42222),
          Case("42", 42));

        System.out.println(of);

    }

    @Test
    public void e17() throws Exception {
        Object o = "42jahdkjsad";

        final Option<Integer> of = Match(o).option(
          Case("42_", 42222),
          Case("42", 42));

        Match(o).of(
          Case("42_", 42222),
          Case("42", 42),
          Case($(), "default"));


        Match(o).of(
          Case($("42_"), s -> 42),
          Case("42", 42),
          Case($(), "default"));

        Match(o).of(
          Case(instanceOf(String.class), "string"),
          Case(instanceOf(Integer.class), "integer")
        );


    }

    @Test
    public void e18() throws Exception {
        final Try<String> failableMethod = Try.of(() -> {
            throw new IllegalArgumentException();
        });

        final String default1 = failableMethod
          .map(s -> s.toUpperCase())
          .getOrElse("default");

        failableMethod
          .map(s -> s.toUpperCase())
          .filter(s -> !s.isEmpty())
          .recover(throwable -> {
             Match(throwable).of(
               Case(instanceOf(IllegalStateException.class), "ISE"),
               Case(instanceOf(NullPointerException.class), "NPE"),
               Case($(), "")
             );
          });
    }

    @Test
    public void e19() throws Exception {
        final Option<String> option = Option.of("cos tam");

       /* Match(option).of(
          Case(Some(), t -> ),
          Case()
        )*/
    }


    @Test
    public void e20() throws Exception {

        getFile()
          .map(path -> path)
          .filter(path -> true)
          //.recover(t -> M)
          .getOrElse(Paths.get(""));

    }


    private Try<Path> getFile() {
        return Try.of(() -> Files.createTempFile("", ""));
    }


    @Test
    public void e21() throws Exception {
        final HashSet<String> result = HashSet.of("b", "a");

        For(result, For())
    }





}