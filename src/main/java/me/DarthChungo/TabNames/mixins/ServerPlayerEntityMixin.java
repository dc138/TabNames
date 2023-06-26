package me.DarthChungo.TabNames.mixins;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import me.DarthChungo.TabNames.TabNames;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
  @Inject(method = "getPlayerListName", at = @At("HEAD"), cancellable = true)
  private void styledPlayerList$changePlayerListName(CallbackInfoReturnable<Text> cir) {
    try {
      cir.setReturnValue(Placeholders.parseText(Text.of("%player:displayname%"), PlaceholderContext.of((ServerPlayerEntity)(Object)this)));
    } catch (Exception e) {
      TabNames.logger.info(e.getStackTrace().toString());
    }
  }
}
