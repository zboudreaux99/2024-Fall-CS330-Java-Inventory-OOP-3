package edu.odu.cs.cs330.items;

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

        this.speed = 0;
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
            return false;
        } else if (this == rhs) {
            return true;
        }

        Tool rhsItem = (Tool) rhs;

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
        System.out.println(String.format(
           FMT_STR,
           this.getName(),
           this.getDurability(),
           this.getSpeed(),
           this.getMaterial(),
           this.getModifier(),
           this.getModifierLevel()
        ));
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
