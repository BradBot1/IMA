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
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

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
public class ExtinguishSpell extends SingleTargetSpell {
	
	private final ResourceLocation spellId = new ResourceLocation(Entrypoint.MODID, "extinguish");
	
	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of();
	}

	@Internal
	public ExtinguishSpell() {
		this.manaCostPerLevel = 0;
		this.baseSpellPower = 0;
		this.spellPowerPerLevel = 0;
		this.castTime = 0;
		this.baseManaCost = 300;
		this.playerOnly = false;
	}

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.RARE)
			.setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
			.setMaxLevel(1)
			.setCooldownSeconds(7)
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
		return CastType.INSTANT;
	}

	@Override
	public Optional<SoundEvent> getCastStartSound() {
		return Optional.of(SoundRegistry.FIRE_CAST.get());
	}
	
	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.ANIMATION_INSTANT_CAST;
	}

	@Override
	public void onCast(@NotNull ServerLevel level, int spellLevel, @NotNull LivingEntity caster, @NotNull MagicData casterMagicData, @NotNull LivingEntity target) {
		target.clearFire();
	}
	
}