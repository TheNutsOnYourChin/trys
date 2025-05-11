package com.app.trys.exceptions;

import com.app.trys.utils.ReflectUtils;
import lombok.Getter;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class TryCatchProxy implements InvocationHandler {
	/**
	 * 目标对象
	 */
	@Getter
	private final Object target;

	public TryCatchProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			System.out.println("受Jdk代理的bean：" + Arrays.toString(proxy.getClass().getGenericInterfaces()) + " method：" + method.getName());
			return method.invoke(target, args);
		} catch (Exception e) {
			Exception exception = e;
			// 如果是 InvocationTargetException 异常，则其中的 target 才是实际的异常
			if (exception instanceof InvocationTargetException) {
				exception = (Exception) ((InvocationTargetException) e).getTargetException();
			}
			this.errorDialog(exception);

			Class<?> returnType = method.getReturnType();
			return Objects.equals(returnType, Void.TYPE) ? null : ReflectUtils.newInstance(returnType);
		}
	}


	private void errorDialog(Exception e) {
		JOptionPane.showConfirmDialog(null,
				ExceptionUtils.buildStackInfo(e),
				"发生一个异常",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE);
	}

}