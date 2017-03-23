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

        List.of("Hello Łódź JUG")
          .forEach(System.out::println);

    }
}
