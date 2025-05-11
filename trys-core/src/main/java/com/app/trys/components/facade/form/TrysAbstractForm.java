package com.app.trys.components.facade.form;

import com.app.trys.base.form.Ctrl;
import com.app.trys.base.form.Form;
import com.app.trys.base.form.properties.FormProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.factory.ButtonSetFactory;
import com.app.trys.components.facade.controls.factory.ControlFactory;
import com.app.trys.components.facade.form.validator.FormValidator;
import com.app.trys.components.facade.form.validator.TrysFormValidator;
import com.app.trys.config.BaseProperty;
import com.app.trys.event.EventAdapter;
import com.app.trys.event.component.OnSubmitSuccessEvent;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.generator.ModuleGenerator;
import com.app.trys.object.IdDTO;
import com.app.trys.object.Result;
import com.app.trys.util.ComponentUtils;
import com.app.trys.utils.Actions;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.SpringContextUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 流式布局表单
 * @author linjf
 * @since 2023/3/27
 */
@Getter
public abstract class TrysAbstractForm<VALUE extends IdDTO<?>> extends VBox implements BaseForm<VALUE> , EventAdapter {

	protected final FormProperties formProperties;
	private final FlowPane controlPane = ComponentUtils.createFlowPane();
	protected final Class<VALUE> formClass;
	private final VALUE dto;

	/**
	 * 所有表单控件
	 */
	protected final List<TrysControlWithLabel> controlList = new ArrayList<>();

	protected FormValidator formValidator;
	/**
	 * 功能栏，查询、新增等等
	 */
	private FlowPane buttonPane;
	/**
	 * 成功时执行
	 */
	private final List<BiConsumer<VALUE, Result>> onSuccessListeners = new ArrayList<>();

	protected List<ModuleGenerator> moduleGenerators;


	public static String baseName = "base";

	public TrysAbstractForm(Class<VALUE> formClass){
		this.moduleGenerators = SpringContextUtils.getBeans(ModuleGenerator.class);
		this.formClass = formClass;
		this.dto = ReflectUtils.newInstance(formClass);
		this.formProperties = Arrays.stream(formClass.getAnnotations())
				.filter(a -> ReflectUtils.isMethodExist(a.getClass(), baseName, Form.class))
				.findFirst()
				.map(a -> {
					Method method = ReflectUtils.getMethod(a.getClass(), baseName);
					return (Form)ReflectUtils.invoke(a, method);
				})
				.map(FormProperties::new)
				.orElseThrow(() -> ExceptionUtils.newException("找不到表单注解"));

		// 生成表单控件
		initControls();
		// 功能栏
		initBtnBar();
		// 回车即提交
		if(this.formProperties.isOnEnter()) {
			this.addEventFilter(KeyEvent.KEY_PRESSED, e -> Actions.ifTrue(e.getCode().equals(KeyCode.ENTER), this::submit));
		}
		// VBox的组件的垂直间隔
		this.setSpacing(BaseProperty.padding);


//		test();
	}

	void test(){
//		heightProperty().addListener((o, oldValue, newValue) -> {
//			System.out.println("表单宽度    " + this.getWidth() * 1.5 + " control宽度    " + this.controlPane.getWidth() * 1.5);
//			System.out.println("表单宽度Pref" + this.getPrefWidth() * 1.5 + " control宽度Pref" + this.controlPane.getPrefWidth() * 1.5);
//		});
//		widthProperty().addListener((o, oldValue, newValue) -> {
//			System.out.println("表单高度    " + this.getHeight() * 1.5 + " control高度    " + this.controlPane.getHeight() * 1.5);
//			System.out.println("表单高度Pref" + this.getPrefHeight() * 1.5 + " control高度Pref" + this.controlPane.getPrefHeight() * 1.5);
//		});
		controlPane.setStyle("-fx-background-color: #567");
		buttonPane.setStyle("-fx-background-color: #758");

	}

	private void initControls() {
		// 一个隐藏的Id输入框
		controlList.add(ControlFactory.createWithId(formClass));
		for (Field f : ReflectUtils.getFields(formClass)) {
			List<Annotation> baseList = Arrays.stream(f.getAnnotations()).filter(a -> ReflectUtils.isMethodExist(a.getClass(), "base", Ctrl.class)).collect(Collectors.toList());
			ExceptionUtils.isTure(baseList.size() <= 1, "字段{0}被重复表单控件标记", f.getName());
			baseList.forEach(base -> controlList.add(new ControlFactory(baseList.get(0)).setBindField(f).create()));
		}
		this.controlPane.getChildren().addAll(controlList);
		controlList.stream().map(ctrl -> ctrl.getCtrlProperties().getWidthWeight()).max(Integer::compareTo).ifPresent(maxWidthWeight -> {
			// 这里让表单的最小宽度设置为至少两个control的宽度，看起来更舒服
			double max = Math.max(maxWidthWeight, 2.0);
			this.controlPane.setPrefWidth((BaseProperty.controlLineW + BaseProperty.padding) * max);
		});
		// 加入到盒子
		this.getChildren().add(this.controlPane);
		// 添加校验器
		initVerifier();
	}

	public void initVerifier(){
		this.formValidator = new TrysFormValidator(this);
		this.controlList.forEach(formValidator::addControlValidator);
	}

	private void initBtnBar(){
		this.buttonPane = this.createBtnPane();
		this.getChildren().addAll(this.buttonPane);
	}

	/**
	 * 不同表单对于按钮的功能和排序都可能不一样，这里适用继承
	 */
	protected FlowPane createBtnPane() {
		FlowPane funcBar = new ButtonSetFactory().buttonSet();
		if(this.formProperties.getBtnArea().length > 0){
			List<TrysButton> btnOfModuleList = createModulesByProperties();
			funcBar.getChildren().addAll(btnOfModuleList);
		} else {
			// 默认为 确认、重置、取消按钮
			return new ButtonSetFactory().onSubmitButtonSet(e -> this.submit(), e -> this.reset(), e -> {
				getScene().getWindow().hide();
				this.reset();
			});
		}
		return funcBar;
	}

	protected final List<TrysButton> createModulesByProperties() {
		// 按DTO所属功能创建功能模块
		return Arrays.stream(this.formProperties.getBtnArea())
				.flatMap(c -> this.moduleGenerators.stream().map(mg -> mg.generate(c)))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}


	@Override
	public VALUE getValue(){
		controlList.forEach(item -> ReflectUtils.set(dto, item.getBindField(), item.getCurrentValue()));
		return dto;
	}

	/**
	 * 检查并获取表单值，检查不通过时返回空
	 */
	@Override
	public Optional<VALUE> getAndCheckValue(){
		// verify已经把值设置到dto了，所以直接返回dto就行
		return getFormValidator().verify() ? Optional.of(dto) : Optional.empty();
	}

	@Override
	public void submit() {
		getAndCheckValue().ifPresent(val -> {
			beforeSubmit(dto);
			Result result = publish(ReflectUtils.newInstance(formProperties.getEvent(), val));
			Objects.requireNonNull(result);
			publish(new OnSubmitSuccessEvent(getFormClass(), dto));
			afterSubmit(dto, result);
		});
	}

	/**
	 * 勾子-提交前
	 * @param value 表单值
	 */
	public void beforeSubmit(VALUE value){

	}

	/**
	 * 钩子-提交后
	 * @param value 表单值
	 * @param result 返回结果
	 */
	public void afterSubmit(VALUE value, Result result){
		if(result.isSuccess()){
			this.onSuccessListeners.forEach(consumer -> consumer.accept(value, result));
		}
	}

	public void addOnSuccessListener(BiConsumer<VALUE, Result> onSuccess){
		this.onSuccessListeners.add(onSuccess);
	}

	@Override
	public void reset() {
		controlList.forEach(TrysControlWithLabel::reset);
	}

	@Override
	public void setDefValue(VALUE defValue) {
		List<Field> fieldList = ReflectUtils.getFields(defValue);
		controlList.forEach(item -> fieldList.forEach(f -> {
			if(Objects.equals(item.getBindField(), f)){
				item.setDefValue(ReflectUtils.getFieldValue(f, defValue));
				item.reset();
			}
		}));
	}
}

