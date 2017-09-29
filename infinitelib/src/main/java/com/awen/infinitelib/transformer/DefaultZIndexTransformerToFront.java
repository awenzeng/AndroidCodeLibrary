package com.awen.infinitelib.transformer;


import com.awen.infinitelib.CardItem;
import com.awen.infinitelib.ZIndexTransformer;

/**
 * @author BakerJ
 */
public class DefaultZIndexTransformerToFront implements ZIndexTransformer {
    @Override
    public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
        if (fraction < 0.5f) {
            card.zIndex = 1f + 0.01f * fromPosition;
        } else {
            card.zIndex = 1f + 0.01f * toPosition;
        }
    }

    @Override
    public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

    }
}
