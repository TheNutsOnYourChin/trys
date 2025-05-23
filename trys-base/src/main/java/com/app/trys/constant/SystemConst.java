package com.app.trys.constant;

/**
 * @author linjf
 * @since 2023/2/28
 */
public class SystemConst {
	/**
	 *  all to suppress all warnings （抑制所有警告）
	 * 	boxing to suppress warnings relative to boxing/unboxing operations（抑制装箱、拆箱操作时候的警告）
	 * 	cast to suppress warnings relative to cast operations （抑制映射相关的警告）
	 * 	dep-ann to suppress warnings relative to deprecated annotation（抑制启用注释的警告）
	 * 	deprecation to suppress warnings relative to deprecation（抑制过期方法警告）
	 * 	fallthrough to suppress warnings relative to missing breaks in switch statements（抑制确在switch中缺失breaks的警告）
	 * 	finally to suppress warnings relative to finally block that don’t return （抑制finally模块没有返回的警告）
	 * 	hiding to suppress warnings relative to locals that hide variable（）
	 * 	incomplete-switch to suppress warnings relative to missing entries in a switch statement (enum case)(忽略没有完整的switch语句)
	 * 	nls to suppress warnings relative to non-nls string literals(忽略非nls格式的字符)
	 *  null to suppress warnings relative to null analysis(忽略对null的操作)
	 * 	rawtypes to suppress warnings relative to un-specific types when using generics on class params(使用generics时忽略没有指定相应的类型)
	 *  restriction to suppress warnings relative to usage of discouraged or forbidden references
	 * 	serial to suppress warnings relative to missing serialVersionUID field for a serializable class(忽略在serializable类中没有声明serialVersionUID变量)
	 * 	static-access to suppress warnings relative to incorrect static access（抑制不正确的静态访问方式警告)
	 * 	synthetic-access to suppress warnings relative to unoptimized access from inner classes（抑制子类没有按最优方法访问内部类的警告）
	 * 	unchecked to suppress warnings relative to unchecked operations（抑制没有进行类型检查操作的警告）
	 * 	unqualified-field-access to suppress warnings relative to field access unqualified （抑制没有权限访问的域的警告）
	 * 	unused to suppress warnings relative to unused code  （抑制没被使用过的代码的警告）
	 */
	public static final String ALL = "all";
	public static final String BOXING = "boxing";
	public static final String CAST = "cast";
	public static final String DEP_ANN = "dep-ann";
	public static final String DEPRECATION = "deprecation";
	public static final String FALLTHROUGH = "fallthrough";
	public static final String FINALLY = "finally";
	public static final String HIDING = "hiding";
	public static final String INCOMPLETE_SWITCH = "incomplete-switch";
	public static final String NLS = "nls";
	public static final String NULL = "null";
	public static final String RAW_TYPES = "rawtypes";
	public static final String RESTRICTION = "restriction";
	public static final String SERIAL = "serial";
	public static final String STATIC_ACCESS = "static-access";
	public static final String SYNTHETIC_ACCESS = "synthetic-access";
	public static final String UNCHECKED = "unchecked";
	public static final String UNQUALIFIED_FIELD_ACCESS = "unqualified-field-access";
	public static final String UNUSED = "unused";
}
