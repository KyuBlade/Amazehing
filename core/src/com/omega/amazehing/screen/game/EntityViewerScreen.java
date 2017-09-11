package com.omega.amazehing.screen.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.panel.Panel;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.ui.CategoryNode;
import com.omega.amazehing.ui.ComponentNode;
import com.omega.amazehing.ui.EntityNode;

public class EntityViewerScreen extends BaseScreen {

    private Engine engine;

    private ScrollPane dataScroll;
    private ScrollPane nodeScroll;
    private Tree entitiesTree;
    private Panel dataPanel;

    private ObjectMap<ViewerCategory, CategoryNode> categories = new ObjectMap<ViewerCategory, CategoryNode>();
    private ObjectMap<Entity, EntityNode> entityNodes = new ObjectMap<Entity, EntityNode>();
    private ObjectMap<Entity, ObjectMap<Class<? extends Component>, ComponentNode>> componentNodes = new ObjectMap<Entity, ObjectMap<Class<? extends Component>, ComponentNode>>();

    private ComponentMapper<ViewerCategoryComponent> categoryMapper = ComponentMapperHandler
	    .getViewerCategoryMapper();

    public EntityViewerScreen(ScreenManager screenManager, EntityEngine engine) {
	super(screenManager, 11);

	this.engine = engine;

	dataPanel = new Panel(skin);
	dataPanel.defaults().left().pad(0f, 5f, 0f, 10f).expandX();
	dataPanel.top();
	dataScroll = new ScrollPane(dataPanel, skin);
	layout.add(dataScroll).right().width(300f).expand().fill();

	entitiesTree = new Tree(skin);

	for (int i = 0; i < ViewerCategory.values().length; i++) {
	    ViewerCategory _category = ViewerCategory.values().clone()[i];
	    CategoryNode _categoryNode = new CategoryNode(_category, skin);
	    categories.put(_category, _categoryNode);
	    entitiesTree.add(_categoryNode);
	}

	dataScroll = new ScrollPane(entitiesTree, skin);
	layout.add(dataScroll).right().width(300f).expandY().fillY();

	engine.addEntityListener(new EntityListener() {

	    @Override
	    public void entityRemoved(Entity entity) {
		EntityNode _removedEntityNode = entityNodes.remove(entity);
		componentNodes.remove(entity);

		if (_removedEntityNode != null) {
		    _removedEntityNode.getParent().remove(_removedEntityNode);
		}
	    }

	    @Override
	    public void entityAdded(Entity entity) {
		EntityNode _entityNode = new EntityNode(entity, skin);
		entityNodes.put(entity, _entityNode);
		categories.get(ViewerCategory.ANY).add(_entityNode);

		checkAddedComponents(entity);

		entity.componentAdded.add(new Listener<Entity>() {

		    @Override
		    public void receive(Signal<Entity> signal, Entity object) {
			// addComponent(object);
		    }
		});
		// entity.componentRemoved.add(new Listener<EntityEvent>() {
		//
		// @Override
		// public void receive(Signal<EntityEvent> signal, EntityEvent event) {
		// Entity _entity = event.getEntity();
		// Component _component = event.getComponent();
		//
		// removeComponent(_entity, _component);
		// }
		// });
	    }
	});
    }

    private void checkAddedComponents(Entity entity) {
	ImmutableArray<Component> _components = entity.getComponents();
	for (int i = 0; i < _components.size(); i++) {
	    Component _component = _components.get(i);
	    addComponent(entity, _component);
	}
    }

    private void addComponent(Entity entity, Component component) {
	EntityNode _entityNode = entityNodes.get(entity);
	if (_entityNode == null) {
	    return;
	}

	if (component instanceof ViewerCategoryComponent) {
	    ViewerCategory _category = ((ViewerCategoryComponent) component).getCategory();
	    CategoryNode _categoryNode = categories.get(_category);
	    _entityNode.getParent().remove(_entityNode);
	    _categoryNode.add(_entityNode);
	}

	ObjectMap<Class<? extends Component>, ComponentNode> _componentNodes = componentNodes
		.get(entity);
	if (_componentNodes == null) {
	    _componentNodes = new ObjectMap<Class<? extends Component>, ComponentNode>();
	    componentNodes.put(entity, _componentNodes);
	}

	Class<? extends Component> _compClass = component.getClass();
	if (!_componentNodes.containsKey(_compClass)) {
	    ComponentNode _componentNode = new ComponentNode(component, skin);
	    _componentNodes.put(component.getClass(), _componentNode);
	    _entityNode.add(_componentNode);
	}
    }

    private void removeComponent(Entity entity, Component component) {
	EntityNode _entityNode = entityNodes.get(entity);
	if (_entityNode == null) {
	    return;
	}

	if (component instanceof ViewerCategoryComponent) {
	    ViewerCategory _category = ViewerCategory.ANY;
	    CategoryNode _categoryNode = categories.get(_category);
	    _entityNode.getParent().remove(_entityNode);
	    _categoryNode.add(_entityNode);
	}

	ObjectMap<Class<? extends Component>, ComponentNode> _componentNodes = componentNodes
		.get(entity);
	if (_componentNodes != null) {
	    try {
		ComponentNode _componentNode = _componentNodes.remove(component.getClass());
		_componentNode.remove();
	    } catch (NullPointerException e) {
		Gdx.app.error("EntityViewerScreen",
			"Unable to remove component of type " + component.getClass()
				.getSimpleName());
		throw e;
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private String formatValue(Object value) {
	String _strValue = "";
	if (value == null) {
	    _strValue = "Null";
	} else if (value instanceof Array) {
	    StringBuilder _sbuild = new StringBuilder();
	    _sbuild.append('[');
	    Array<Object> _array = (Array<Object>) value;
	    for (int i = 0; i < _array.size; i++) {
		_sbuild.append(formatValue(_array.get(i)));
		if (i + 1 < _array.size)
		    _sbuild.append(", ");
	    }
	    _sbuild.append(']');
	    _strValue = _sbuild.toString();
	} else if (value instanceof Entity) {
	    StringBuilder _sBuild = new StringBuilder();
	    _sBuild.append("Entity [Id=").append(((Entity) value).getId()).append(']');
	    _strValue = _sBuild.toString();
	} else {
	    _strValue = value.toString();
	}

	return _strValue;
    }

    @Override
    public void render(float delta) {
	Selection<Node> _selection = entitiesTree.getSelection();
	Node _selected = _selection.first();
	if (_selected instanceof ComponentNode) {
	    Component _comp = ((ComponentNode) _selected).getComponent();

	    dataPanel.clear();
	    Field[] _fields = ClassReflection.getDeclaredFields(_comp.getClass());
	    for (int i = 0; i < _fields.length; i++) {
		Field _field = _fields[i];
		_field.setAccessible(true);
		dataPanel.add(_field.getName());
		Object _value;
		try {
		    _value = _field.get(_comp);

		    dataPanel.add(formatValue(_value));
		} catch (ReflectionException e) {
		    e.printStackTrace();
		} finally {
		    _field.setAccessible(false);
		}
		dataPanel.row();
	    }
	}
    }
}