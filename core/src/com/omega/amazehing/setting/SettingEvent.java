package com.omega.amazehing.setting;

public interface SettingEvent<T> {

    void apply(String key, T value);

    void reset(String key, T oldValue);
}