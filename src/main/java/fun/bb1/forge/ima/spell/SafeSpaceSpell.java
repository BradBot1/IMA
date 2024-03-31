package fun.bb1.forge.ima.spell;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import fun.bb1.forge.ima.Entrypoint;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

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
public class SafeSpaceSpell extends AbstractSpell {
	
	private static final @NotNull TagKey<EntityType<?>> BOSS_TAG = ForgeRegistries.ENTITY_TYPES.tags().createTagKey(new ResourceLocation("forge", "bosses"));
	private final ResourceLocation spellId = new ResourceLocation(Entrypoint.MODID, "safespace");
	
	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel, caster), 1)));
	}

	@Internal
	public SafeSpaceSpell() {
		this.manaCostPerLevel = 50;
		this.baseSpellPower = 5;
		this.spellPowerPerLevel = 2;
		this.castTime = 0;
		this.baseManaCost = 300;
	}

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.EPIC)
			.setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
			.setMaxLevel(5)
			.setCooldownSeconds(60)
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
		return Optional.of(SoundRegistry.ABYSSAL_TELEPORT.get());
	}
	
	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.ANIMATION_INSTANT_CAST;
	}
	
	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		level.getEntities(entity, this.createBox(entity, spellLevel), e->e instanceof LivingEntity le && le.isAlive()).forEach(e -> {
			((LivingEntity)e).setLastHurtByMob(entity);
			if (e.getType().is(BOSS_TAG)) {
				if (e instanceof ServerPlayer serverPlayer) {
					serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.ima.safespace.resist", e.getDisplayName()).withStyle(ChatFormatting.RED)));
				}
				return;
			}
			e.addDeltaMovement(entity.position().subtract(e.position()).reverse().multiply(2.5, 1.5, 2.5));
		});;
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}
	
	private final @NotNull AABB createBox(@NotNull final LivingEntity entity, final int spellLevel) {
		final float radius = this.getRadius(spellLevel, entity);
		return new AABB(entity.position().add(radius, radius, radius), entity.position().subtract(radius, radius, radius));
	}
	
	private final float getRadius(final int spellLevel, @NotNull final LivingEntity caster) {
		return this.getSpellPower(spellLevel, caster);
	}
	
}