package com.yourname.securelock.network;

import com.yourname.securelock.util.PasswordRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CheckPasswordPacket {
    public static final Identifier ID = new Identifier("securelock", "check_password");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            String input = buf.readString();

            server.execute(() -> {
                if (!(player.getWorld().getBlockEntity(pos) instanceof LockableBlockEntity be)) return;

                String passwordId = be.getPasswordId();
                boolean success = PasswordRegistry.validate(passwordId, input);

                if (!success) {
                    player.closeHandledScreen();
                    // 튕겨내는 효과 (간단하게 벡터 적용)
                    player.addVelocity(player.getX() - pos.getX(), 0.5, player.getZ() - pos.getZ());
                } else {
                    // 열기 동작 처리
                    player.sendMessage(Text.literal("🔓 자물쇠가 해제되었습니다!"), false);
                }
            });
        });
    }
}
