package com.valkryst.VTerminal_Tutorial.generator;

import com.valkryst.VDice.DiceRoller;
import com.valkryst.VTerminal_Tutorial.enums.*;
import com.valkryst.VTerminal_Tutorial.item.Equipment;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStatistic;
import com.valkryst.VTerminal_Tutorial.statistic.Statistic;

public class EquipmentGenerator extends ItemGenerator {
    private final static int MAX_LEVEL = 60;
    private final static int MIN_LEVEL = 1;

    private final DiceRoller diceRoller = new DiceRoller();

    /** The level to generate the item for. */
    private final int level;

    /** The slot in which the generated item can be equipped. */
    private final EquipmentSlot slot;

    /**
     * The rarity drop chances for every level range.
     *
     * Each row's order is: {Common, Uncommon, Rare, Epic, Legendary, Artifact}
     * Each row corresponds to a level range: 1-10, 11-20, 21-30, 31-40, 41-50, 51-60
     */
    private static final int[][] RARITY_DROP_CHANCES = {
            {94, 5, 1, 0, 0, 0},
            {80, 18, 2, 0, 0, 0},
            {70, 25, 4, 1, 0, 0},
            {55, 35, 7, 2, 1},
            {40, 40, 14, 3, 2, 1},
            {20, 40, 28, 6, 4, 2},
    };

    /**
     * The material drop chances for every level range.
     *
     * Each row's order is: {Copper, Bronze, Iron, Steel, Mithril, Adamantine}
     * Each row corresponds to a level range: 1-10, 11-20, 21-30, 31-40, 41-50, 51-60
     */
    private static final int[][] MATERIAL_DROP_CHANCES = {
            {85, 10, 5, 0, 0, 0},
            {70, 20, 7, 3, 0, 0},
            {55, 25, 14, 5, 1, 0},
            {40, 30, 20, 7, 2, 1},
            {20, 30, 30, 15, 3, 2},
            {10, 30, 30, 20, 6, 4}
    };

    /**
     * Constructs a new EquipmentGenerator.
     *
     * @param level
     *          The level to generate the item for.
     *
     * @param slot
     *          The slot in which the generated item can be equipped.
     */
    public EquipmentGenerator(final int level, final EquipmentSlot slot) {
        if (level < MIN_LEVEL) {
            this.level = MIN_LEVEL;
        } else if (level > MAX_LEVEL) {
            this.level = MAX_LEVEL;
        } else {
            this.level = level;
        }

        this.slot = slot;
    }

    @Override
    public Equipment generate() {
        switch (slot) {
            case HEAD:
            case NECK:
            case CHEST:
            case WRISTS:
            case HANDS:
            case FEET: {
                return generateArmor();
            }
            case MAIN_HAND:
            case OFF_HAND:{
                return generateWeapon(slot == EquipmentSlot.MAIN_HAND);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Pseudorandomly chooses a rarity.
     *
     * @return
     *          A rarity.
     */
    private Rarity randomizeRarity() {
        diceRoller.reset();
        diceRoller.addDice(100, 1);

        final int roll = diceRoller.roll();

        final int[] dropChances = RARITY_DROP_CHANCES[(int) ((level - 1) / 10.0)];
        int chance = 100 - dropChances[0];

        if (roll > chance) {
            return Rarity.COMMON;
        } else if (roll > (chance -= dropChances[1])) {
            return Rarity.UNCOMMON;
        } else if (roll > (chance -= dropChances[2])) {
            return Rarity.RARE;
        } else if (roll > (chance -= dropChances[3])) {
            return Rarity.EPIC;
        } else if (roll > (chance - dropChances[4])) {
            return Rarity.LEGENDARY;
        } else {
            return Rarity.ARTIFACT;
        }
    }

    /**
     * Pseudorandomly chooses a material.
     *
     * @return
     *          A material.
     */
    private Material randomizeMaterial() {
        diceRoller.reset();
        diceRoller.addDice(100, 1);

        final int roll = diceRoller.roll();

        final int[] dropChances = MATERIAL_DROP_CHANCES[(int) ((level - 1) / 10.0)];
        int chance = 100 - dropChances[0];

        if (roll > chance) {
            return Material.COPPER;
        } else if (roll > (chance -= dropChances[1])) {
            return Material.BRONZE;
        } else if (roll > (chance -= dropChances[2])) {
            return Material.IRON;
        } else if (roll > (chance -= dropChances[3])) {
            return Material.STEEL;
        } else if (roll > (chance - dropChances[4])) {
            return Material.MITHRIL;
        } else {
            return Material.ADAMANTINE;
        }
    }

    /**
     * Pseudorandomly chooses a modifier.
     *
     * @return
     *          A modifier.
     */
    private EquipmentModifier randomizeModifier() {
        diceRoller.reset();
        diceRoller.addDice((int) Math.max(((MAX_LEVEL - level) / 10.0), 1), 1);

        // Only a fraction of items should have a modifier, so we do this first roll to determine if a modifier
        // should be chosen.
        if (diceRoller.roll() == 1) {
            diceRoller.reset();
            diceRoller.addDice(EquipmentModifier.values().length, 1);
            return EquipmentModifier.values()[diceRoller.roll() - 1];
        } else {
            return EquipmentModifier.NONE;
        }
    }

    /**
     * Generates an armor equipment item.
     *
     * @return
     *          The generated piece of equipment.
     */
    private Equipment generateArmor() {
        // Generate Misc
        final Rarity rarity = randomizeRarity();
        final Material material = randomizeMaterial();
        final EquipmentModifier modifier = randomizeModifier();

        // Generate Name
        final String name = material.getName() + " " + slot.getName();

        // Generate Description
        final String description = material.getName() + " " + slot.getName().toLowerCase() + ".";

        // Build Item
        final Equipment item = new Equipment(name, description, null, rarity, material, modifier, slot);

        // Generate Stat
        diceRoller.reset();
        diceRoller.addDice(level * 2, 1);

        item.addStat(new Statistic(Stat.ARMOR, diceRoller.roll() + level));

        for (int i = 0 ; i < modifier.getStats().length ; i++) {
            item.addStat(new Statistic(modifier.getStats()[i], diceRoller.roll() + level));
        }

        return item;
    }

    /**
     * Generates a weapon equipment item.
     *
     * @param isMainHand
     *          Whether the weapon to generate is for the main hand or not.
     *          If not, then it's for the off hand.
     *
     * @return
     *          The generated piece of equipment.
     */
    private Equipment generateWeapon(final boolean isMainHand) {
        // Generate Misc
        final Rarity rarity = randomizeRarity();
        final Material material = randomizeMaterial();
        final EquipmentModifier modifier = randomizeModifier();
        final WeaponType weaponType = (isMainHand ? WeaponType.SWORD : WeaponType.SHIELD);

        // Generate Name
        final String name = material.getName() + " " + weaponType.getName();

        // Generate Description
        final String description = material.getName() + " " + slot.getName().toLowerCase() + ".";

        // Build Item
        final Equipment item = new Equipment(name, description, null, rarity, material, modifier, slot);

        // Generate Stat
        diceRoller.reset();

        if (isMainHand) {
            diceRoller.addDice(level * 9, 1);

            final int minDmg = diceRoller.roll() + level;
            final int maxDmg = minDmg * 4;
            item.addStat(new BoundStatistic(Stat.DAMAGE, minDmg, maxDmg));
        } else {
            diceRoller.addDice(level * 2, 1);

            item.addStat(new Statistic(Stat.ARMOR, diceRoller.roll() + level));
        }

        for (int i = 0 ; i < modifier.getStats().length ; i++) {
            item.addStat(new Statistic(modifier.getStats()[i], diceRoller.roll() + level));
        }

        return item;
    }

    /** Estimates and prints the drop chances of all rarities. */
    public void estimateRarityDropChances() {
        final double[] chances = new double[Rarity.values().length];

        for (int i = 0 ; i < 100_000 ; i++) {
            chances[randomizeRarity().ordinal()]++;
        }

        System.out.println("Rarity Drop Chances");
        for (int i = 0 ; i < chances.length ; i++) {
            chances[i] /= 1000;
            System.out.println("\t" + Rarity.values()[i] + ": " + chances[i]);
        }
    }

    /** Estimates and prints the drop chances of all materials.*/
    public void estimateMaterialDropChances() {
        final double[] chances = new double[Material.values().length];

        for (int i = 0 ; i < 100_000 ; i++) {
            chances[randomizeMaterial().ordinal()]++;
        }

        System.out.println("Material Drop Chances");
        for (int i = 0 ; i < chances.length ; i++) {
            chances[i] /= 1000;
            System.out.println("\t" + Rarity.values()[i] + ": " + chances[i]);
        }
    }

    /** Estimates and prints the drop chances of all modifiers. */
    public void estimateModifierDropChances() {
        final double[] chances = new double[EquipmentModifier.values().length];

        for (int i = 0 ; i < 100_000 ; i++) {
            chances[randomizeModifier().ordinal()]++;
        }

        System.out.println("Modifier Drop Chances");
        for (int i = 0 ; i < chances.length ; i++) {
            chances[i] /= 1000;
            System.out.println("\t" + EquipmentModifier.values()[i] + ": " + chances[i]);
        }
    }
}
