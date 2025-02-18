package pseudoble.firststeps

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import org.slf4j.LoggerFactory


object Pseudoblemod : ModInitializer {
    private val logger = LoggerFactory.getLogger("pseudoble-mod")

	override fun onInitialize() {
		TutorialItems.initialize()
		TutorialItemGroups.initialize();
	}
}

object TutorialItems {
	val CUSTOM_ITEM: CustomItem = register("custom_item", CustomItem(Item.Settings()))

	fun initialize() {
		registerFuels()
	}

	fun registerFuels() {
		FuelRegistry.INSTANCE.add(CUSTOM_ITEM, 300)
	}

	fun <T : Item> register(path: String, item: T): T {
		return Registry.register<Item, T>(Registries.ITEM, Identifier.of("pseudoble-mod", path), item)
	}

}

object TutorialItemGroups {
	val  TEST_GROUP: ItemGroup = FabricItemGroup.builder()
		.icon { ItemStack(TutorialItems.CUSTOM_ITEM) }
		.displayName(Text.translatable("itemGroup.pseudoble-mod.test_group"))
		.entries { context, entries -> entries.add(TutorialItems.CUSTOM_ITEM) }
		.build()

	fun initialize() {
		Registry.register(Registries.ITEM_GROUP, Identifier.of("pseudoble-mod", "test_group"), TEST_GROUP)
	}
}

class CustomItem(settings: Settings) : Item(settings) {
	override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
		user.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
		return TypedActionResult.success(user.getStackInHand(hand))
	}
}