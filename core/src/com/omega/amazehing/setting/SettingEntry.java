package com.omega.amazehing.setting;

public class SettingEntry<T> {

    private T value;
    private SettingEvent<T> event;

    public SettingEntry(SettingEvent<T> event) {
	this.event = event;
    }

    public SettingEntry(T value) {
	this.value = value;
    }

    public T getValue() {
	return value;
    }

    public void setValue(T value) {
	this.value = value;
    }

    public void setEvent(SettingEvent<T> event) {
	this.event = event;
    }

    public void apply(String key, T value) {
	if (event != null) {
	    event.apply(key, value);
	}
    }

    public void reset(String key, T value) {
	if (event != null) {
	    event.reset(key, value);
	}
    }
}