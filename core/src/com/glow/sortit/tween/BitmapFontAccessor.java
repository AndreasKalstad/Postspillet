package com.glow.sortit.tween;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import aurelienribon.tweenengine.TweenAccessor;

public class BitmapFontAccessor implements TweenAccessor<BitmapFont> {
    public static final int POS_XY = 1;
    public static final int SCALE = 2;

	@Override
	public int getValues(BitmapFont target, int tweenType, float[] returnValues) {
		switch (tweenType) {
	        case POS_XY:                 
	        	returnValues[0] = target.getCapHeight();                
	        	//returnValues[1] = target.getY();                 
	        	return 1;             
	        case SCALE:                 
	        	returnValues[0] = target.getScaleX();    
	        	//returnValues[1] = target.getScaleY(); 
	        	return 2; 
	        }
 
        assert false;
        return -1;
	}

	@Override
	public void setValues(BitmapFont target, int tweenType, float[] newValues) {
        switch (tweenType) {
	    	case POS_XY: 
	    		break;             
	    	case SCALE: 
	    		target.setScale(newValues[0]); 
	    		break;
        }
		
	}
}
