package com.glow.sortit.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Andreas on 27.03.2017.
 */

public class EscalatorFrontActor extends Image {

    public EscalatorFrontActor(Sprite escalatorFront, float width, float height) {
        super(escalatorFront);
        setSize(width/5, height/4.85f);
        setPosition(width-(escalatorFront.getWidth()*2.4f), height/2.37f);
    }
}
