package com.scs.spectrumarcade.entities;

import com.atr.jme.font.TrueTypeFont;
import com.atr.jme.font.TrueTypeMesh;
import com.atr.jme.font.asset.TrueTypeKeyMesh;
import com.atr.jme.font.shape.TrueTypeContainer;
import com.atr.jme.font.util.StringContainer;
import com.atr.jme.font.util.Style;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IHudItem;

public class HUDTextEntity extends AbstractEntity implements IEntity, IHudItem, IProcessable {

	private TrueTypeContainer textArea;
	private float duration;

	public HUDTextEntity(SpectrumArcade game, String text, int size, ColorRGBA col, float x, float y, float _duration) {
		super(game, "HUDTextEntity");
		
		duration = _duration;

		TrueTypeKeyMesh ttkSmall = new TrueTypeKeyMesh("Fonts/zx_spectrum-7.ttf", Style.Bold, (int)size);
		TrueTypeFont ttfSmall = (TrueTypeMesh)game.getAssetManager().loadAsset(ttkSmall);
		textArea = ttfSmall.getFormattedText(new StringContainer(ttfSmall, text), col);
		textArea.setLocalTranslation(x, y, 0);
	}


	public void setText(String s) {
		textArea.setText(s);
	}


	@Override
	public void process(float tpfSecs) {
		duration -= tpfSecs;
		if (duration < 0) {
			this.markForRemoval();
		}
	}


	@Override
	public void actuallyRemove() {
		textArea.removeFromParent();

	}


	@Override
	public Spatial getSpatial() {
		return textArea;
	}


}
