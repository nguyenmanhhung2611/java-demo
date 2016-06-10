package jp.co.transcosmos.dm3.utils;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;


/**
 * ���f�I�Ɏg�p���� ���ʃI�u�W�F�N�g�� ���N�G�X�g�X�R�[�v�֊i�[����B<br/>
 * ���̃R�}���h�N���X�� scope = protptype �� bean ��`���AFilter �Ƃ��� URL �}�b�s���O���Ďg�p����B<br/>
 * URL �}�b�s���O�ɊY���������N�G�X�g�ɑ΂��ă��N�G�X�g�X�R�[�v�֎w�肳�ꂽ Bean �����[�h�����B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class AddBeanToRequestScopeCommand implements Command {

    private static final Log log = LogFactory.getLog(AddBeanToRequestScopeCommand.class);



	/** ���N�G�X�g�X�R�[�v�Ɋi�[���� Bean �� Map ��� */
	private Map<String, Object> beanMap;
	
	/**
	 * ���N�G�X�g�X�R�[�v�Ɋi�[���� Bean ��ݒ肷��B<br/>
	 * Map �̍\���͉��L�̒ʂ�B<br/>
	 * <br/>
	 *   Key = Bean ID<br/>
	 *   Value = �i�[����I�u�W�F�N�g<br/> 
	 * <br/>
	 * @param beanMap ���N�G�X�g�X�R�[�v�Ɋi�[����I�u�W�F�N�g�� Map ���
	 */
	public void setBeanMap(Map<String, Object> beanMap) {
		this.beanMap = beanMap;
	}


	
	/**
	 * ���C������<br/>
	 * �w�肳�ꂽ�I�u�W�F�N�g�����N�G�X�g�X�R�[�v�֊i�[����B<br/>
	 * <br/>
	 * @param req HTTP ���N�G�X�g
	 * @param res HTTP ���X�|���X
	 * @return null �Œ�
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (this.beanMap != null) {
			for (Entry<String, Object> e : this.beanMap.entrySet()){
	            req.setAttribute(e.getKey(), e.getValue());
	            log.info("Added " + e.getValue().getClass().getSimpleName() + " to request attribute: " + e.getKey());
			}
		}

		return null;
	}
	
}
