package edu.odu.cs.cs330.items;
import java.util.Objects;

/**
 * This class represents one tool--as found in most video games. This includes
 * pickaxes and shovels.
 *
 * Tools may not be stacked. All Constructors must initialize Item::stackable
 * to false.
 */
@SuppressWarnings({
    "PMD.CloneMethodReturnTypeMustMatchClassName",
    "PMD.CloneThrowsCloneNotSupportedException",
    "PMD.LawOfDemeter",
    "PMD.OnlyOneReturn",
    "PMD.ProperCloneImplementation",
    "PMD.MethodArgumentCouldBeFinal",
    "PMD.LocalVariableCouldBeFinal",
    "PMD.ShortClassName",
})
public class Tool extends Equippable {
    /**
     * Format used to generate a printable representation.
     */
    public static final String FMT_STR = String.join(
        "",
        "  Nme: %s%n",
        "  Dur: %d%n",
        "  Spd: %d%n",
        "  Mtl: %s%n",
        "  Mdr: %s (Lvl %d)%n"
    );

    /**
     * Base operation (e.g., harvest/mine) speed.
     */
    protected int speed;

    /**
     * Default to an unstackable tool with zero speed.
     */
    public Tool()
    {
        super();
        this.setSpeed(0);
    }

    /**
     * Retrieve tool speed.
     *
     * @return how quickly a tool operates
     */
    public int getSpeed()
    {
        return this.speed;
    }

    /**
     * Set tool speed.
     *
     * @param spd new speed
     */
    public void setSpeed(int spd)
    {
        this.speed = spd;
    }

    @Override
    public int requiredNumberOfValues()
    {
        // Replace this with the correct value
        return 6;
    }

    @Override
    public void fromTokens(String[] tokens)
    {
        this.setName(tokens[0]);
        this.setMaterial(tokens[1]);
        this.setDurability(Integer.parseInt(tokens[2]));
        this.setSpeed(Integer.parseInt(tokens[3]));
        this.setModifier(tokens[4]);
        this.setModifierLevel(Integer.parseInt(tokens[5]));
    }

    /**
     * Clone--i.e., copy--this Tool.
     */
    @Override
    public Item clone()
    {
        Tool clone = new Tool();
        clone.setName(this.getName());
        clone.setMaterial(this.getMaterial());
        clone.setDurability(this.getDurability());
        clone.setModifier(this.getModifier());
        clone.setModifierLevel(this.getModifierLevel());
        clone.setSpeed(this.getSpeed());
        return clone;
    }

    /**
     * Check for logical equivalence--based on name, speed, material, modifier,
     * and modifierLevel
     *
     * @param rhs object for which a comparison is desired
     */
    @Override
    public boolean equals(Object rhs)
    {
        if (!(rhs instanceof Tool)) {
            System.out.println("failed for instanceof");
            return false;
        } else if (this == rhs) {
            System.out.println("true for this == rhs");
            return true;
        }

        Tool rhsItem = (Tool) rhs;

        if (!this.getName().equals(rhsItem.getName())) {
            System.out.println("Failed on name");
            System.out.println("This name = " + this.getName());
            System.out.println("RHS name = " + rhsItem.getName());
        }

        if (!(this.getSpeed() == rhsItem.getSpeed())) {
            System.out.println("Failed on speed");
            System.out.println("This speed = " + this.getSpeed());
            System.out.println("RHS speed = " + rhsItem.getSpeed());
        }

        if (!this.getMaterial().equals(rhsItem.getMaterial())) {
            System.out.println("Failed on material");
            System.out.println("This material = " + this.getMaterial());
            System.out.println("RHS material = " + rhsItem.getMaterial());
        }

        if (!this.getModifier().equals(rhsItem.getModifier())) {
            System.out.println("Failed on Modifier");
            System.out.println("This Modifier = " + this.getModifier());
            System.out.println("RHS Modifier = " + rhsItem.getModifier());
        }

        if (!(this.getModifierLevel() == rhsItem.getModifierLevel())) {
            System.out.println("Failed on ModifierLevel");
            System.out.println("This ModifierLevel = " + this.getModifierLevel());
            System.out.println("RHS ModifierLevel = " + rhsItem.getModifierLevel());
        }

        return this.getName().equals(rhsItem.getName()) &&
               this.getSpeed() == rhsItem.getSpeed() &&
               this.getMaterial().equals(rhsItem.getMaterial()) &&
               this.getModifier().equals(rhsItem.getModifier()) &&
               this.getModifierLevel() == rhsItem.getModifierLevel(); 
    }

    /**
     * Compute hashCode using name, speed, material, modifier,
     * and modifierLevel.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(
            this.getName(),
            this.getMaterial(),
            this.getModifier(),
            this.getModifierLevel(),
            this.getSpeed()
        );
        // int hash = this.getName().hashCode();
        // hash += 2 * this.getMaterial().hashCode();
        // hash += 4 * this.getModifier().hashCode();
        // hash += 8 * this.getModifierLevel();
        // hash += 32 * this.getSpeed();

        // return hash;
    }

    /**
     * *Print* a Tool.
     */
    @Override
    public String toString()
    {
        return String.format(
           FMT_STR,
           this.getName(),
           this.getDurability(),
           this.getSpeed(),
           this.getMaterial(),
           this.getModifier(),
           this.getModifierLevel()
        );
    }
}
