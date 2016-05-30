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


// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� start
    public static void populateFormBeanFromRequest(HttpServletRequest request, Object[] forms,
            int maxFileSize, File tempDirectory) {

    	// �}���`�p�[�g�̏ꍇ�A�A�b�v���[�h�t�@�C���̃��X�g���擾
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
        	// �}���`�p�[�g�̏ꍇ�A�A�b�v���[�h�t�@�C���̃��X�g���擾
            return upload.parseRequest(request);

        } catch (FileUploadException e) {
            throw new RuntimeException("Error processing multipart upload", e);
		}

    }
// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� end
    
    
    
    /**
     * Uses reflection to try to populate the form bean attributes from the request objects.
     * Form bean attributes should be 
     * - Strings for normal parameters, or
     * - A pair of byte[] (for file contents) and String (for filename) for multipart file uploads  
     */
// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� start
//    public static void populateFormBeanFromRequest(HttpServletRequest request, Object form,
//            int maxFileSize, File tempDirectory) {
    protected static void populateFormBeanFromRequest(HttpServletRequest request, Object form,
    		int maxFileSize, File tempDirectory, List<FileItem> targetItems) {
// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� end
    	
    	if (request == null) {
            log.warn("WARNING: request is null !!");
        } else if (form == null) {
            log.warn("WARNING: form is null !!");
        } else {

        	FormPopulationFilterChain chain = null;

            if (form instanceof FormPopulationFilterable) {

// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//
// FormPopulationFilterChain �́AFileItem�@�̔z��v���p�e�B�ɑΉ����Ă��Ȃ��B
// �����ŁA�t�H�[���̃C���^�[�t�F�[�X���@FileArrayFormPopulationFilterable�@�̏ꍇ��
// FileItem�@�̔z��v���p�e�B�Ή��Ɋg������ FileArrayFormPopulationFilterChain
// ���g�p����p�ɋ@�\�g�����s�����B
// ����āAFileItem�@�̔z��v���p�e�B���g�p����ꍇ�ŁA�t�H�[���̃t�B���^�[���g�p����ꍇ��
// FormPopulationFilterable �̑���ɁAFileArrayFormPopulationFilterable
// ���g�p����K�v������B
//
//                chain = new FormPopulationFilterChain();
            	if (form instanceof FileArrayFormPopulationFilterable) {
            		chain = new FileArrayFormPopulationFilterChain();
            	} else {
            		chain = new FormPopulationFilterChain();
            	}
// 2015/03/24 H.Mizuno FileItem �z��Ή� end

            	((FormPopulationFilterable) form).initializeFormPopulationFilters(chain);
                ((FormPopulationFilterable) form).initializeFormWithDefaults();
            }
            
            // �t�H�[���ɒ�`����Ă���S�Ẵt�B�[���h�����擾
            String allFormFieldNames[] = ReflectionUtils.getAllFieldNamesByGetters(
                    form.getClass());

            if (ServletFileUpload.isMultipartContent(new ServletRequestContext(request))) {
                String encoding = request.getCharacterEncoding();
                // If multipart, iterate over request parameters using Commons-multipart

// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� start
//                DiskFileItemFactory factory = new DiskFileItemFactory();
//                factory.setSizeThreshold(maxFileSize);
//                factory.setRepository(tempDirectory == null ? 
//                        new File(System.getProperty("java.io.tmpdir")) : tempDirectory);
//                ServletFileUpload upload = new ServletFileUpload(factory);
// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� end
                try {

// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� start
//                  List<FileItem> items = upload.parseRequest(request);
                    List<FileItem> items = targetItems;
                    if (targetItems == null){
                    	items = getMultiPartItems(request, maxFileSize, tempDirectory);
                    }
// 2015.05.01 H.Mizuno �}���`�p�[�g�Ή��Ƃ��āA���� Form �̈ꊇ��M�@�\��ǉ� end


                    // �t�H�[���̃t�B�[���h�����A�l�̐ݒ���J��Ԃ�
                    for (int n = 0; n < allFormFieldNames.length; n++) {

                    	// Find the field

// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//
// �I���W�i���̃R�[�h�ł́A�t�B�[���h���ɊY�������ԍŏ��Ɍ������� FileItem �݂̂��g�p����B
// ����̏C���ł́A�z��Ƃ��Ď󂯎���l�ɂ���ׁA�t�B�[���h���ɊY������ FileItem �I�u�W�F�N�g��
// List �I�u�W�F�N�g�ŊǗ�����l�ɕύX���Ă���B
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
// 2015/03/24 H.Mizuno FileItem �z��Ή� end
                        
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
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//
// �A�b�v���[�h�t�@�C���ɊY������t�H�[���̃t�B�[���h���� String �^�̏ꍇ�A���̃t�@�C���̖{����
// �i�[�����d�l�ɂȂ��Ă���B
// ����ł͔z��Œ�`����Ă��Ă��ŏ��Ɍ��������t�@�C���P�����l���ݒ肳��Ȃ��B
// �t�B�[���h�ɊY������ FileItem �̐��ɉ������z����쐬���A���̐��� String �ϊ����J��Ԃ�
// �l�ɏ�����ύX�����B
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
// 2015/03/24 H.Mizuno FileItem �z��Ή� end

                            if (chain != null) {
                                values = chain.filterString(allFormFieldNames[n], values);
                            }
                            setStringAttribute(allFormFieldNames[n], values, form);
                            
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
// 
// ���X�AFileItem �̔z��͍l������Ă��Ȃ������̂ŁA�z��p�̏�����ǉ������B
// ���̃C���^�[�t�F�[�X�Ƃ̌݊��̖�������̂ŁAFileItem �P�Ƃ̃t�B�[���h��`�̏ꍇ�A
// �]���̃C���^�[�t�F�[�X�Ńt�B���^�[���`�F�[������B
// �t�H�[���̃t�B�[���h�� FileItem �̔z��̏ꍇ�A�g�����ꂽ�z��Ή����\�b�h���g�p����B
//
                        } else if (clsFormField.isAssignableFrom(FileItem[].class)) {
                        	// �v���p�e�B�� FileItem�@�̔z��̏ꍇ

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
// 2015/03/24 H.Mizuno FileItem �z��Ή� end

                        } else {
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//
// �]���� FileItem �Ɋւ��鏈�����B
// �t�B�[���h���ɊY������ FilteItem �� List �I�u�W�F�N�g�ɕύX���ꂽ�ׁA�ŏ��̂P���g�p
// ���ē����悤�Ƀ��[�J���ϐ��̏�����������ǉ��B
//
                        	FileItem item = null;
                        	if (itemArray.size() > 0){
                        		item = itemArray.get(0);
                        	}
// 2015/03/24 H.Mizuno FileItem �z��Ή� end
                            if (chain != null) {
                                item = chain.filterFileItem(allFormFieldNames[n], item);
                            }
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//
// �t�H�[���� FileItem ���i�[��������I�ȏ����̌Ăяo���B
// FileItem ��z��œn����l�Ƀ��\�b�h�̃C���^�[�t�F�[�X��ύX�����B
// ���̏����́A�z��ł͂Ȃ� FileItem �t�B�[���h�ւ̒l�̐ݒ�Ȃ̂ŁA�����ƂȂ�z��́A�P�ڂ�
// �l�����ݒ肳��Ă��Ȃ��B
//
//                            setFileAttribute(allFormFieldNames[n], item, form);
                            setFileAttribute(allFormFieldNames[n], new FileItem[]{item}, form);
// 2015/03/24 H.Mizuno FileItem �z��Ή� end
                        }
                    }
                } catch (IOException err) {
                    throw new RuntimeException("Error processing multipart upload", err);
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
//                } catch (FileUploadException err) {
//                    throw new RuntimeException("Error processing multipart upload", err);
// 2015/03/24 H.Mizuno FileItem �z��Ή� end
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
// 2015/03/24 H.Mizuno FileItem �z��Ή� start
    private static final Class<?>[] MULTI_FILEITEM_ARRAY = new Class[] {FileItem[].class}; 
// 2015/03/24 H.Mizuno FileItem �z��Ή� end
    
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
    
// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//    private static void setFileAttribute(String name, FileItem fileItem, Object form) {
    private static void setFileAttribute(String name, FileItem[] fileItems, Object form) {
// 2015/03/25 H.Mizuno FileItem �z��Ή� end

// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//    	if (fileItem == null) {
       	if (fileItems == null || fileItems.length == 0) {
// 2015/03/25 H.Mizuno FileItem �z��Ή� end
            return;
        }

        // Set the filename
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name + "FileName"), SINGLE_STRING_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + "FileName as uploaded file name");
// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//
// �����́A�t�@�C��������p�̃t�B�[���h�� String �^�Œ�`����Ă����ꍇ�A�t�@�C������ݒ肷�鏈�����L�q
// ����Ă���B�@���X�A�P�ڂ� FileItem ���g�p���Ă��Ȃ��̂ŁA�z��̍ŏ��̃t�@�C����ݒ肷��悤�ɕύX�B
//
//                methSetter.invoke(form, new Object[] {fileItem.getName()});
                methSetter.invoke(form, new Object[] {fileItems[0].getName()});
// 2015/03/25 H.Mizuno FileItem �z��Ή� end
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }

// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//
// �t�@�C�����̎擾�������A�z��ɂ͑Ή����Ă��Ȃ������B
// �z��̏ꍇ�A�t�@�C�����̔z����i�[����悤�ɋ@�\��ǉ������B
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
// 2015/03/25 H.Mizuno FileItem �z��Ή� end
        
        // Set the byte content
        try {
            Method methSetter = form.getClass().getMethod(
                    ReflectionUtils.buildSetterName(name), MULTI_BYTE_ARRAY);
            if (methSetter != null) {
                log.debug("Populating form element " + name + " as uploaded file content");
// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//
// �o�C�g�z��Ŏ󂯎��ꍇ�̏����B ������̔z��Ή��͑z�肵�Ă��Ȃ��̂ŁA�]���Ƃ���A�ŏ��Ɍ�������
// FileItem ���i�[����悤�ɏ�����ύX�B
//
//                methSetter.invoke(form, new Object[] {fileItem.get()});
                methSetter.invoke(form, new Object[] {fileItems[0].get()});
// 2015/03/25 H.Mizuno FileItem �z��Ή� end
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
// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//
// FilteItem �v���p�e�B���g�p�����A�]���̏��������B
// �]���Ƃ���A�ŏ��ɂ݂����� FileItem ���i�[����悤�ɕύX�B
// 
//                methSetter.invoke(form, new Object[] {fileItem});
                methSetter.invoke(form, new Object[] {fileItems[0]});
// 2015/03/25 H.Mizuno FileItem �z��Ή� end
            }
        } catch (NoSuchMethodException err) {
        } catch (Throwable err) {
            throw new RuntimeException("Error populating the form: field=" + name, err);
        }
        
// 2015/03/25 H.Mizuno FileItem �z��Ή� start
//
// FileItem �̔z��Ή��ׂ̈ɒǉ����������B
// �v���p�e�B�ɁA�z��̒l�����̂܂ܐݒ肷��B
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
// 2015/03/25 H.Mizuno FileItem �z��Ή� end

    }
}
