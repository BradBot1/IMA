package fun.bb1.forge.ima.spell;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.CastTargetingData;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.spells.TargetedTargetAreaCastData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
@Internal
abstract class SingleTargetSpell extends AbstractSpell {
	
	protected boolean playerOnly = false;
	
	@Override
	public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
		if (!(level instanceof final ServerLevel serverLevel)) {
			playerMagicData.resetAdditionalCastData();
			return false;
		}
		if (!Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, 0.5f, false)) {
			playerMagicData.setAdditionalCastData(new CastTargetingData(entity));
			if (entity instanceof ServerPlayer serverPlayer) {
				serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success_self", this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
			}
		}
		if (!(playerMagicData.getAdditionalCastData() instanceof final CastTargetingData targetData)) {
			playerMagicData.resetAdditionalCastData();
			return false;
		}
		final LivingEntity target = targetData.getTarget(serverLevel);
		if (this.playerOnly && !(target instanceof Player)) {
			playerMagicData.resetAdditionalCastData();
			return false;
		}
		playerMagicData.setAdditionalCastData(new TargetedTargetAreaCastData(target, TargetedAreaEntity.createTargetAreaEntity(serverLevel, target.position(), 1.25f, Utils.packRGB(this.getTargetingColor()), target)));
		return super.checkPreCastConditions(serverLevel, spellLevel, entity, playerMagicData);
	}
	
	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		if (!(level instanceof final ServerLevel serverLevel)) {
			super.onCast(level, spellLevel, entity, castSource, playerMagicData);
			return;
		}
		if (playerMagicData.getAdditionalCastData() instanceof final TargetedTargetAreaCastData targetData) {
			final LivingEntity target = targetData.getTarget(serverLevel);
			if (target == null) {
				super.onCast(level, spellLevel, entity, castSource, playerMagicData);
				return;
			}
			if (this.playerOnly && !(target instanceof Player)) {
				super.onCast(level, spellLevel, entity, castSource, playerMagicData);
				return;
			}
			this.onCast(serverLevel, spellLevel, entity, playerMagicData, target);
			super.onCast(level, spellLevel, entity, castSource, playerMagicData);
		}
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}
	
	public abstract void onCast(@NotNull final ServerLevel level, final int spellLevel, @NotNull final LivingEntity caster, @NotNull final MagicData casterMagicData, @NotNull final LivingEntity target);
	
}