package RenderTweaks.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Shadow @Final private static Map<String, Integer> CATEGORY_ORDER_MAP;
    @Shadow private InputUtil.Key boundKey;
    @Shadow @Final private static Map<String, KeyBinding> KEYS_BY_ID;
    @Shadow @Final private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;

    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void onClassInitInjectAtReturn(CallbackInfo ci) {
        CATEGORY_ORDER_MAP.put("RenderTweaks", CATEGORY_ORDER_MAP.size() + 1);
    }

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/util/InputUtil$Type;ILjava/lang/String;)V", at = @At(value = "RETURN"))
    private void onClassInitInjectAtReturn(String translationKey, InputUtil.Type type, int code, String category, CallbackInfo ci) {
        System.out.println(translationKey + "\n");
    }

    @Inject(method = "setKeyPressed", at = @At(value = "RETURN"))
    private static void injectPrint(InputUtil.Key key, boolean pressed, CallbackInfo ci) {
        Map<String, KeyBinding> a = KEYS_BY_ID;
        Map<InputUtil.Key, KeyBinding> b = KEY_TO_BINDINGS;
    }
}
