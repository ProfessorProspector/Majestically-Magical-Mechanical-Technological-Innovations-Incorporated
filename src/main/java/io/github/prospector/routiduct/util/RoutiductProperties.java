package io.github.prospector.routiduct.util;

import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class RoutiductProperties {
	public static final EnumProperty<NullableDirection> DIRECTION_1 = EnumProperty.create("direction_1", NullableDirection.class);
	public static final EnumProperty<NullableDirection> DIRECTION_2 = EnumProperty.create("direction_2", NullableDirection.class);
	public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
	public static final BooleanProperty PUSHING = BooleanProperty.create("pushing");
}
