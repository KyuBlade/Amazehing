package com.omega.amazehing.screen;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.panel.Panel;
import com.omega.amazehing.ui.ValueNode;
import com.sun.management.GcInfo;

public class DebugScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(DebugScreen.class);

    private Label fpsLabel;
    private Label resolutionLabel;

    private Label glCallsLabel;
    private Label glCallsValue;
    private Label drawCallsLabel;
    private Label drawCallsValue;
    private Label textureBindingsLabel;
    private Label textureBindingsValue;
    private Label shaderSwitchesLabel;
    private Label shaderSwitchesValue;
    private Label verticesLabel;
    private Label verticesValue;
    private Label timePerFrameLabel;
    private Label timePerFrameValue;

    private Label gcTimeLabel;
    private Label totalThreadCountLabel;
    private Label daemonThreadCountLabel;
    private Label peakThreadCountLabel;
    private Label startedThreadCountLabel;

    private Panel graphicsPanel;
    private Panel performances;
    private Panel screensPanel;

    private Tree threadTree;
    private Tree gcTree;

    private ThreadMXBean threadBean;
    private List<GarbageCollectorMXBean> gcBeans;
    private boolean isGcMonitorable;
    private long gcTime;
    private ObjectMap<String, ObjectMap<String, ObjectMap<String, Label>>> gcMemLabels;

    private NumberFormat numberFormater;
    private float accumulator;

    public DebugScreen(ScreenManager screenManager) {
	super(screenManager, 100);

	layout.setTouchable(Touchable.childrenOnly);

	try {
	    gcBeans = (List<GarbageCollectorMXBean>) ManagementFactory.getGarbageCollectorMXBeans();
	    for (int i = 0; i < gcBeans.size(); i++) {
		com.sun.management.GarbageCollectorMXBean _sunBean = (com.sun.management.GarbageCollectorMXBean) gcBeans
			.get(i);
	    }
	    isGcMonitorable = true;
	} catch (Exception e) {
	    logger.error("Unable to get garbage collector bean", e);
	}

	numberFormater = NumberFormat.getIntegerInstance();
	numberFormater.setMinimumFractionDigits(2);
	numberFormater.setMaximumFractionDigits(2);

	fpsLabel = new Label("", skin);
	resolutionLabel = new Label("", skin);

	glCallsLabel = new Label("OpenGL calls : ", skin);
	glCallsValue = new Label("", skin);
	drawCallsLabel = new Label("Draw calls : ", skin);
	drawCallsValue = new Label("", skin);
	textureBindingsLabel = new Label("Texture bindings : ", skin);
	textureBindingsValue = new Label("", skin);
	shaderSwitchesLabel = new Label("Shader switches : ", skin);
	shaderSwitchesValue = new Label("", skin);
	verticesLabel = new Label("Vertices : ", skin);
	verticesValue = new Label("", skin);
	timePerFrameLabel = new Label("Time per frame : ", skin);
	timePerFrameValue = new Label("", skin);

	layout.left().top();

	graphicsPanel = new Panel(skin, "black_alpha");
	graphicsPanel.top().left();
	graphicsPanel.pad(5f).defaults().left();
	graphicsPanel.add(resolutionLabel).row();
	graphicsPanel.add(fpsLabel);

	layout.add(graphicsPanel).spaceBottom(15f).expandX().top().left().row();

	performances = new Panel(skin, "black_alpha");
	performances.defaults().left();
	performances.left().pad(5f);
	performances.add(glCallsLabel);
	performances.add(glCallsValue).row();
	performances.add(drawCallsLabel);
	performances.add(drawCallsValue).row();
	performances.add(textureBindingsLabel);
	performances.add(textureBindingsValue).row();
	performances.add(shaderSwitchesLabel);
	performances.add(shaderSwitchesValue).row();
	performances.add(verticesLabel);
	performances.add(verticesValue).row();
	performances.add(timePerFrameLabel);
	performances.add(timePerFrameValue).row();

	threadTree = new Tree(skin);
	totalThreadCountLabel = new Label("", skin);
	ValueNode _threadNode = new ValueNode(new Label("Total Threads : ", skin),
		totalThreadCountLabel);
	threadTree.add(_threadNode);

	daemonThreadCountLabel = new Label("", skin);
	_threadNode.add(new ValueNode(new Label("Daemons : ", skin), daemonThreadCountLabel));

	peakThreadCountLabel = new Label("", skin);
	_threadNode.add(new ValueNode(new Label("Peaks : ", skin), peakThreadCountLabel));

	startedThreadCountLabel = new Label("", skin);
	_threadNode.add(new ValueNode(new Label("Started : ", skin), startedThreadCountLabel));

	performances.add(threadTree).colspan(2).row();

	threadBean = ManagementFactory.getThreadMXBean();

	if (isGcMonitorable) {
	    gcTree = new Tree(skin);

	    gcTimeLabel = new Label("", skin);
	    ValueNode gcTimeNode = new ValueNode(new Label("GC Time : ", skin), gcTimeLabel);
	    gcTree.add(gcTimeNode);

	    gcMemLabels = new ObjectMap<String, ObjectMap<String, ObjectMap<String, Label>>>();
	    gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
	    for (int i = 0; i < gcBeans.size(); i++) {
		com.sun.management.GarbageCollectorMXBean _gcBean = (com.sun.management.GarbageCollectorMXBean) gcBeans
			.get(i);

		Node _gcNameNode = new Node(new Label(_gcBean.getName(), skin));
		gcTimeNode.add(_gcNameNode);

		ObjectMap<String, ObjectMap<String, Label>> _beanMems = new ObjectMap<String, ObjectMap<String, Label>>();
		gcMemLabels.put(_gcBean.getName(), _beanMems);

		String[] _memPoolNames = _gcBean.getMemoryPoolNames();
		for (int j = 0; j < _memPoolNames.length; j++) {
		    ObjectMap<String, Label> _memPool = new ObjectMap<String, Label>(4);
		    _beanMems.put(_memPoolNames[j], _memPool);

		    Node _memPoolNode = new Node(new Label(_memPoolNames[j], skin));
		    _gcNameNode.add(_memPoolNode);

		    Label _committedValue = new Label("", skin);
		    ValueNode _committedMem = new ValueNode(new Label("committed : ", skin),
			    _committedValue);
		    _memPoolNode.add(_committedMem);
		    _memPool.put("committed", _committedValue);

		    Label _initValue = new Label("", skin);
		    ValueNode _initMem = new ValueNode(new Label("init : ", skin), _initValue);
		    _memPoolNode.add(_initMem);
		    _memPool.put("init", _initValue);

		    Label _maxValue = new Label("", skin);
		    ValueNode _maxMem = new ValueNode(new Label("max : ", skin), _maxValue);
		    _memPoolNode.add(_maxMem);
		    _memPool.put("max", _maxValue);

		    Label _usedValue = new Label("", skin);
		    ValueNode _usedMem = new ValueNode(new Label("used : ", skin), _usedValue);
		    _memPoolNode.add(_usedMem);
		    _memPool.put("used", _usedValue);
		}
	    }
	}

	performances.add(gcTree).colspan(2).row();
	layout.add(performances).minWidth(200f).expand().top().left();

	screensPanel = new Panel(skin, "black_alpha");
	screensPanel.pad(5f).defaults().left();

	layout.add(screensPanel).minWidth(200f).bottom().right();
    }

    @Override
    public void render(float delta) {
	accumulator += delta;
	if (accumulator < 0.25f) {
	    return;
	}

	accumulator = 0f;

	fpsLabel.setText("Fps : " + Gdx.graphics.getFramesPerSecond());
	resolutionLabel.setText("Resolution : " + Gdx.graphics.getWidth() + "x" + Gdx.graphics
		.getHeight());

	glCallsValue.setText(String.valueOf(GLProfiler.calls));
	drawCallsValue.setText(String.valueOf(GLProfiler.drawCalls));
	textureBindingsValue.setText(String.valueOf(GLProfiler.textureBindings));
	shaderSwitchesValue.setText(String.valueOf(GLProfiler.shaderSwitches));
	verticesValue.setText(String.valueOf((int) GLProfiler.vertexCount.total));
	timePerFrameValue.setText(numberFormater.format(delta * 1000f) + "ms");

	totalThreadCountLabel.setText(String.valueOf(threadBean.getThreadCount()));
	daemonThreadCountLabel.setText(String.valueOf(threadBean.getDaemonThreadCount()));
	peakThreadCountLabel.setText(String.valueOf(threadBean.getPeakThreadCount()));
	startedThreadCountLabel.setText(String.valueOf(threadBean.getTotalStartedThreadCount()));

	if (isGcMonitorable) {
	    gcTime = 0L;
	    for (int i = 0; i < gcBeans.size(); i++) {
		com.sun.management.GarbageCollectorMXBean _gcBean = (com.sun.management.GarbageCollectorMXBean) gcBeans
			.get(i);
		GcInfo _gcInfo = _gcBean.getLastGcInfo();
		try {
		    gcTime += _gcInfo.getDuration();

		    Map<String, MemoryUsage> _memBeforeGc = _gcInfo.getMemoryUsageBeforeGc();
		    Map<String, MemoryUsage> _memAfterGc = _gcInfo.getMemoryUsageAfterGc();

		    ObjectMap<String, ObjectMap<String, Label>> _memPools = gcMemLabels.get(_gcBean
			    .getName());
		    String[] _memPoolNames = _gcBean.getMemoryPoolNames();
		    for (int j = 0; j < _memPoolNames.length; j++) {
			ObjectMap<String, Label> _memType = _memPools.get(_memPoolNames[j]);
			Label _committedLabel = _memType.get("committed");
			_committedLabel
				.setText(_memBeforeGc.get(_memPoolNames[j]).getCommitted() + "/" + _memAfterGc
					.get(_memPoolNames[j]).getCommitted());

			Label _initLabel = _memType.get("init");
			_initLabel
				.setText(_memBeforeGc.get(_memPoolNames[j]).getInit() + "/" + _memAfterGc
					.get(_memPoolNames[j]).getInit());

			Label _maxLabel = _memType.get("max");
			_maxLabel
				.setText(_memBeforeGc.get(_memPoolNames[j]).getMax() + "/" + _memAfterGc
					.get(_memPoolNames[j]).getMax());

			Label _usedLabel = _memType.get("used");
			_usedLabel
				.setText(_memBeforeGc.get(_memPoolNames[j]).getUsed() + "/" + _memAfterGc
					.get(_memPoolNames[j]).getUsed());
		    }

		    gcTimeLabel.setText(gcTime + "ms");
		} catch (NullPointerException e) {
		    Gdx.app.debug("GC", "GCInfo is null");
		}
	    }
	}

	screensPanel.clear();
	for (BaseScreen screen : screenManager.getScreens()) {
	    if (screen.isActive()) {
		screensPanel.add(screen.getDepth() + " : " + screen.getClass().getSimpleName())
			.left().row();
	    }
	}

	GLProfiler.reset();
    }

    @Override
    protected void show() {
	super.show();

	GLProfiler.enable();
    }

    @Override
    protected void hide() {
	super.hide();

	GLProfiler.disable();
    }
}