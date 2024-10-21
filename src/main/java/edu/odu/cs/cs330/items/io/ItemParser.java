package edu.odu.cs.cs330.items.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import edu.odu.cs.cs330.items.Item;
import edu.odu.cs.cs330.items.ItemFactory;

@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.LongVariable",
    "PMD.UseVarargs"
})
public final class ItemParser
{
    private ItemParser()
    {
    }

    /**
     * Read an input stream and generate a collection of Items.
     *
     * @param ins source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static List<Item> readItems(final BufferedReader ins)
        throws IOException
    {
        return ItemParser.streamItems(ins)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Read an input stream and transform it into a stream of Shapes.
     *
     * @param ins source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static Stream<Item> streamItems(final BufferedReader ins)
        throws IOException
    {
        return ins
            .lines()
            .map(line -> line.strip().split(" "))
            .filter(
                (String[] tokens) -> {
                    final String keyword = tokens[0];

                    final int numTokensAfterName = tokens.length - 1;

                    // If "keyword" is unknown... -1 will be returned
                    // An array cannot have -1 elements
                    final int numTokensNeeded = ItemFactory.getNumberOfRequiredValues(keyword);

                    return numTokensAfterName == numTokensNeeded;
                }
            )
            .map(
                (String[] splitLine) -> {

                    final String keyword = splitLine[0].strip();
                    final String tokens[] = Arrays.stream(splitLine)
                        .skip(1)
                        .toArray(String[]::new);

                    final Item item = ItemFactory.createItemFromTokens(keyword, tokens);

                    return item;
                }
            )
            .filter(Objects::nonNull);
    }

    /**
     * Take a string and transform it into a stream of Shapes.
     *
     * @param ins source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static Stream<Item> streamItems(final String str)
        throws IOException
    {
        BufferedReader buffer = new BufferedReader(new StringReader(str));

        return ItemParser.streamItems(buffer);
    }

    /**
     * Read an input stream and generate a collection of Items.
     *
     * @param filename source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static List<Item> readItemsFromFile(final String filename)
        throws IOException
    {
        final FileReader inFile = new FileReader(filename);
        final BufferedReader buffer = new BufferedReader(inFile);

        final List<Item> itemsToStore = ItemParser.readItems(buffer);
        buffer.close();

        return itemsToStore;
    }
}
