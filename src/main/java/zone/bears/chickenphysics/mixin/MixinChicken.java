package zone.bears.chickenphysics.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

@Mixin(Chicken.class)
public abstract class MixinChicken {
	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Chicken;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
	private ItemEntity ChickenDropItemReplacement(Chicken entity, ItemLike stack) {
		if (entity.hasCustomName() && (entity.getCustomName().getString().equals("Dinnerbone")
				|| entity.getCustomName().getString().equals("Grumm"))) {
			ThrownEgg ee = new ThrownEgg(entity.level(), entity);
			ee.setItem(new ItemStack(Items.EGG));
			ee.shootFromRotation(entity, -90f, entity.getYRot(), 0f, 1.5f, 1f);
			entity.level().addFreshEntity(ee);
			return null;
		}
		return entity.spawnAtLocation(stack);
	}
}
