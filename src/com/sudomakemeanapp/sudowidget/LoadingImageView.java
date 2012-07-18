/*
 *   Copyright (C) 2012 John Hostile
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.sudomakemeanapp.sudowidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class LoadingImageView extends FrameLayout{

	public static final int SIZE_LARGE = 1;
	public static final int SIZE_MEDIUM = 2;
	public static final int SIZE_SMALL = 3;
	public static final int SIZE_TINY = 4;
	
	private ImageView mImageView1=null;
	private ImageView mImageView2=null;
	private Animation mAnim1=null;
	private Animation mAnim2=null;
	private boolean mAnimating=true;
		
	public LoadingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingImageView, 0, 0);
		setImageSize(a.getInt(R.styleable.LoadingImageView_liv_imageSize, LoadingImageView.SIZE_LARGE));
		
		mAnim1 = AnimationUtils.loadAnimation(context, R.anim.spinner_76_outer_holo_anim);
    	mAnim2 = AnimationUtils.loadAnimation(context, R.anim.spinner_76_inner_holo_anim);
    	mAnim1.setRepeatCount(Animation.INFINITE);
    	mAnim2.setRepeatCount(Animation.INFINITE);
    	startLoadingAnimation();	
    	
    	setImageExpandBeyondDrawableSize(a.getBoolean(R.styleable.LoadingImageView_liv_expandImageBeyondDrawableSize, false));
    	if(!a.getBoolean(R.styleable.LoadingImageView_liv_startShown, true))
    		stopLoadingAnimation();
		a.recycle();
	}
	
	/**
	 * Sets the size of the drawables used for the animation image.
	 * 
	 * LoadingImageView.SIZE_LARGE is the default.
	 * 
	 * @param size The size of the animation image drawable.
	 */
	public void setImageSize(int size){
		if(mAnimating && mImageView1!=null && mImageView2!=null){
			mImageView1.clearAnimation();
			mImageView2.clearAnimation();
		}
		this.removeAllViews();
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		switch(size){
			case SIZE_LARGE:
				mImageView1 = (ImageView) inflater.inflate(R.layout.outer_image_layout_76, null);
				mImageView2 = (ImageView) inflater.inflate(R.layout.inner_image_layout_76, null);
				break;
			case SIZE_MEDIUM:
				mImageView1 = (ImageView) inflater.inflate(R.layout.outer_image_layout_48, null);
				mImageView2 = (ImageView) inflater.inflate(R.layout.inner_image_layout_48, null);
				break;
			case SIZE_SMALL:
				mImageView1 = (ImageView) inflater.inflate(R.layout.outer_image_layout_20, null);
				mImageView2 = (ImageView) inflater.inflate(R.layout.inner_image_layout_20, null);
				break;
			case SIZE_TINY:
				mImageView1 = (ImageView) inflater.inflate(R.layout.outer_image_layout_16, null);
				mImageView2 = (ImageView) inflater.inflate(R.layout.inner_image_layout_16, null);
				break;
			default:
				mImageView1 = (ImageView) inflater.inflate(R.layout.outer_image_layout_76, null);
				mImageView2 = (ImageView) inflater.inflate(R.layout.inner_image_layout_76, null);
				break;
		}
		mImageView1.setVisibility(View.INVISIBLE);
		mImageView2.setVisibility(View.INVISIBLE);
		this.addView(mImageView1);
		this.addView(mImageView2);
		if(mAnimating){
			if(mAnim1!=null && mAnim2!=null){
				mAnim1.reset();
				mAnim2.reset();
				startLoadingAnimation();
			}
		}
	}
	
	public void stopLoadingAnimation(){
		mAnimating =false;
		mImageView1.setVisibility(View.INVISIBLE);
		mImageView2.setVisibility(View.INVISIBLE);
		mImageView1.clearAnimation();
		mImageView2.clearAnimation();
		mAnim1.reset();
		mAnim2.reset();
	}
	
	public void startLoadingAnimation(){
		mAnimating=true;
		mImageView1.setVisibility(View.VISIBLE);
		mImageView2.setVisibility(View.VISIBLE);
		mImageView1.startAnimation(mAnim1);
    	mImageView2.startAnimation(mAnim2);
	}
	
	public void setImageExpandBeyondDrawableSize(boolean shouldExpand){
		if(shouldExpand){
			mImageView1.setScaleType(ScaleType.FIT_CENTER);
			mImageView2.setScaleType(ScaleType.FIT_CENTER);
		}
		else{
			mImageView1.setScaleType(ScaleType.CENTER_INSIDE);
			mImageView2.setScaleType(ScaleType.CENTER_INSIDE);
		}
	}

}
