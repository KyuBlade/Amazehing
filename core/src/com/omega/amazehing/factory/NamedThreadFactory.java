package com.omega.amazehing.factory;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private String name;
    private ThreadGroup group;

    public NamedThreadFactory(String name) {
	this.name = name;

	SecurityManager _secureMgr = System.getSecurityManager();
	group = (_secureMgr != null) ? _secureMgr.getThreadGroup() : Thread.currentThread()
		.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
	Thread t = new Thread(group, r, name, 0);

	if (!t.isDaemon()) {
	    t.setDaemon(true);
	}
	if (t.getPriority() != Thread.NORM_PRIORITY)
	    t.setPriority(Thread.NORM_PRIORITY);
	return t;
    }

}
