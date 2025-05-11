package com.app.trys.components.facade.controls.base;

import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.util.Constants;
import com.app.trys.utils.Actions;
import com.app.trys.utils.ObjUtils;
import com.app.trys.utils.ReflectUtils;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 数字输入框
 *
 * @author linjf
 * @since 2023/2/24
 */
public class TrysNumberInput extends TrysTextField {

	/**
	 * 数字类型
	 */
	private final Class<?> numberType;

	private final Map<Class<?>, Supplier<?>> supportTypeMap = new HashMap<>();


	private TrysNumberInput(Class<?> numberType, Consumer<Object> onEnterPress) {
		super();
		if(supportTypeMap.isEmpty()){
			// 添加数字类型装换
			supportTypeMap.put(BigDecimal.class, () -> ObjUtils.parseIfExist(getText(), BigDecimal::new));
			supportTypeMap.put(Double.class, () -> ObjUtils.parseIfExist(getText(), Double::parseDouble));
			supportTypeMap.put(Float.class, () -> ObjUtils.parseIfExist(getText(), Float::parseFloat));
			supportTypeMap.put(Long.class, () -> ObjUtils.parseIfExist(getText(), Long::parseLong));
			supportTypeMap.put(Integer.class, () -> ObjUtils.parseIfExist(getText(), Integer::parseInt));
		}
		this.numberType = ReflectUtils.parseClassIfPrimitive(numberType);
		ExceptionUtils.isNotNull(supportTypeMap.get(numberType), "数字输入框仅支持数字类型{0}, 当前类型{1}", supportTypeMap.keySet().stream().map(Class::getSimpleName).collect(Collectors.joining(",")), numberType.getSimpleName());
		Actions.ifTrue(onEnterPress != null, () -> addOnEnterPressListener(text -> onEnterPress.accept(getCurrentValue())));

		// 只接受数字
		setTextFormatter(new TextFormatter<>(c -> {
			String newText = c.getControlNewText();
			if (newText.isEmpty()) {
				return c;
			}
			try {
				new BigDecimal(newText);
			} catch (Exception ignore){
				return null;
			}
			if(ReflectUtils.isSameClass(this.numberType, Integer.class) || ReflectUtils.isSameClass(this.numberType, Long.class)){
				// 整数则不会有小数点
				return c.getText().contains(Constants.POINT) ? null : c;
			}
			return c;
		}));
	}


	public static TrysNumberInput of(Class<?> numberType){
		return new TrysNumberInput(numberType, null);
	}

	public static TrysNumberInput of(Class<?> numberType, Consumer<Object> onEnterPress){
		Objects.requireNonNull(numberType);
        return new TrysNumberInput(numberType, onEnterPress);
	}


	@Override
	public Object getCurrentValue() {
		return supportTypeMap.get(numberType).get();
	}
}
