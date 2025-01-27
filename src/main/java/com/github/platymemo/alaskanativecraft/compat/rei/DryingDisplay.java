package com.github.platymemo.alaskanativecraft.compat.rei;

import com.github.platymemo.alaskanativecraft.recipe.DryingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class DryingDisplay implements Display {
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> output;
    private final int dryTime;
    private DryingRecipe display;

    public DryingDisplay(@NotNull DryingRecipe recipe) {
        this(recipe.getIngredients(), recipe.getOutput(), recipe.getCookTime());
        this.display = recipe;
    }

    public DryingDisplay(DefaultedList<Ingredient> ingredients, ItemStack output, int dryTime) {
        this.inputs = EntryIngredients.ofIngredients(ingredients);
        this.output = Collections.singletonList(EntryIngredients.of(output));
        this.dryTime = dryTime;
    }

    public double getDryTime() {
        return dryTime;
    }

    @Override
    public @NotNull Optional<Identifier> getDisplayLocation() {
        return Optional.ofNullable(display).map(DryingRecipe::getId);
    }

    @Override
    public @NotNull List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public @NotNull List<EntryIngredient> getOutputEntries() {
        return output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AlaskaPlugin.DRYING_ID;
    }
}

