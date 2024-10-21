package edu.odu.cs.cs330.items;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;


/**
 * An Inventory is composed of n slots. Each slot may store only
 * one type of item--specified by *slots*.
 * <p>
 * Once all slots are filled, no additional Item types may be
 * stored. Individual slots may contain any number of the same
 * Item--if the Item is stackable.
 */
@SuppressWarnings({
    "PMD.LawOfDemeter",
    "PMD.OnlyOneReturn",
    "PMD.BeanMembersShouldSerialize"
})
public class Inventory implements Iterable<ItemStack>
{
    /**
     * This is the Default Inventory size.
     */
    public static final int DEFAULT_SIZE = 10;

    /**
     * Individual item slots--each ItemStack occupies one slot.
     */
    private List<ItemStack> slots;

    /**
     * Total number of distinct Item types that can be stored.
     */
    private int capacity;

    /**
     * Default to an inventory with 10 slots.
     */
    public Inventory()
    {
        this(DEFAULT_SIZE);
    }

    /**
     * Create an inventory with n slots.
     *
     * @param desiredCapacity size of the new Inventory
     */
    public Inventory(final int desiredCapacity)
    {
        this.slots    = new ArrayList<>();
        this.capacity = desiredCapacity;
    }

    /**
     * Get the total number of slots (inventory size).
     *
     * @return maximum size
     */
    public int totalSlots()
    {
        return this.capacity;
    }

    /**
     * Get the number of currently filled (used) slots.
     *
     * @return number of slots in use
     */
    public int utilizedSlots()
    {
        return this.slots.size();
    }

    /**
     * Get the number of empty (unused) slots.
     *
     * @return number of available slots
     */
    public int emptySlots()
    {
        return this.totalSlots() - this.utilizedSlots();
    }

    /**
     * Determine if all slots are in use.
     *
     * @return true if all slots contain an ItemStack and false otherwise
     */
    public boolean isFull()
    {
        return this.emptySlots() == 0;
    }

    /**
     * Add one item to the inventory list.
     *
     * @param stack new stack of items to add
     *
     * @return true if *stack* was added and false otherwise
     */
    public boolean addItem(final Item oneItem)
    {
        final ItemStack stack = new ItemStack(oneItem);
        return this.addItems(stack);
    }

    /**
     * Add one or more items to the inventory list.
     *
     * @param stack new stack of items to add
     *
     * @return true if *stack* was added and false otherwise
     */
    public boolean addItems(final ItemStack stack)
    {
        final Optional<ItemStack> match = this.slots.stream()
            .filter(entry -> stack.equals(entry))
            .findFirst();

        if (match.isPresent()) {
            final ItemStack matchingStack = match.get();
            if (matchingStack.permitsStacking()) {
                matchingStack.addItems(stack.size());

                return true;
            }
        }

        if (this.emptySlots() == 0) {
            return false;
        }

        slots.add(stack);
        return true;
    }

    /**
     * Return the percent filled rounded to the nearest whole number (integer).
     */
    public int percentFilled()
    {
        return (int) Math.round(100.0 * this.utilizedSlots() / capacity);
    }

    @Override
    public Iterator<ItemStack> iterator()
    {
        return this.slots.iterator();
    }

    private String usageSummary()
    {
        return String.format(" -Used %3d%% of %d slots", percentFilled(), capacity);
    }

    /**
     * Generate a Summary of the Inventory and all Items contained within.
     */
    @Override
    public String toString()
    {
        return Stream.concat(
            Stream.of(this.usageSummary()),
            this.slots
                .stream()
                .map(Object::toString)
        )
        .collect(
            joining(System.lineSeparator(), "", "")
        );
    }
}
