package fun.bb1.forge.ima.spell;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import fun.bb1.forge.ima.Entrypoint;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 *    Copyright 2024 BradBot_1#2042
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
@AutoSpellConfig
public class HungerSpell extends SingleTargetSpell {
	
	private final ResourceLocation spellId = new ResourceLocation(Entrypoint.MODID, "hunger");
	
	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui.ima.hunger", Utils.stringTruncation(getSpellPower(spellLevel, caster), 1)));
	}

	@Internal
	public HungerSpell() {
		this.manaCostPerLevel = 25;
		this.baseSpellPower = 2;
		this.spellPowerPerLevel = 2;
		this.castTime = 100;
		this.baseManaCost = 150;
		this.playerOnly = true;
	}

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.EPIC)
			.setSchoolResource(SchoolRegistry.HOLY_RESOURCE)
			.setMaxLevel(10)
			.setCooldownSeconds(35)
			.build();

	@Override
	public ResourceLocation getSpellResource() {
		return this.spellId;
	}

	@Override
	public DefaultConfig getDefaultConfig() {
		return this.defaultConfig;
	}

	@Override
	public CastType getCastType() {
		return CastType.LONG;
	}

	@Override
	public Optional<SoundEvent> getCastStartSound() {
		return Optional.of(SoundRegistry.HOLY_CAST.get());
	}
	
	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.ANIMATION_LONG_CAST;
	}

	@Override
	public void onCast(@NotNull ServerLevel level, int spellLevel, @NotNull LivingEntity caster, @NotNull MagicData casterMagicData, @NotNull LivingEntity target) {
		if (target instanceof final Player player) {
			player.getFoodData().setFoodLevel((int)Math.floor(Math.min(player.getFoodData().getFoodLevel() + this.getSpellPower(spellLevel, caster), 20)));
		}
	}
	
}