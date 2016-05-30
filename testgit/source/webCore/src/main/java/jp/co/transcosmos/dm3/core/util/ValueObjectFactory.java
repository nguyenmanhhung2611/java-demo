package jp.co.transcosmos.dm3.core.util;

import java.lang.reflect.Array;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * �o���[�I�u�W�F�N�g�̃C���X�^���X���擾���� Factory �N���X.
 * <p>
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * H.Mizuno		2015.03.12	���t���N�V�����Ή��Ɏd�l��ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 * 
 */
public class ValueObjectFactory {

	private static final Log log = LogFactory.getLog(ValueObjectFactory.class);
	
	/** VO �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "valueObjectFactory";

	/** VO �̃p�b�P�[�W�K�w */
	protected String packageName = "jp.co.transcosmos.dm3.core.vo";



	/**
	 * ValueObjectFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A valueObjectFactory �Œ�`���ꂽ ValueObjectFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AvalueObjectFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * 
	 * @return ValueObjectFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static ValueObjectFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (ValueObjectFactory)springContext.getBean(ValueObjectFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * �w�肳�ꂽ�N���X���i�V���v�����j�ɊY������o���[�I�u�W�F�N�g�̃C���X�^���X�𕜋A����B<br/>
	 * <br/>
	 * �o���[�I�u�W�F�N�g���g�������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���Ċg�������N���X�𕜋A����l�ɃJ�X�^�}�C�Y���鎖�B<br/>
	 * <br/>
	 * @param shortClassName�@ �N���X���i�V���v�����j
	 * 
	 * @return�@�o���[�I�u�W�F�N�g�̃C���X�^���X
	 * 
	 */
	public Object getValueObject(String shortClassName) {
		return buildValueObject(shortClassName);
	}



	/**
	 * �w�肳�ꂽ�N���X���i�V���v�����j�ɊY������o���[�I�u�W�F�N�g�̃C���X�^���X�𕜋A����B<br/>
	 * �w�肳�ꂽ�������̔z��I�u�W�F�N�g�𕜋A����B<br/>
	 * <br/>
	 * �o���[�I�u�W�F�N�g���g�������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���Ċg�������N���X�𕜋A����l�ɃJ�X�^�}�C�Y���鎖�B<br/>
	 * <br/>
	 * @param shortClassName�@ �N���X���i�V���v�����j
	 * @param cnt�@�z��̐�
	 * 
	 * @return�@�o���[�I�u�W�F�N�g�̃C���X�^���X�̔z��
	 * 
	 */
	public Object[] getValueObject(String shortClassName, int cnt) {

		// �o���[�I�u�W�F�N�g�𐶐����A�C���X�^���X���擾����B
		Object target = buildValueObject(shortClassName);

		// ���̃C���X�^���X�̃N���X����z������t���N�V��������B
		Object[] objects = (Object[])Array.newInstance(target.getClass(), cnt);

		// �z��̒��Ƀ��t���N�V���������C���X�^���X��ݒ肷��B
		for (int i=0; i < cnt; ++i){
			try {
				objects[i] = target.getClass().newInstance();

			} catch (InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
		}
		return objects;
	}



	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����A�����I�ȏ���<br/>
	 * <br/>
	 * @param shortClassName �N���X���i�V���v�����j
	 * 
	 * @return �w�肳�ꂽ�N���X�̃C���X�^���X
	 */
	protected Object buildValueObject(String shortClassName){

		try {
			// �N���X������C���X�^���X�𐶐�����B
			Class<?> cls = Class.forName(this.packageName + "." + shortClassName);
			Object vo = cls.newInstance();

			// Default �l��ݒ�
			setDefaultValue(vo);

			return vo;

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		
	}



	/**
	 * ����̏����l��ݒ肷�ׂ� Value �I�u�W�F�N�g�����`�F�b�N����B<br/>
	 * �Y������ Value �I�u�W�F�N�g�̏ꍇ�A���������������s����B<br/>
	 * <br/>
	 * @param vo Value �I�u�W�F�N�g
	 */
	protected void setDefaultValue(Object vo){
		
		if (vo instanceof AdminLoginInfo){
			initAdminLoginInfo((AdminLoginInfo)vo);
		}
	}
	
	
	
	/**
	 * �Ǘ��҃��O�C��ID���̏���������<br/>
	 * <br/>
	 * @param adminLoginInfo �Ǘ��҃��O�C��ID���� Value �I�u�W�F�N�g
	 */
	protected void initAdminLoginInfo(AdminLoginInfo adminLoginInfo){
		// DB ��`���Ńf�t�H���g�ݒ肵�Ă��Ă��A�o���[�I�u�W�F�N�g�̒l�� null �̏ꍇ�A��O����������B
		// ����āADB ��`���Ńf�t�H���g�ݒ肵�Ă���ꍇ�A���̃^�C�~���O�ŏ����l��ݒ肵�Ă����B
		adminLoginInfo.setFailCnt(0);
	}
	
}
