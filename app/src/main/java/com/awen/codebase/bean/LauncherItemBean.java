package com.awen.codebase.bean;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

public class LauncherItemBean {
	private Drawable icon;
	private String name;
	private ComponentName component;

	public LauncherItemBean(Drawable d, String s, ComponentName cn) {
		icon = d;
		name = s;
		component = cn;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComponentName getComponent() {
		return component;
	}

	public void setComponent(ComponentName component) {
		this.component = component;
	}
	
};