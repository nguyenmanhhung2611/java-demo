package jp.co.transcosmos.dm3.corePana.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Panasonic共通計算Util.
 *
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   Trans	  2015.03.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaCommonUtil {
	private static final Log log = LogFactory.getLog(PanaCommonUtil.class);

	/** 現在処理中のプロパティ名 */
	private static ThreadLocal<String> currentCopyPropertyName = new ThreadLocal<String>();

	/**
	 * jspでsubmit値がrequestからformに設定する（UTF-8固定）
	 *
	 * @param request
	 *            HttpServletRequest固定
	 * @param form
	 *            ページのフォーム
	 */
	public static void creatForm(HttpServletRequest request, Object form) {
		creatForm(request, form, "utf-8");
	}

	/**
	 * jspでsubmitする値がrequestからformに設定する。
	 *
	 * Fileと普通値両方ある場合でも値が直接に設定できる。 Form:String ⇒String String[]⇒String[] File
	 * ⇒File File[] ⇒File[]
	 *
	 * @param request
	 *            HttpServletRequest固定
	 * @param form
	 *            ページのフォーム
	 * @param encode
	 *            コード転換
	 */
	public static void creatForm(HttpServletRequest request, Object form,
			String encode) {

		// 「multipart/form-data」含むフォームかの判断
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) { // 「multipart/form-data」含むフォームの場合
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding(encode);

			List<FileItem> fileList = new ArrayList<FileItem>();
			try {
				fileList = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				log.debug("creatForm error : " + e1.getMessage());
				e1.printStackTrace();
			}

			Iterator<FileItem> it = fileList.iterator();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) { // fileの場合
					log.debug("creatForm info : " + item.getFieldName() + " : "
							+ item.getName());
				} else { // form valueの場合
					try {
						log.debug("creatForm info : " + item.getFieldName()
								+ " : " + item.getString(encode));
					} catch (UnsupportedEncodingException e) {
						log.debug("creatForm error : " + e.getMessage());
						e.printStackTrace();
					}
				}
			}

			log.debug("=====================================");

			String[] allFormFieldNames = ReflectionUtils.getAllFieldNamesByGetters(form.getClass());

			for (int i = 0; i < allFormFieldNames.length; i++) {
				try {
					log.debug("creatForm info : " + allFormFieldNames[i] + " : "
							+ ReflectionUtils.getFieldTypeByGetter(form.getClass(), allFormFieldNames[i]).toString());
				} catch (NoSuchMethodException e) {
					log.debug("creatForm error : " + e.getMessage());
					e.printStackTrace();
				}
			}
			List<FileItem> itemList = null;
			List<String> stringList = null;

			FileItem[] itemArray = null;
			String[] stringArray = null;
			for (int i = 0; i < allFormFieldNames.length; i++) {
				try {
					if (ReflectionUtils.getFieldTypeByGetter(form.getClass(), allFormFieldNames[i]).toString()
							.startsWith("class [")) { // arrayの場合
						log.debug("creatForm info : this is a array");

						itemList = new ArrayList<FileItem>();
						stringList = new ArrayList<String>();

						for (int j = 0; j < fileList.size(); j++) {
							if (fileList.get(j).getFieldName().equals(allFormFieldNames[i])) {
								if (fileList.get(j).isFormField()) {
									// form valueの場合
									try {
										stringList.add(fileList.get(j).getString(encode));
									} catch (UnsupportedEncodingException e) {
										log.debug("creatForm error : " + e.getMessage());
										e.printStackTrace();
									}
								} else {// fileの場合
									itemList.add(fileList.get(j));
								}
							}
						}

						if (itemList != null && itemList.size() != 0) {
							// file arrayある場合
							itemArray = new FileItem[itemList.size()];
							for (int k = 0; k < itemList.size(); k++) {
								itemArray[k] = itemList.get(k);
							}
							try {
								ReflectionUtils.setFieldValueBySetter(form, allFormFieldNames[i], itemArray);
							} catch (NoSuchMethodException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							}
						}

						if (stringList != null && stringList.size() != 0) {
							// form value arrayある場合
							stringArray = new String[stringList.size()];
							for (int k = 0; k < stringList.size(); k++) {
								stringArray[k] = stringList.get(k);
							}
							try {
								ReflectionUtils.setFieldValueBySetter(form, allFormFieldNames[i], stringArray);
							} catch (NoSuchMethodException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								log.debug("creatForm error : " + e.getMessage());
								e.printStackTrace();
							}
						}

					} else {
						// 単一値の場合
						for (int j = 0; j < fileList.size(); j++) {
							if (fileList.get(j).getFieldName().equals(allFormFieldNames[i])) {
								try {
									log.debug("creatForm info : " + fileList.get(j).getString(encode));
								} catch (UnsupportedEncodingException e) {
									log.debug("creatForm error : " + e.getMessage());
									e.printStackTrace();
								}

								try {
									if (fileList.get(j).isFormField()) {
										try {
											ReflectionUtils.setFieldValueBySetter(form, allFormFieldNames[i],
													fileList.get(j).getString(encode));
										} catch (UnsupportedEncodingException e) {
											log.debug("creatForm error : " + e.getMessage());
											e.printStackTrace();
										}
									} else {
										ReflectionUtils.setFieldValueBySetter(form, allFormFieldNames[i],
												fileList.get(j));
									}

								} catch (NoSuchMethodException e) {
									log.debug("creatForm error : " + e.getMessage());
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									log.debug("creatForm error : " + e.getMessage());
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									log.debug("creatForm error : " + e.getMessage());
									e.printStackTrace();
								}
							}
						}

					}
				} catch (NoSuchMethodException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		} else {// 「multipart/form-data」含めないフォームの場合
			FormPopulator.populateFormBeanFromRequest(request, form);
		}
	}

	/**
	 * site.propertiesからkey対する値取得（廃止）
	 *
	 * @param obj
	 *            関数コールするクラス本体
	 * @param key
	 *            キー値
	 * @return
	 */
	//    public static String getSiteProperty(Object obj, String key) {
	//        InputStream inputStream = obj.getClass().getClassLoader().getResourceAsStream("site.properties");
	//        Properties properties = new Properties();
	//
	//        try {
	//            properties.load(inputStream);
	//        } catch (Exception e) {
	//            log.debug("getProperty error : " + e.getMessage());
	//            return "";
	//        }
	//
	//        return properties.getProperty(key);
	//    }

	/**
	 * オブジェクト値のコピー
	 *
	 * @param dest
	 *            コピー先
	 * @param src
	 *            コピー元
	 */
	public static void copyProperties(Object dest, Object src) {
		copyProperties(dest, src, false);
	}

	/**
	 * オブジェクト値のコピー
	 *
	 * @param dest
	 *            コピー先
	 * @param src
	 *            コピー元
	 * @param ignoreError
	 *            エラーを無視するかどうか<br />
	 *            trueの場合無視する
	 */
	public static void copyProperties(Object dest, Object src,
			boolean ignoreError) {

		try {
			// Validate existence of the specified beans
			if (dest == null) {
				throw new IllegalArgumentException("No destination bean specified");
			}
			if (src == null) {
				throw new IllegalArgumentException("No origin bean specified");
			}

			// Copy the properties, converting as necessary
			if (src instanceof DynaBean) {
				DynaProperty[] srcDescriptors = ((DynaBean) src).getDynaClass().getDynaProperties();
				for (int i = 0; i < srcDescriptors.length; i++) {
					String name = srcDescriptors[i].getName();
					if (PropertyUtils.isReadable(src, name) && PropertyUtils.isWriteable(dest, name)) {
						Object value = ((DynaBean) src).get(name);
						if (ignoreError) {
							try {
								copyProperty(dest, name, value);
							} catch (Exception e) {
							}
						} else {
							copyProperty(dest, name, value);
						}
					}
				}
			} else if (src instanceof Map) {
				Iterator<?> entries = ((Map<?, ?>) src).entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entries.next();
					String name = (String) entry.getKey();
					if (PropertyUtils.isWriteable(dest, name)) {
						if (ignoreError) {
							try {
								copyProperty(dest, name, entry.getValue());
							} catch (Exception e) {
							}
						} else {
							copyProperty(dest, name, entry.getValue());
						}
					}
				}
			} else /* if (orig is a standard JavaBean) */{
				PropertyDescriptor[] srcDescriptors = PropertyUtils.getPropertyDescriptors(src);
				for (int i = 0; i < srcDescriptors.length; i++) {
					String name = srcDescriptors[i].getName();
					if ("class".equals(name)) {
						continue; // No point in trying to set an object's class
					}
					if (PropertyUtils.isReadable(src, name) && PropertyUtils.isWriteable(dest, name)) {
						Object value = PropertyUtils.getSimpleProperty(src, name);
						if (ignoreError) {
							try {
								copyProperty(dest, name, value);
							} catch (Exception e) {
							}
						} else {
							copyProperty(dest, name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * オブジェクト値のコピー
	 *
	 * @param dest
	 *            コピー先
	 * @param name
	 *            プロパティ名
	 * @param value
	 *            プロパティ値
	 */
	public static void copyProperty(Object dest, String name, Object value) {
		try {
			currentCopyPropertyName.set(name);
			BeanUtils.copyProperty(dest, name, value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			currentCopyPropertyName.remove();
		}
	}
}
