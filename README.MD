# IMA

An addon for Iron's Spells N Spellbooks!

## Addons

### Spells

#### Holy

##### Hunger Restoration (ima:hunger)

Restores the hunger of a targetted player. The hunger given is calculated as so:

`floor(min(20, currentFoodLevel + spellPower))`

where `currentFoodLevel` is the players current hunger value as an int and `spellPower` is the result of `#getSpellPower()`


|Field|Value|
|-|-|
|ID|ima:hunger|
|BaseManaCost|150|
|ManaCostIncreasePerLevel|+25|
|CastingDuration|100 ticks (5 seconds)|
|BaseSpellPower|2|
|SpellPowerIncreasePerLevel|+2|
|TargetTypes|Player|
|MaxTargets|1|
|MagicSchool|Holy|
|MaxLevel|10|
|CooldownTime|700 ticks (35 seconds)|
|Minimum Rarity|EPIC|
|CastType|Long|
|Name|Hunger Restoration|
|Description|Cast to replenish hunger.|

[View the source code here](https://github.com/BradBot1/IMA/blob/master/src/main/java/fun/bb1/forge/ima/spell/HungerSpell.java)

#### Fire

##### Extinguish (ima:extinguish)

Calls `#clearFire()` on the target entity.


|Field|Value|
|-|-|
|ID|ima:extinguish|
|BaseManaCost|300|
|ManaCostIncreasePerLevel|0|
|CastingDuration|0 ticks (0 seconds)|
|BaseSpellPower|0|
|SpellPowerIncreasePerLevel|0|
|TargetTypes|Any|
|MaxTargets|1|
|MagicSchool|Fire|
|MaxLevel|1|
|CooldownTime|140 ticks (7 seconds)|
|Minimum Rarity|Rare|
|CastType|Instant|
|Name|Extinguish|
|Description|Instantly extinguish an entity.|

[View the source code here](https://github.com/BradBot1/IMA/blob/master/src/main/java/fun/bb1/forge/ima/spell/ExtinguishSpell.java)

#### Ender

##### SafeSpace (ima:safespace)

Send's all entities flying away from the caster, where the power level determines the radius to expell from.

|Field|Value|
|-|-|
|ID|ima:safespace|
|BaseManaCost|300|
|ManaCostIncreasePerLevel|+50|
|CastingDuration|0 ticks (0 seconds)|
|BaseSpellPower|5|
|SpellPowerIncreasePerLevel|+2|
|TargetTypes|Any NOT ["forge:bosses"](https://forge.gemwire.uk/wiki/Tags#Entity_Types)|
|MaxTargets|1|
|MagicSchool|Ender|
|MaxLevel|5|
|CooldownTime|1200 ticks (60 seconds)|
|Minimum Rarity|EPIC|
|CastType|Instant|
|Name|Safe Space|
|Description|Get everyone out of your bubble.|

[View the source code here](https://github.com/BradBot1/IMA/blob/master/src/main/java/fun/bb1/forge/ima/spell/SafeSpaceSpell.java)

#### Eldritch

##### Cooldown Reduction (ima:cooldownless)

Resets the targets cooldowns, all of them.

|Field|Value|
|-|-|
|ID|ima:safespace|
|BaseManaCost|375|
|ManaCostIncreasePerLevel|0|
|CastingDuration|225 ticks (11.25 seconds)|
|BaseSpellPower|0|
|SpellPowerIncreasePerLevel|0|
|TargetTypes|Player|
|MaxTargets|1|
|MagicSchool|Edritch|
|MaxLevel|1|
|CooldownTime|18000 ticks (900 seconds)|
|Minimum Rarity|LEGENDARY|
|CastType|Long|
|Name|Cooldown Reduction|
|Description|Cast oncemore.|

[View the source code here](https://github.com/BradBot1/IMA/blob/master/src/main/java/fun/bb1/forge/ima/spell/CooldownlessSpell.java)

## Thanks

This project is based upon the [template mod](https://github.com/iron431/Irons-Example-Mod) provided by [iron431](https://github.com/iron431)
