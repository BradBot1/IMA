package fun.bb1.forge.ima.registry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import fun.bb1.forge.ima.Entrypoint;
import fun.bb1.forge.ima.spell.CooldownlessSpell;
import fun.bb1.forge.ima.spell.ExtinguishSpell;
import fun.bb1.forge.ima.spell.HungerSpell;
import fun.bb1.forge.ima.spell.SafeSpaceSpell;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

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
public final class SpellRegistry {
	
	private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, Entrypoint.MODID);
	
	@Internal
	public static final void register(@NotNull final IEventBus eventBus) {
		SPELLS.register(eventBus);
	}
	
	@Internal
	public static final RegistryObject<AbstractSpell> registerSpell(@NotNull final AbstractSpell spell) {
		return SPELLS.register(spell.getSpellName(), ()->spell);
	}

	public static final @NotNull RegistryObject<AbstractSpell> HOLY_HUNGER = registerSpell(new HungerSpell());
	public static final @NotNull RegistryObject<AbstractSpell> FIRE_EXTINGUISH = registerSpell(new ExtinguishSpell());
	public static final @NotNull RegistryObject<AbstractSpell> ENDER_SAFE_SPACE = registerSpell(new SafeSpaceSpell());
	public static final @NotNull RegistryObject<AbstractSpell> ELDRITCH_COOLDOWNLESS = registerSpell(new CooldownlessSpell());

}