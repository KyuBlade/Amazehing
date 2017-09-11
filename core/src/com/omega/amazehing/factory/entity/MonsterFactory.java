package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.BodyFactory;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.btree.BehaviorTreeManager;
import com.omega.amazehing.game.ai.steering.PhysicsSteeringAgent;
import com.omega.amazehing.game.ai.steering.behavior.PrioritySteeringBehavior;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.BodyComponent;
import com.omega.amazehing.game.entity.component.LevelComponent;
import com.omega.amazehing.game.entity.component.NameComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.ai.btree.BehaviorTreeComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;
import com.omega.amazehing.game.entity.component.attack.SkillListComponent;
import com.omega.amazehing.game.entity.component.attack.state.HealthComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent.MovementType;
import com.omega.amazehing.game.entity.component.movement.SpeedComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.render.AnimationComponent;
import com.omega.amazehing.game.entity.component.render.BlendComponent;
import com.omega.amazehing.game.entity.component.render.FlipComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.render.ZIndexComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.game.entity.system.ai.BehaviorTreeSystem;

public class MonsterFactory implements Factory, Disposable {

    private AssetManager assetManager;
    private EntityEngine engine;
    private FactoryManager factoryManager;
    private Database database;

    private PreparedStatement monsterStatement;
    private PreparedStatement skillStatement;

    public MonsterFactory(AssetManager assetManager, EntityEngine engine,
	    FactoryManager factoryManager, Database database) throws SQLiteException {
	this.assetManager = assetManager;
	this.engine = engine;
	this.database = database;
	this.factoryManager = factoryManager;

	this.monsterStatement = database.getPreparedStatement("SELECT * FROM Monster WHERE ID = ?");
	this.skillStatement = database
		.getPreparedStatement("SELECT * FROM Monster_Skill WHERE Monster = ?");
    }

    public Entity createMonster(long monsterId, float x, float y) throws Exception {
	monsterStatement.setLong(1, monsterId);
	skillStatement.setLong(1, monsterId);

	BodyFactory _bodyFactory = factoryManager.getFactory(BodyFactory.class);
	SkillFactory _skillFactory = factoryManager.getFactory(SkillFactory.class);
	DatabaseCursor _monsterCursor = monsterStatement.executeQuery();
	if (_monsterCursor.next()) {
	    final Entity _monster = engine.createEntity();

	    String _name = _monsterCursor.getString(1);
	    int _level = _monsterCursor.getInt(2);
	    int _health = _monsterCursor.getInt(3);
	    float _speed = _monsterCursor.getFloat(4);
	    int _exp = _monsterCursor.getInt(5);

	    // Define the entity
	    _monster.add(engine.createComponent(ViewerCategoryComponent.class)
		    .setCategory(ViewerCategory.MONSTER))
		    .add(engine.createComponent(ViewerNameComponent.class).setName("Monster"))
		    .add(engine.createComponent(NameComponent.class).setName(_name))
		    .add(engine.createComponent(TextureComponent.class))
		    .add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		    .add(engine.createComponent(MovementTypeComponent.class)
			    .setMovementType(MovementType.ABSOLUTE))
		    .add(engine.createComponent(LevelComponent.class).setLevel(_level))
		    .add(engine.createComponent(HealthComponent.class).setMax(_health)
			    .setCurrent(_health))
		    .add(engine.createComponent(SpeedComponent.class).setSpeed(_speed))
		    .add(engine.createComponent(VelocityComponent.class))
		    .add(engine.createComponent(ZIndexComponent.class).setZindex(3))
		    .add(engine.createComponent(SizeComponent.class).setWidth(1f).setHeight(1f))
		    .add(engine.createComponent(RotationComponent.class))
		    .add(engine.createComponent(FlipComponent.class).setFlipX(true))
		    .add(engine.createComponent(BlendComponent.class).setBlending(true))
		    .add(engine.createComponent(TargetComponent.class));

	    Animation _anim = assetManager.get(Assets.Animations.Player.STAND);
	    _monster.add(engine.createComponent(AnimationComponent.class).setAnimation(_anim));

	    // Create the body
	    Body _body = _bodyFactory.createEnemy(x, y, 0.5f);

	    _monster.add(engine.createComponent(BodyComponent.class).setBody(_body));

	    // Create ai steering
	    PhysicsSteeringAgent _agent = new PhysicsSteeringAgent(_body);
	    _agent.setMaxLinearAcceleration(1f);
	    _agent.setMaxLinearSpeed(10f);
	    _agent.setMaxAngularAcceleration(1f);
	    _agent.setMaxAngularSpeed(10f);
	    _agent.setIndependentFacing(true);
	    _monster.add(engine.createComponent(SteeringComponent.class).setSteeringAgent(_agent));

	    SteeringFactory _steeringFactory = factoryManager.getFactory(SteeringFactory.class);
	    PrioritySteeringBehavior _priorityBehavior = _steeringFactory
		    .createPrioritySteering(_monster);
	    _agent.setSteeringBehavior(_priorityBehavior);

	    // Create ai behavior
	    BehaviorTreeSystem _btSystem = engine.getSystem(BehaviorTreeSystem.class);
	    BehaviorTreeManager _btManager = _btSystem.getManager();
	    _monster.add(engine.createComponent(BehaviorTreeComponent.class).setBehaviorTree(
		    _btManager.getBehaviorTree(Assets.BehaviorTrees.CLOSE_ENCOUTER)));

	    // Load skills
	    SkillListComponent _skills = engine.createComponent(SkillListComponent.class);
	    DatabaseCursor _skillCursor = skillStatement.executeQuery();
	    while (_skillCursor.next()) {
		int _skillId = _skillCursor.getInt(1);

		Entity _skill = _skillFactory.getSkill(_skillId);
		if (_skill != null) {
		    _skills.addSkill(_skill);
		}
	    }
	    _monster.add(_skills);
	    engine.addEntity(_monster);

	    _monsterCursor.close();

	    return _monster;
	} else {
	    _monsterCursor.close();

	    throw new NullPointerException("Monster " + monsterId + " not found.");
	}
    }

    @Override
    public void dispose() {
	try {
	    if (database != null) {
		database.closeDatabase();
	    }
	    if (monsterStatement != null) {
		monsterStatement.close();
	    }
	    if (skillStatement != null) {
		skillStatement.close();
	    }
	} catch (SQLiteException e) {
	}
    }
}