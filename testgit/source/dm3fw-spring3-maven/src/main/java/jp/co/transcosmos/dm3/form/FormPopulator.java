/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.servlet.PatternMatchedRequestWrapper;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class for populating form beans from servlet requests. Call 
 * <code>FormPopulator.populateFormBeanFromRequest(request, form)</code> to
 * transfer the request parameters into the form fields as matched by name 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: FormPopulator.java,v 1.6 2007/05/31 10:18:26 rick Exp $
 */
public class FormPopulator {
    private static final Log log = LogFactory.getLog(FormPopulator.class);


// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 start
    public static void populateFormBeanFromRequest(HttpServletRequest request, Object[] forms,
            int maxFileSize, File tempDirectory) {

    	// マルチパートの場合、アップロードファイルのリストを取得
        List<FileItem> items = getMultiPartItems(request, maxFileSize, tempDirectory);

    	for (Object form : forms) {
        	populateFormBeanFromRequest(request, form, maxFileSize, tempDirectory, items);
    	}

    }

    

    public static void populateFormBeanFromRequest(HttpServletRequest request, Object form,
            int maxFileSize, File tempDirectory) {

    	populateFormBeanFromRequest(request, form, maxFileSize, tempDirectory, null);
    }



    private static List<FileItem> getMultiPartItems(HttpServletRequest request, int maxFileSize, File tempDirectory){

    	DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxFileSize);
        factory.setRepository(tempDirectory == null ? 
                new File(System.getProperty("java.io.tmpdir")) : tempDirectory);
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
        	// マルチパートの場合、アップロードファイルのリストを取得
            return upload.parseRequest(request);

        } catch (FileUploadException e) {
            throw new RuntimeException("Error processing multipart upload", e);
		}

    }
// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 end
    
    
    
    /**
     * Uses reflection to try to populate the form bean attributes from the request objects.
     * Form bean attributes should be 
     * - Strings for normal parameters, or
     * - A pair of byte[] (for file contents) and String (for filename) for multipart file uploads  
     */
// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 start
//    public static void populateFormBeanFromRequest(HttpServletRequest request, Object form,
//            int maxFileSize, File tempDirectory) {
    protected static void populateFormBeanFromRequest(HttpServletRequest request, Object form,
    		int maxFileSize, File tempDirectory, List<FileItem> targetItems) {
// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 end
    	
    	if (request == null) {
            log.warn("WARNING: request is null !!");
        } else if (form == null) {
            log.warn("WARNING: form is null !!");
        } else {

        	FormPopulationFilterChain chain = null;

            if (form instanceof FormPopulationFilterable) {

// 2015/03/24 H.Mizuno FileItem 配列対応 start
//
// FormPopulationFilterChain は、FileItem　の配列プロパティに対応していない。
// そこで、フォームのインターフェースが　FileArrayFormPopulationFilterable　の場合は
// FileItem　の配列プロパティ対応に拡張した FileArrayFormPopulationFilterChain
// を使用する用に機能拡張を行った。
// よって、FileItem　の配列プロパティを使用する場合で、フォームのフィルターを使用する場合は
// FormPopulationFilterable の代わりに、FileArrayFormPopulationFilterable
// を使用する必要がある。
//
//                chain = new FormPopulationFilterChain();
            	if (form instanceof FileArrayFormPopulationFilterable) {
            		chain = new FileArrayFormPopulationFilterChain();
            	} else {
            		chain = new FormPopulationFilterChain();
            	}
// 2015/03/24 H.Mizuno FileItem 配列対応 end

            	((FormPopulationFilterable) form).initializeFormPopulationFilters(chain);
                ((FormPopulationFilterable) form).initializeFormWithDefaults();
            }
            
            // フォームに定義されている全てのフィールド名を取得
            String allFormFieldNames[] = ReflectionUtils.getAllFieldNamesByGetters(
                    form.getClass());

            if (ServletFileUpload.isMultipartContent(new ServletRequestContext(request))) {
                String encoding = request.getCharacterEncoding();
                // If multipart, iterate over request parameters using Commons-multipart

// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 start
//                DiskFileItemFactory factory = new DiskFileItemFactory();
//                factory.setSizeThreshold(maxFileSize);
//                factory.setRepository(tempDirectory == null ? 
//                        new File(System.getProperty("java.io.tmpdir")) : tempDirectory);
//                ServletFileUpload upload = new ServletFileUpload(factory);
// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 end
                try {

// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 start
//                  List<FileItem> items = upload.parseRequest(request);
                    List<FileItem> items = targetItems;
                    if (targetItems == null){
                    	items = getMultiPartItems(request, maxFileSize, tempDirectory);
                    }
// 2015.05.01 H.Mizuno マルチパート対応として、複数 Form の一括受信機能を追加 end


                    // フォームのフィールド名分、値の設定を繰り返す
                    for (int n = 0; n < allFormFieldNames.length; n++) {

                    	// Find the field

// 2015/03/24 H.Mizuno FileItem 配列対応 start
//
// オリジナルのコードでは、フィールド名に該当する一番最初に見つかった FileItem のみを使用する。
// 今回の修正では、配列として受け取れる様にする為、フィールド名に該当する FileItem オブジェクトを
// List オブジェクトで管理する様に変更している。
//
//                        FileItem item = null;
//                        for (Iterator<FileItem> i = items.iterator(); i.hasNext() && (item == null); ) {
//                            FileItem candidate = i.next();
//                            if (candidate.getFieldName().equals(allFormFieldNames[n])) {
//                                item = candidate;
//                            }
//                        }
//
                    	List<FileItem> itemArray = new ArrayList<>();
                    	for (FileItem candidate : items) {
                    		if (candidate.getFieldName().equals(allFormFieldNames[n])) {
                    			itemArray.add(candidate);
                    		}
                    	}
// 2015/03/24 H.Mizuno FileItem 配列対応 end
                        
                        Class<?> clsFormField = null;
                        try {
                            clsFormField = ReflectionUtils.getFieldTypeByGetter(
                                form.getClass(), allFormFieldNames[n]);
                        } catch (NoSuchMethodException err) {
                            throw new RuntimeException("Unknown member field", err);
                        }
                        
                        // If string or string array field, use string setter
                        if (clsFormField.isAssignableFrom(String.class) ||
                                (clsFormField.isArray() && clsFormField.getComponentType().
                                        isAssignableFrom(String.class))) {

                        	String values[] = null;
// 2015/03/24 H.Mizuno FileItem 配列対応 start
//
// アップロードファイルに該当するフォームのフィールド名が String 型の場合、そのファイルの本文が
// 格納される仕様になっている。
// 現状では配列で定義されていても最初に見つかったファイル１個しか値が設定されない。
// フィールドに該当する FileItem の数に応じた配列を作成し、その数分 String 変換を繰り返す
// 様に処理を変更した。
//
//                            if (item != null) {
//                                values = new String[] {encoding == null ? item.getString() :
//                                    item.getString(encoding)};
//                            }
//
                        	if (itemArray.size() > 0) {
                        		values = new String[itemArray.size()];
                        		int i = 0;
                        		for (FileItem item : itemArray){
                        			if (item != null){
                        				values[i] = encoding == null ? item.getString() : item.getString(encoding);
                        			}
                                    ++i;
                        		}
                            }
// 2015/03/24 H.Mizuno FileItem 配列対応 end

                            if (chain != null) {
                                values = chain.filterString(allFormFieldNames[n], values);
                            }
                            setStringAttribute(allFormFieldNames[n], values, form);
                            
// 2015/03/24 H.Mizuno FileItem 配列対応 start
// 
// 元々、FileItem の配列は考慮されていなかったので、配列用の処理を追加した。
// 元のインターフェースとの互換の問題もあるので、FileItem 単独のフィールド定義の場合、
// 従来のインターフェースでフィルターをチェーンする。
// フォームのフィールドが FileItem の配列の場合、拡張された配列対応メソッドを使用する。
//
                        } else if (clsFormField.isAssignableFrom(FileItem[].class)) {
                        	// プロパティが FileItem　の配列の場合

                        	FileItem[] values = new FileItem[itemArray.size()];
                        	int i = 0;
                        	for (FileItem item : itemArray){
                        		values[i] = item;
                        		++i;
                        	}

                            if (chain != null) {
                                values = ((FileArrayFormPopulationFilterChain)chain).filterFileItem(allFormFieldNames[n], values);
                            }
                            setFileAttribute(allFormFieldNames[n], values, form);
// 2015/03/24 H.Mizuno FileItem 配列対応 end

                        } else {
// 2015/03/24 H.Mizuno FileItem 配列対応 start
//
// 従来の FileItem に関する処理部。
// フィールド名に該当する FilteItem が List オブジェクトに変更された為、最初の１個を使用
// して動くようにローカル変数の初期化処理を追加。
//
                        	FileItem item = null;
                        	if (itemArray.size() > 0){
                        		item = itemArray.get(0);
                        	}
// 2015/03/24 H.Mizuno FileItem 配列対応 end
                            if (chain != null) {
                                item = chain.filterFileItem(allFormFieldNames[n], item);
                            }
// 2015/03/24 H.Mizuno FileItem 配列対応 start
//
// フォームへ FileItem を格納する実質的な処理の呼び出し。
// FileItem を配列で渡せる様にメソッドのインターフェースを変更した。
// この処理は、配列ではない FileItem フィールドへの値の設定なので、引数となる配列は、１個目の
// 値しか設定されていない。
//
//                            setFileAttribute(allFormFieldNames[n], item, form);
                            setFileAttribute(allFormFieldNames[n], new FileItem[]{item}, form);
// 2015/03/24 H.Mizuno FileItem 配列対応 end
                        }
                    }
                } catch (IOException err) {
                    throw new RuntimeException("Error processing multipart upload", err);
// 2015/03/24 H.Mizuno FileItem 配列対応 start
//                } catch (FileUploadException err) {
//                    throw new RuntimeException("Error processing multipart upload", err);
// 2015/03/24 H.Mizuno FileItem 配列対応 end
                }
                
                // Check if the request was a pattern matched request wrapper,
                // and additionally populate any params from there, since common-fileupload will 
                // ignore these.
                if (request instanceof PatternMatchedRequestWrapper) {
                    String pairs[][] = ((PatternMatchedRequestWrapper) request).getParameterPairs();
                    for (int n = 0; n < pairs.length; n++) {
                        setStringAttribute(pairs[n][0], new String[] {pairs[n][1]}, form);
                    }
                }
                
            } else {
                // Else iterate over request parameters and set on form
                for (int n = 0; n < allFormFieldNames.length; n++) {
                    String values[] = request.getParameterValues(allFormFieldNames[n]);
                    if (chain != null) {
                        values = chain.filterString(allFormFieldNames[n], values);
                    }
                    setStringAttribute(allFormFieldNames[n], values, form);
                }
            }
        }
    }
    
    public static void populateFormBeanFromRequest(HttpServletRequest request, Object form) {
        populateFormBeanFromRequest(request, form, 1024 * 1024, null); // default 1M max
    }
    
    private static final Class<?>[] SINGLE_STRING_ARRAY = new Class[] {String.class}; 
    private static final Class<?>[] MULTI_STRING_ARRAY = new Class[] {String[].class}; 
    private static final Class<?>[] MULTI_BYTE_ARRAY = new Class[] {byte[].class}; 
    private static final Class<?>[] SINGLE_FILEITEM_ARRAY = new Class[] {FileItem.class}; 
// 2015/03/24 H.Mizuno FileItem 配列対応 start
    private static final Class<?>[] MULTI_FILEITEM_ARRAY = new Class[] {FileItem[].class}; 
// 2015/03/24 H.Mizuno FileItem 配列対応 end
    
    private static void setStringAttribute(String name, String values[], Object form) {
        if (values == null) {
            return;
        }
        // If null or single string, try single string first
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), MULTI_STRING_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as String array");
                methSetter.invoke(form, new Object[] {values});
                return;
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), SINGLE_STRING_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as String");
                methSetter.invoke(form, new Object[] {values == null ? null : values[0]});
                return;
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
    }
    
// 2015/03/25 H.Mizuno FileItem 配列対応 start
//    private static void setFileAttribute(String name, FileItem fileItem, Object form) {
    private static void setFileAttribute(String name, FileItem[] fileItems, Object form) {
// 2015/03/25 H.Mizuno FileItem 配列対応 end

// 2015/03/25 H.Mizuno FileItem 配列対応 start
//    	if (fileItem == null) {
       	if (fileItems == null || fileItems.length == 0) {
// 2015/03/25 H.Mizuno FileItem 配列対応 end
            return;
        }

        // Set the filename
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name + "FileName"), SINGLE_STRING_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + "FileName as uploaded file name");
// 2015/03/25 H.Mizuno FileItem 配列対応 start
//
// ここは、ファイル名受取り用のフィールドが String 型で定義されていた場合、ファイル名を設定する処理が記述
// されている。　元々、１個目の FileItem か使用していないので、配列の最初のファイルを設定するように変更。
//
//                methSetter.invoke(form, new Object[] {fileItem.getName()});
                methSetter.invoke(form, new Object[] {fileItems[0].getName()});
// 2015/03/25 H.Mizuno FileItem 配列対応 end
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }

// 2015/03/25 H.Mizuno FileItem 配列対応 start
//
// ファイル名の取得処理も、配列には対応していなかった。
// 配列の場合、ファイル名の配列を格納するように機能を追加した。
//
        // Set the filename (Array)
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name + "FileName"), MULTI_STRING_ARRAY);
            if (methSetter != null) {
                String fileNames[] = new String[fileItems.length];
                int i = 0;
                for (FileItem item : fileItems){
                	fileNames[i] = item.getFieldName();
                	++i;
                }

            	log.debug("Populating form element " + name + "FileName as uploaded file name (Array)");
                methSetter.invoke(form, new Object[] {fileNames});
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
// 2015/03/25 H.Mizuno FileItem 配列対応 end
        
        // Set the byte content
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), MULTI_BYTE_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as uploaded file content");
// 2015/03/25 H.Mizuno FileItem 配列対応 start
//
// バイト配列で受け取る場合の処理。 こちらの配列対応は想定していないので、従来とおり、最初に見つかった
// FileItem を格納するように処理を変更。
//
//                methSetter.invoke(form, new Object[] {fileItem.get()});
                methSetter.invoke(form, new Object[] {fileItems[0].get()});
// 2015/03/25 H.Mizuno FileItem 配列対応 end
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
        
        // Set the file item content
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), SINGLE_FILEITEM_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as uploaded file");
// 2015/03/25 H.Mizuno FileItem 配列対応 start
//
// FilteItem プロパティを使用した、従来の処理部分。
// 従来とおり、最初にみつかった FileItem を格納するように変更。
// 
//                methSetter.invoke(form, new Object[] {fileItem});
                methSetter.invoke(form, new Object[] {fileItems[0]});
// 2015/03/25 H.Mizuno FileItem 配列対応 end
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
        
// 2015/03/25 H.Mizuno FileItem 配列対応 start
//
// FileItem の配列対応の為に追加した処理。
// プロパティに、配列の値をそのまま設定する。
//
        // Set the file item content (Array)
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), MULTI_FILEITEM_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as uploaded file (Array)");
                methSetter.invoke(form, new Object[] {fileItems});
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
// 2015/03/25 H.Mizuno FileItem 配列対応 end

    }
}
