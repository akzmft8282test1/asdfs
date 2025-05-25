package com.yourname.securelock.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class KeyItem extends Item {
    public KeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!world.isClient) {
            // 향후 열쇠 인증 로직 연결
            context.getPlayer().sendMessage(Text.literal("🔑 열쇠를 사용했습니다."), false);
        }

        return ActionResult.SUCCESS;
    }
}
