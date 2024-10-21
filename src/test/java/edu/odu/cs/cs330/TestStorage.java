package edu.odu.cs.cs330;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import static edu.odu.cs.cs330.items.Inventory.DEFAULT_SIZE;
import edu.odu.cs.cs330.items.Inventory;
import edu.odu.cs.cs330.items.Item;
import edu.odu.cs.cs330.items.Tool;
import edu.odu.cs.cs330.items.Consumable;
import edu.odu.cs.cs330.items.Armour;


/**
 * 1 - Does this piece of code perform the operations
 *     it was designed to perform?
 *
 * 2 - Does this piece of code do something it was not
 *     designed to perform?
 *
 * 1 Test per mutator
 *
 * This is technically an Integration Test.
 */
@SuppressWarnings({
    "PMD.AtLeastOneConstructor",
    "PMD.BeanMembersShouldSerialize",
    "PMD.JUnitAssertionsShouldIncludeMessage",
    "PMD.JUnitTestContainsTooManyAsserts",
    "PMD.LocalVariableCouldBeFinal",
    "PMD.MethodArgumentCouldBeFinal",
    "PMD.LawOfDemeter"
})
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestStorage
{
    private Armour boots;
    private Consumable tomato;
    private Tool shovel;

    @BeforeEach
    public void setUp()
    {
        boots = new Armour();
        boots.fromTokens(new String[] {
            "Boots", "Diamond", "100", "10", "FeatherFalling", "4", "lightning"
        });

        tomato = new Consumable();
        tomato.fromTokens(new String[] {
            "Tomato", "Hunger-10", "2"
        });

        shovel = new Tool();
        shovel.fromTokens(new String[] {
            "Shovel", "Gold", "20", "3", "Unbreaking", "2"
        });
    }

    @ParameterizedTest(name = "{index} => size=''{0}''")
    @ValueSource(strings = {"11", "12", "1", "10", "7", "5", "100"})
    public void testParseValid(String intAsString)
    {
        String[] args = new String[]{"file.txt", intAsString};
        assertThat(Storage.getInventorySize(args), equalTo(Integer.parseInt(intAsString)));
    }

    @Test
    public void testParseInvalidTooFewArgs()
    {
        String[] args = new String[]{"file.txt"};
        assertThat(Storage.getInventorySize(args), equalTo(DEFAULT_SIZE));
    }

    @Test
    public void testParseInvalidNotAnInt()
    {
        String[] args = new String[]{"file.txt", "The Number Ten"};
        assertThat(Storage.getInventorySize(args), equalTo(DEFAULT_SIZE));
    }

    @ParameterizedTest(name = "{index} => size=''{0}''")
    @ValueSource(strings = {"0", "-1", "-100", "-10", "-5", "-32"})
    public void testParseInvalidZeroOrLess(String intAsString)
    {
        String[] args = new String[]{"file.txt", intAsString};

        assertThat(Storage.getInventorySize(args), equalTo(DEFAULT_SIZE));
    }

    @Test
    public void testCreateInventory()
    {
        List<Item> items = Arrays.asList(boots, tomato, shovel, tomato);
        List<String> itemsAsStrings = Arrays.asList(
            boots.toString(),
            tomato.toString(),
            shovel.toString()
        );

        Inventory inv = Storage.createInventory(items, 4);
        assertThat(inv.percentFilled(), equalTo(75));

        String invAsStr = inv.toString();
        assertThat(invAsStr, stringContainsInOrder(Arrays.asList("75%", "of", "4", "slots")));
        assertThat(invAsStr, stringContainsInOrder(itemsAsStrings));
    }

    @Test
    public void testCreateInventoryWithDiscards()
    {
        List<Item> items = Arrays.asList(boots, tomato, shovel, tomato);
        List<String> itemsAsStrings = Arrays.asList(
            boots.toString(),
            tomato.toString()
        );

        Inventory inv = Storage.createInventory(items, 2);
        assertThat(inv.percentFilled(), equalTo(100));

        String invAsStr = inv.toString();
        assertThat(invAsStr, stringContainsInOrder(Arrays.asList("100%", "of", "2", "slots")));
        assertThat(invAsStr, stringContainsInOrder(itemsAsStrings));
    }
}

