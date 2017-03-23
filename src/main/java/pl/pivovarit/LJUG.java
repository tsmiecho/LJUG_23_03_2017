package pl.pivovarit;

import javaslang.collection.List;

/**
 * Grzegorz Piwowarek
 * gpiwowarek@gmail.com
 * @pivovarit
 * https://github.com/pivovarit/LJUG_23_03_2017
 */
public class LJUG {
    public static void main(String[] args) throws Exception {

        final String result = List
          .of("Hello", "Łódź", "JUG")
          .intersperse(" ").append("!!!")
          .reduceLeftOption((s, s2) -> s + s2)
          .getOrElseThrow(() -> new IllegalStateException(";___;"));

        System.out.println(result);
    }
}
