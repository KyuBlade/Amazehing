package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.ComponentMapper;
import com.omega.amazehing.game.entity.component.ai.InSightComponent;
import com.omega.amazehing.game.entity.component.ai.StateComponent;
import com.omega.amazehing.game.entity.component.ai.btree.BehaviorTreeComponent;
import com.omega.amazehing.game.entity.component.ai.path.LastPathfindingComponent;
import com.omega.amazehing.game.entity.component.ai.path.PathComponent;
import com.omega.amazehing.game.entity.component.ai.path.PathfindingComponent;
import com.omega.amazehing.game.entity.component.ai.path.SourceComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;
import com.omega.amazehing.game.entity.component.attack.state.HealthComponent;
import com.omega.amazehing.game.entity.component.attack.state.ManaComponent;
import com.omega.amazehing.game.entity.component.attack.state.StaminaComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent;
import com.omega.amazehing.game.entity.component.event.NotifierComponent;
import com.omega.amazehing.game.entity.component.event.SizeChangedComponent;
import com.omega.amazehing.game.entity.component.item.DestroyOnUseComponent;
import com.omega.amazehing.game.entity.component.item.InventoryComponent;
import com.omega.amazehing.game.entity.component.item.InventoryOperationComponent;
import com.omega.amazehing.game.entity.component.item.ItemTypeComponent;
import com.omega.amazehing.game.entity.component.item.QuantityComponent;
import com.omega.amazehing.game.entity.component.item.StackableComponent;
import com.omega.amazehing.game.entity.component.item.TwoHandedComponent;
import com.omega.amazehing.game.entity.component.movement.AngularVelocityComponent;
import com.omega.amazehing.game.entity.component.movement.MoveToComponent;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.entity.component.movement.MovementReferenceComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.SpeedComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.node.AttachedNodeComponent;
import com.omega.amazehing.game.entity.component.node.ParentNodeComponent;
import com.omega.amazehing.game.entity.component.render.AnimationComponent;
import com.omega.amazehing.game.entity.component.render.BlendComponent;
import com.omega.amazehing.game.entity.component.render.CameraComponent;
import com.omega.amazehing.game.entity.component.render.ColorComponent;
import com.omega.amazehing.game.entity.component.render.FlipComponent;
import com.omega.amazehing.game.entity.component.render.ShapeRenderComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.render.VerticeComponent;
import com.omega.amazehing.game.entity.component.render.ViewportComponent;
import com.omega.amazehing.game.entity.component.render.ZIndexComponent;
import com.omega.amazehing.game.entity.component.render.ZoomComponent;
import com.omega.amazehing.game.entity.component.render.particle.ParticleComponent;
import com.omega.amazehing.game.entity.component.render.text.FontComponent;
import com.omega.amazehing.game.entity.component.render.text.TextComponent;
import com.omega.amazehing.game.entity.component.skill.PassiveComponent;
import com.omega.amazehing.game.entity.component.transform.OriginComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RadiusComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerActivationComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent;

public class ComponentMapperHandler {

    // Common
    private static final ComponentMapper<ActiveComponent> activeMapper = ComponentMapper
	    .getFor(ActiveComponent.class);
    private static final ComponentMapper<BodyComponent> bodyMapper = ComponentMapper
	    .getFor(BodyComponent.class);
    private static final ComponentMapper<IdentityComponent> identityMapper = ComponentMapper
	    .getFor(IdentityComponent.class);
    private static final ComponentMapper<LevelComponent> levelMapper = ComponentMapper
	    .getFor(LevelComponent.class);
    private static final ComponentMapper<NameComponent> nameMapper = ComponentMapper
	    .getFor(NameComponent.class);
    private static final ComponentMapper<ViewerCategoryComponent> viewerCategoryMapper = ComponentMapper
	    .getFor(ViewerCategoryComponent.class);
    private static final ComponentMapper<ViewerNameComponent> viewerNameMapper = ComponentMapper
	    .getFor(ViewerNameComponent.class);
    private static final ComponentMapper<TileTypeComponent> tileTypeMapper = ComponentMapper
	    .getFor(TileTypeComponent.class);
    private static final ComponentMapper<DebugComponent> debugMapper = ComponentMapper
	    .getFor(DebugComponent.class);
    private static final ComponentMapper<InteractionComponent> interactionMapper = ComponentMapper
	    .getFor(InteractionComponent.class);
    private static final ComponentMapper<DelayComponent> delayMapper = ComponentMapper
	    .getFor(DelayComponent.class);
    private static final ComponentMapper<CallbackComponent> callbackMapper = ComponentMapper
	    .getFor(CallbackComponent.class);
    private static final ComponentMapper<TaskComponent> taskMapper = ComponentMapper
	    .getFor(TaskComponent.class);
    private static final ComponentMapper<NotifierComponent> notifierMapper = ComponentMapper
	    .getFor(NotifierComponent.class);

    // AI
    private static final ComponentMapper<InSightComponent> inSightMapper = ComponentMapper
	    .getFor(InSightComponent.class);
    private static final ComponentMapper<StateComponent> stateMapper = ComponentMapper
	    .getFor(StateComponent.class);
    private static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapper
	    .getFor(SteeringComponent.class);
    private static final ComponentMapper<BehaviorTreeComponent> behaviorTreeMapper = ComponentMapper
	    .getFor(BehaviorTreeComponent.class);

    // Pathfinding
    private static final ComponentMapper<SourceComponent> sourceMapper = ComponentMapper
	    .getFor(SourceComponent.class);
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapper
	    .getFor(TargetComponent.class);
    private static final ComponentMapper<PathfindingComponent> pathfindingMapper = ComponentMapper
	    .getFor(PathfindingComponent.class);
    private static final ComponentMapper<PathComponent> pathMapper = ComponentMapper
	    .getFor(PathComponent.class);
    private static final ComponentMapper<LastPathfindingComponent> lastPathfindingMapper = ComponentMapper
	    .getFor(LastPathfindingComponent.class);

    // Items
    private static final ComponentMapper<DestroyOnUseComponent> destroyOnUseMapper = ComponentMapper
	    .getFor(DestroyOnUseComponent.class);
    private static final ComponentMapper<ItemTypeComponent> itemTypeMapper = ComponentMapper
	    .getFor(ItemTypeComponent.class);
    private static final ComponentMapper<StackableComponent> stackableMapper = ComponentMapper
	    .getFor(StackableComponent.class);
    private static final ComponentMapper<TwoHandedComponent> twoHandedMapper = ComponentMapper
	    .getFor(TwoHandedComponent.class);
    private static final ComponentMapper<QuantityComponent> quantityMapper = ComponentMapper
	    .getFor(QuantityComponent.class);
    private static final ComponentMapper<InventoryOperationComponent> inventoryOperationMapper = ComponentMapper
	    .getFor(InventoryOperationComponent.class);
    private static final ComponentMapper<InventoryComponent> inventoryMapper = ComponentMapper
	    .getFor(InventoryComponent.class);

    // Movement
    private static final ComponentMapper<MovementDirectionComponent> movementDirectionMapper = ComponentMapper
	    .getFor(MovementDirectionComponent.class);
    private static final ComponentMapper<MovementReferenceComponent> movementReferenceMapper = ComponentMapper
	    .getFor(MovementReferenceComponent.class);
    private static final ComponentMapper<MovementTypeComponent> movementTypeMapper = ComponentMapper
	    .getFor(MovementTypeComponent.class);
    private static final ComponentMapper<MoveToComponent> moveToMapper = ComponentMapper
	    .getFor(MoveToComponent.class);
    private static final ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
	    .getFor(SpeedComponent.class);
    private static final ComponentMapper<VelocityComponent> velocityMapper = ComponentMapper
	    .getFor(VelocityComponent.class);
    private static final ComponentMapper<AngularVelocityComponent> angularVelocityMapper = ComponentMapper
	    .getFor(AngularVelocityComponent.class);

    // Nodes
    private static final ComponentMapper<AttachedNodeComponent> attachedNodeMapper = ComponentMapper
	    .getFor(AttachedNodeComponent.class);
    private static final ComponentMapper<ParentNodeComponent> parentNodeMapper = ComponentMapper
	    .getFor(ParentNodeComponent.class);

    // Render
    private static final ComponentMapper<CameraComponent> cameraMapper = ComponentMapper
	    .getFor(CameraComponent.class);
    private static final ComponentMapper<ZoomComponent> zoomMapper = ComponentMapper
	    .getFor(ZoomComponent.class);
    private static final ComponentMapper<ViewportComponent> viewportMapper = ComponentMapper
	    .getFor(ViewportComponent.class);
    private static final ComponentMapper<FlipComponent> flipMapper = ComponentMapper
	    .getFor(FlipComponent.class);
    private static final ComponentMapper<ShapeRenderComponent> shapeRenderMapper = ComponentMapper
	    .getFor(ShapeRenderComponent.class);
    private static final ComponentMapper<TextureComponent> textureRegionMapper = ComponentMapper
	    .getFor(TextureComponent.class);
    private static final ComponentMapper<ZIndexComponent> zindexMapper = ComponentMapper
	    .getFor(ZIndexComponent.class);
    private static final ComponentMapper<ColorComponent> colorMapper = ComponentMapper
	    .getFor(ColorComponent.class);
    private static final ComponentMapper<VerticeComponent> verticeMapper = ComponentMapper
	    .getFor(VerticeComponent.class);
    private static final ComponentMapper<BlendComponent> blendMapper = ComponentMapper
	    .getFor(BlendComponent.class);
    private static final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper
	    .getFor(AnimationComponent.class);
    private static final ComponentMapper<FontComponent> fontMapper = ComponentMapper
	    .getFor(FontComponent.class);
    private static final ComponentMapper<TextComponent> textMapper = ComponentMapper
	    .getFor(TextComponent.class);
    private static final ComponentMapper<ParticleComponent> particleMapper = ComponentMapper
	    .getFor(ParticleComponent.class);

    // Skill
    private static final ComponentMapper<PassiveComponent> passiveMapper = ComponentMapper
	    .getFor(PassiveComponent.class);

    // Transforms
    private static final ComponentMapper<OriginComponent> originMapper = ComponentMapper
	    .getFor(OriginComponent.class);
    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper
	    .getFor(PositionComponent.class);
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapper
	    .getFor(RotationComponent.class);
    private static final ComponentMapper<SizeComponent> sizeMapper = ComponentMapper
	    .getFor(SizeComponent.class);
    private static final ComponentMapper<RadiusComponent> radiusMapper = ComponentMapper
	    .getFor(RadiusComponent.class);

    // Triggers
    private static final ComponentMapper<TriggerActivationComponent> triggerActivationMapper = ComponentMapper
	    .getFor(TriggerActivationComponent.class);
    private static final ComponentMapper<TriggerTypeComponent> triggerTypeMapper = ComponentMapper
	    .getFor(TriggerTypeComponent.class);

    // Events
    private static final ComponentMapper<SizeChangedComponent> sizeChangedMapper = ComponentMapper
	    .getFor(SizeChangedComponent.class);

    // Attack
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper
	    .getFor(HealthComponent.class);
    private static final ComponentMapper<StaminaComponent> staminaMapper = ComponentMapper
	    .getFor(StaminaComponent.class);
    private static final ComponentMapper<ManaComponent> manaMapper = ComponentMapper
	    .getFor(ManaComponent.class);

    public static ComponentMapper<ActiveComponent> getActiveMapper() {
	return activeMapper;
    }

    public static ComponentMapper<BodyComponent> getBodyMapper() {
	return bodyMapper;
    }

    public static ComponentMapper<IdentityComponent> getIdentityMapper() {
	return identityMapper;
    }

    public static ComponentMapper<LevelComponent> getLevelMapper() {
	return levelMapper;
    }

    public static ComponentMapper<NameComponent> getNameMapper() {
	return nameMapper;
    }

    public static ComponentMapper<ViewerCategoryComponent> getViewerCategoryMapper() {
	return viewerCategoryMapper;
    }

    public static ComponentMapper<ViewerNameComponent> getViewerNameMapper() {
	return viewerNameMapper;
    }

    public static ComponentMapper<TileTypeComponent> getTileTypeMapper() {
	return tileTypeMapper;
    }

    public static ComponentMapper<DebugComponent> getDebugMapper() {
	return debugMapper;
    }

    public static ComponentMapper<InteractionComponent> getInteractionMapper() {
	return interactionMapper;
    }

    public static ComponentMapper<DelayComponent> getDelayMapper() {
	return delayMapper;
    }

    public static ComponentMapper<CallbackComponent> getCallbackMapper() {
	return callbackMapper;
    }

    public static ComponentMapper<TaskComponent> getTaskMapper() {
	return taskMapper;
    }

    public static ComponentMapper<NotifierComponent> getNotifierMapper() {
	return notifierMapper;
    }

    public static ComponentMapper<InSightComponent> getInSightMapper() {
	return inSightMapper;
    }

    public static ComponentMapper<StateComponent> getStateMapper() {
	return stateMapper;
    }

    public static ComponentMapper<SteeringComponent> getSteeringMapper() {
	return steeringMapper;
    }

    public static ComponentMapper<BehaviorTreeComponent> getBehaviorTreeMapper() {
	return behaviorTreeMapper;
    }

    public static ComponentMapper<SourceComponent> getSourceMapper() {
	return sourceMapper;
    }

    public static ComponentMapper<TargetComponent> getTargetMapper() {
	return targetMapper;
    }

    public static ComponentMapper<PathfindingComponent> getPathfindingMapper() {
	return pathfindingMapper;
    }

    public static ComponentMapper<PathComponent> getPathMapper() {
	return pathMapper;
    }

    public static ComponentMapper<LastPathfindingComponent> getLastPathfindingMapper() {
	return lastPathfindingMapper;
    }

    public static ComponentMapper<DestroyOnUseComponent> getDestroyOnUseMapper() {
	return destroyOnUseMapper;
    }

    public static ComponentMapper<ItemTypeComponent> getItemTypeMapper() {
	return itemTypeMapper;
    }

    public static ComponentMapper<StackableComponent> getStackableMapper() {
	return stackableMapper;
    }

    public static ComponentMapper<TwoHandedComponent> getTwoHandedMapper() {
	return twoHandedMapper;
    }

    public static ComponentMapper<QuantityComponent> getQuantityMapper() {
	return quantityMapper;
    }

    public static ComponentMapper<InventoryOperationComponent> getInventoryOperationMapper() {
	return inventoryOperationMapper;
    }

    public static ComponentMapper<InventoryComponent> getInventoryMapper() {
	return inventoryMapper;
    }

    public static ComponentMapper<MovementDirectionComponent> getMovementDirectionMapper() {
	return movementDirectionMapper;
    }

    public static ComponentMapper<MovementReferenceComponent> getMovementReferenceMapper() {
	return movementReferenceMapper;
    }

    public static ComponentMapper<MovementTypeComponent> getMovementTypeMapper() {
	return movementTypeMapper;
    }

    public static ComponentMapper<MoveToComponent> getMoveToMapper() {
	return moveToMapper;
    }

    public static ComponentMapper<SpeedComponent> getSpeedMapper() {
	return speedMapper;
    }

    public static ComponentMapper<VelocityComponent> getVelocityMapper() {
	return velocityMapper;
    }

    public static ComponentMapper<AngularVelocityComponent> getAngularVelocityMapper() {
	return angularVelocityMapper;
    }

    public static ComponentMapper<AttachedNodeComponent> getAttachedNodeMapper() {
	return attachedNodeMapper;
    }

    public static ComponentMapper<ParentNodeComponent> getParentNodeMapper() {
	return parentNodeMapper;
    }

    public static ComponentMapper<CameraComponent> getCameraMapper() {
	return cameraMapper;
    }

    public static ComponentMapper<ZoomComponent> getZoomMapper() {
	return zoomMapper;
    }

    public static ComponentMapper<ViewportComponent> getViewportMapper() {
	return viewportMapper;
    }

    public static ComponentMapper<FlipComponent> getFlipMapper() {
	return flipMapper;
    }

    public static ComponentMapper<ShapeRenderComponent> getShapeRenderMapper() {
	return shapeRenderMapper;
    }

    public static ComponentMapper<TextureComponent> getTextureMapper() {
	return textureRegionMapper;
    }

    public static ComponentMapper<ZIndexComponent> getZIndexMapper() {
	return zindexMapper;
    }

    public static ComponentMapper<ColorComponent> getColorMapper() {
	return colorMapper;
    }

    public static ComponentMapper<VerticeComponent> getVerticeMapper() {
	return verticeMapper;
    }

    public static ComponentMapper<BlendComponent> getBlendMapper() {
	return blendMapper;
    }

    public static ComponentMapper<AnimationComponent> getAnimationMapper() {
	return animationMapper;
    }

    public static ComponentMapper<FontComponent> getFontMapper() {
	return fontMapper;
    }

    public static ComponentMapper<TextComponent> getTextMapper() {
	return textMapper;
    }

    public static ComponentMapper<ParticleComponent> getParticleMapper() {
	return particleMapper;
    }

    public static ComponentMapper<PassiveComponent> getPassiveMapper() {
	return passiveMapper;
    }

    public static ComponentMapper<OriginComponent> getOriginMapper() {
	return originMapper;
    }

    public static ComponentMapper<PositionComponent> getPositionMapper() {
	return positionMapper;
    }

    public static ComponentMapper<RotationComponent> getRotationMapper() {
	return rotationMapper;
    }

    public static ComponentMapper<SizeComponent> getSizeMapper() {
	return sizeMapper;
    }

    public static ComponentMapper<RadiusComponent> getRadiusMapper() {
	return radiusMapper;
    }

    public static ComponentMapper<TriggerActivationComponent> getTriggerActivationMapper() {
	return triggerActivationMapper;
    }

    public static ComponentMapper<TriggerTypeComponent> getTriggerTypeMapper() {
	return triggerTypeMapper;
    }

    public static ComponentMapper<SizeChangedComponent> getSizeChangedMapper() {
	return sizeChangedMapper;
    }

    public static ComponentMapper<HealthComponent> getHealthMapper() {
	return healthMapper;
    }

    public static ComponentMapper<StaminaComponent> getStaminaMapper() {
	return staminaMapper;
    }

    public static ComponentMapper<ManaComponent> getManaMapper() {
	return manaMapper;
    }
}