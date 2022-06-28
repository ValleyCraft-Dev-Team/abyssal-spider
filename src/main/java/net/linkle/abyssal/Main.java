package net.linkle.abyssal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.linkle.abyssal.init.Entities;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	public static final String ID = "abyssal_spider";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
	    Entities.init();
	}
	
	public static Identifier makeId(String id) {
	    return new Identifier(ID, id);
	}
}
