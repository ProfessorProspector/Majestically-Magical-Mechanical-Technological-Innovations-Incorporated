package io.github.prospector.routiduct.api;

public enum PackageColor {
	TEAL("color.routiduct.teal", 0xFF00FFFA, 0xFF00D4D0, 18),
	SKY("color.routiduct.sky", 0xFF009EFF, 0xFF0083D4, 24),
	BLUE("color.routiduct.blue", 0xFF2100FF, 0xFF1C00D4, 30),
	PURPLE("color.routiduct.purple", 0xFF9000FF, 0xFF7800D4, 36),
	PINK("color.routiduct.pink", 0xFFF200FF, 0xFFC900D4, 42),
	RED("color.routiduct.red", 0xFFFF0800, 0xFFD40700, 48),
	ORANGE("color.routiduct.orange", 0xFFFF6A00, 0xFFD45800, 54),
	YELLOW("color.routiduct.yellow", 0xFFFFE100, 0xFFD4BB00, 60),
	LIME("color.routiduct.lime", 0xFF80FF00, 0xFF6AD400, 66),
	GREEN("color.routiduct.green", 0xFF21A943, 0xFF0B7C27, 72),
	BLACK("color.routiduct.black", 0xFF191919, 0xFF000000, 78),
	GREY("color.routiduct.grey", 0xFF666666, 0xFF484848, 84),
	SILVER("color.routiduct.silver", 0xFFB2B2B2, 0xFF949494, 90),
	WHITE("color.routiduct.white", 0xFFFFFFFF, 0xFFDEDEDE, 96);

	public final int color;
	public final int altColor;
	public final int textureY;
	public String translationKey;

	PackageColor(String translationKey, int color, int altColor, int textureY) {
		this.translationKey = translationKey;
		this.color = color;
		this.altColor = altColor;
		this.textureY = textureY;
	}
}
