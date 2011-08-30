package com.gmail.zariust.odspecials;

import java.util.Arrays;
import java.util.List;

import com.gmail.zariust.otherdrops.special.SpecialResult;
import com.gmail.zariust.otherdrops.special.SpecialResultHandler;

public class ExplosionEvents extends SpecialResultHandler {
	@Override
	public SpecialResult getNewEvent(String name) {
		if(name.equalsIgnoreCase("EXPLOSION")) return new ExplodeEvent(this);
		return null;
	}
	
	@Override
	public void onLoad() {
		logInfo("Explosions v" + getVersion() + " loaded.");
	}
	
	@Override
	public List<String> getEvents() {
		return Arrays.asList("EXPLOSION");
	}
	
	@Override
	public String getName() {
		return "Explosions";
	}
	
}