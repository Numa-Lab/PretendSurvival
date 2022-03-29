package net.numalab.pretend_survival

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.item.ItemBuilder
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.holder
import com.github.bun133.guifly.infinite.InfiniteGUIBuilder
import com.github.bun133.guifly.item
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class PSGUI(val psConfig: PSConfig) {
    fun open(plugin: JavaPlugin, player: Player) {
        val gui = InfiniteGUIBuilder()
        gui.setTitle(Component.text("クリエイティブ"))
        gui.setType(InventoryType.CHEST_6)
        gui.holder(player)
        gui.setMarkedNotInsertable()

        setItems(gui, getCreativeItems())

        gui.build(plugin).open(player)
    }

    private fun getCreativeItems(): List<ItemStack> {
        return (Material.values().toMutableList() - psConfig.getItem().toSet())
            .filter { it.isItem }
            .map { ItemStack(it) }
    }

    private fun setItems(builder: InfiniteGUIBuilder, materials: List<ItemStack>) {
        materials.forEachIndexed { index: Int, itemStack: ItemStack ->
            val y = index / 8
            val x = index % 8

            builder.item(x, y) {
                stack(itemStack)
                click { onClick(it, itemStack) }
                markAsUnMovable()
            }
        }
    }

    private fun onClick(e: InventoryClickEvent, itemStack: ItemStack) {
        e.whoClicked.inventory.addItem(itemStack)
    }
}