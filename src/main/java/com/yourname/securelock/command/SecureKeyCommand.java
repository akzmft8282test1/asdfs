package com.yourname.securelock.command;

import com.yourname.securelock.key.KeyRegistry;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Set;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.literal;

public class SecureKeyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("key")
            .then(literal("update")
                .then(CommandManager.argument("lockId", StringArgumentType.string())
                    .executes(ctx -> {
                        String lockId = StringArgumentType.getString(ctx, "lockId");
                        UUID newOwner = ctx.getSource().getPlayer().getUuid();
                        KeyRegistry.updateLockOwner(lockId, newOwner);
                        ctx.getSource().sendFeedback(() -> Text.literal("🔑 자물쇠 소유자가 갱신되었습니다."), false);
                        return 1;
                    })
                )
            )
            .then(literal("debug")
                .then(CommandManager.argument("lockId", StringArgumentType.string())
                    .executes(ctx -> {
                        String lockId = StringArgumentType.getString(ctx, "lockId");
                        UUID owner = KeyRegistry.getOwner(lockId);
                        ctx.getSource().sendFeedback(() -> Text.literal(
                            owner != null ?
                                "🔎 자물쇠 소유자: " + owner :
                                "❌ 자물쇠 정보를 찾을 수 없습니다."
                        ), false);
                        return 1;
                    })
                )
                .then(literal("all")
                    .executes(ctx -> {
                        UUID uuid = ctx.getSource().getPlayer().getUuid();
                        Set<String> locks = KeyRegistry.getLocks(uuid);
                        ctx.getSource().sendFeedback(() -> Text.literal(
                            "🔐 당신의 자물쇠 목록: " + String.join(", ", locks)
                        ), false);
                        return 1;
                    })
                )
            )
        );
    }
}
