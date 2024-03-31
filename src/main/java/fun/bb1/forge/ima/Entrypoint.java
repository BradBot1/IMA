package fun.bb1.forge.ima;

import fun.bb1.forge.ima.registry.SpellRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
@Mod(Entrypoint.MODID)
public class Entrypoint {

	public static final String MODID = "ima";

	public Entrypoint() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		SpellRegistry.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
	}

}