package womp.wild.boars.client.states;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import womp.wild.boars.entity.Boar.BoarVariant;

public class BoarRenderState extends LivingEntityRenderState {

    public boolean isAttacking;
    public BoarVariant variant;
    
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState scratchLeftEarAnimationState = new AnimationState();
    public final AnimationState scratchRightEarAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState sniffAnimationState = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
}
