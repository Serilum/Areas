package com.natamus.areas.functions;

import com.natamus.collective.functions.NumberFunctions;
import com.natamus.collective.functions.SignFunctions;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneFunctions {
    public static final List<String> zonePrefixes = new ArrayList<String>(Arrays.asList("[na]", "[area]", "[region]", "[zone]"));

	public static boolean hasZonePrefix(String line) {
		for (String prefix : zonePrefixes) {
			if (line.toLowerCase().startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasZonePrefix(SignBlockEntity signentity) {
		int i = -1;
		for (String line : SignFunctions.getSignText(signentity)) {
			i += 1;

			if (i == 0 && hasZonePrefix(line)) {
				return true;
			}
			break;
		}

		return false;
	}

	public static Integer getZonePrefixgetRadius(String line) {
		for (String prefix : zonePrefixes) {
			if (line.startsWith(prefix)) {
				String[] linespl = line.split("]");
				if (linespl.length < 2) {
					return -1;
				}

				String rest = linespl[1].trim();
				if (NumberFunctions.isNumeric(rest)) {
					return Integer.parseInt(rest);
				}
			}
		}

		return -1;
	}

	public static String getZoneRGB(String line) {
		String prefix = "[rgb]";
		if (line.startsWith(prefix)) {
			String[] linespl = line.split("]");
			if (linespl.length < 2) {
				return "";
			}

			String rest = linespl[1].replace(" ", "");
			String[] restspl = rest.split(",");
			if (restspl.length != 3) {
				return "";
			}

			for (String value : restspl) {
				if (!NumberFunctions.isNumeric(value)) {
					return "";
				}
			}
			return rest;
		}
		return "";
	}
}
