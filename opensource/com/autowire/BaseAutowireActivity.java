package com.autowire;

import android.app.Activity;
import android.os.Bundle;

/**
 * Provided BaseActivity for use of AndroidAutowire annotations. <br /><br />
 * Use of this class means that you do not need to provide your own custom BaseActivity to
 * integrate with the AndroidAutowire library.
 * @author Jacob Kanipe-Illig (jkanipe-illig@cardinalsolutions.com)
 * Copyright (c) 2013
 */
public abstract class BaseAutowireActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		AndroidAutowire.loadFieldsFromBundle(savedInstanceState, this, BaseAutowireActivity.class);
		
		int layoutId = AndroidAutowire.getLayoutResourceByAnnotation(this, this, BaseAutowireActivity.class);
		//If this activity is not annotated with AndroidLayout, do nothing
		if(layoutId == 0){
			return;
		}
		setContentView(layoutId);
		afterAutowire(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID){
		super.setContentView(layoutResID);
		//autowire the AndroidView fields
		AndroidAutowire.autowire(this, BaseAutowireActivity.class);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		AndroidAutowire.saveFieldsToBundle(outState, this, BaseAutowireActivity.class);
	}
	
	/**
	 * This method will be called after views are autowired by AndroidAutowire
	 * and after the layout is created. <strong>This method will only be called when the
	 * {@link ALayout} annotation is used</strong> to load the layout resource for the Activity.
	 * <br /><br />
	 * This method can be used as a substitute for {@code onCreate()}, as actually overriding
	 * {@code onCreate()} is not necessary when this base class does it for you. Activity set up
	 * that is usually done in {@code onCreate()} can be done in this method instead.
	 */
	protected abstract void afterAutowire(Bundle savedInstanceState);
}
