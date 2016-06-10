package jp.co.transcosmos.dm3.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;



/**
 * <pre>
 * dm3lookup �J�X�^���^�O�� Velocity ��
 * Code Lookup Manager ���g�p���ăR�[�h�ϊ��A����сA�v���_�E�����X�g�p�� List ���擾����B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.18  �V�K�쐬
 * H.Mizuno  2013.04.19  lookupForEach �Ή�
 * 
 * </pre>
*/
@DefaultKey("dm3lookup")
@ValidScope(Scope.REQUEST)
public class CodeLookupVelocity {
    private static final Log log = LogFactory.getLog(CodeLookupVelocity.class);

    // ���[�����Ǘ����Ă���A�Z�b�V������ Key �iV1�AV2 �F�ؗp�j
    private String codeLookupManagerParameterName = "codeLookupManager";

    // HTTP ���N�G�X�g�I�u�W�F�N�g
    protected HttpServletRequest request;

    // HTTP ���X�|���X�I�u�W�F�N�g
    protected HttpServletResponse response;

    // �T�[�u���b�g�E�R���e�L�X�g
    protected ServletContext servletContext;

	

    /**
     * ���ʊ֐�����������<br/>
     * ���̃��\�b�h���`����ƁAVelocity ���� ToolContext �����鎖���ł���B<br />
     * ToolContext �ɂ́AHttpRequest ��AServletContext ���i�[���Ă���̂ŁA�K�v��<br />
     * ����΁A���̃��\�b�h���`����B<br />
     * <br/>
     * @param values Velocity ����n�����v���p�e�B���
     */
    protected void configure(Map<?,?> values)
    {
		log.debug("velocity dm3login init");

		// ToolContext ���擾���A���[�J���R���e�L�X�g����AWeb �֘A�̃I�u�W�F�N�g���擾�B
		ToolContext toolContext = (ToolContext)values.get("velocityContext");
    	this.servletContext = (ServletContext)toolContext.get("servletContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    	this.response = (HttpServletResponse)toolContext.get("response");
    }

	
    /**
     * Code Lookup Manager �ɂ��A�R�[�h�ϊ�����<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pLookupKey �I����Ԃɂ���l
     * @return �R�[�h�ɊY�����镶����
     */
    public String lookup(String pLookupName, String pLookupKey) {
    	return lookup(pLookupName, pLookupKey, null, null);
    }


    
    /**
     * Code Lookup Manager �ɂ��A�R�[�h�ϊ�����<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pLookupKey �I����Ԃɂ���l
     * @param pWildcard �u��������i{1} �Ȃǂ̕�����z��ɏ]���u������B�j
     * @return �R�[�h�ɊY�����镶����
     */
    public String lookup(String pLookupName, String pLookupKey, String pWildcard) {
    	return lookup(pLookupName, pLookupKey, new String[] {pWildcard}, null);
    }

    public String lookup(String pLookupName, String pLookupKey, String pWildcards[]) {
    	return lookup(pLookupName, pLookupKey, pWildcards, null);
    }

    public String lookup(String pLookupName, String pLookupKey, List<String> pWildcards) {
    	String wildcards[] = pWildcards.toArray(new String[pWildcards.size()]);
    	return lookup(pLookupName, pLookupKey, wildcards, null);
    }

    
    
    /**
     * Code Lookup Manager �ɂ��A�R�[�h�ϊ�����<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pLookupKey �I����Ԃɂ���l
     * @param pWildcard �u��������i{1} �Ȃǂ̕�����z��ɏ]���u������B�j
     * @param pManageId Code lookup Manager �� Bean ID
     * @return �R�[�h�ɊY�����镶����
     */
    public String lookup(String pLookupName, String pLookupKey, String pWildcards[], String pManageId) {

    	// �܂��L�蓾�Ȃ��Ǝv�����A����y�[�W���ňقȂ� CodeLookup Manager ���w�肳�ꂽ�ꍇ�A
    	// ���̃I�u�W�F�N�g�̃v���p�e�B������������Ɩ�肪��������\��������B
    	// ����āA�����ɂ��悤����ϐ��͕ʂɊm�ۂ���B
        String managerParam = this.codeLookupManagerParameterName;
        if (pManageId != null && pManageId.length() > 0) {
        	managerParam = "codeLookupManager";
        }

        // Code lookup �}�l�[�W������l�����蕜�A����B
        CodeLookupManager manager = (CodeLookupManager) this.request.getAttribute(managerParam);
        if (manager != null) {
        	return manager.lookupValue(pLookupName, pLookupKey, pWildcards);
        } else {
            log.warn("WARNING: Code lookup manager not found");
            return null;
        }
    }


    /**
     * Code Lookup Manager �ɂ��A���X�g�擾����<br/>
     * �߂�l�́A�Y���f�[�^�� MAP ���i�[���ꂽ List �I�u�W�F�N�g�𕜋A����B<br/>
     * Map �́A���L�\���Œl���ݒ肳���B<br/>
     *     �EKey = "key" : CodeLookupList �Œ�`����Ă��� Key �l<br/>
     *     �Ekey = "value" : CodeLookupList �Œ�`����Ă��� Value �l<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @return �Y���f�[�^�� List ���
     */
    public List<Map<String, String>> lookupForEach(String pLookupName){
    	return lookupForEach(pLookupName, null, null, null, null);
    }


    
    /**
     * Code Lookup Manager �ɂ��A���X�g�擾����<br/>
     * �߂�l�́A�Y���f�[�^�� MAP ���i�[���ꂽ List �I�u�W�F�N�g�𕜋A����B<br/>
     * Map �́A���L�\���Œl���ݒ肳���B<br/>
     *     �EKey = "key" : CodeLookupList �Œ�`����Ă��� Key �l<br/>
     *     �Ekey = "value" : CodeLookupList �Œ�`����Ă��� Value �l<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pWildcard �u��������i{1} �Ȃǂ̕�����z��ɏ]���u������B�j
     * @return �Y���f�[�^�� List ���
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcard){
    	return lookupForEach(pLookupName, new String[] {pWildcard}, null, null, null);
    }
    
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[]){
    	return lookupForEach(pLookupName, pWildcards, null, null, null);
    }
    
    public List<Map<String, String>> lookupForEach(String pLookupName, List<String> pWildcards){
    	String wildcards[] = pWildcards.toArray(new String[pWildcards.size()]);
    	return lookupForEach(pLookupName, wildcards, null, null, null);
    }
    

    
    /**
     * Code Lookup Manager �ɂ��A���X�g�擾����<br/>
     * �߂�l�́A�Y���f�[�^�� MAP ���i�[���ꂽ List �I�u�W�F�N�g�𕜋A����B<br/>
     * Map �́A���L�\���Œl���ݒ肳���B<br/>
     *     �EKey = "key" : CodeLookupList �Œ�`����Ă��� Key �l<br/>
     *     �Ekey = "value" : CodeLookupList �Œ�`����Ă��� Value �l<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pWildcard �u��������i{1} �Ȃǂ̕�����z��ɏ]���u������B�j
     * @param pManageId Code lookup Manager �� Bean ID
     * @return �Y���f�[�^�� List ���
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[], String pManageId){
    	return lookupForEach(pLookupName, pWildcards, pManageId, null, null);
    }



    /**
     * Code Lookup Manager �ɂ��A���X�g�擾����<br/>
     * �߂�l�́A�Y���f�[�^�� MAP ���i�[���ꂽ List �I�u�W�F�N�g�𕜋A����B<br/>
     * Map �́A���L�\���Œl���ݒ肳���B<br/>
     *     �EKey = pKeyParameter : CodeLookupList �Œ�`����Ă��� Key �l<br/>
     *     �Ekey = pValueParameter : CodeLookupList �Œ�`����Ă��� Value �l<br/>
     *     ��pKeyParameter�ApValueParameter �� null �l�̏ꍇ�A"key", "value"�@���g�p���ꂦ��B<br/>
     * <br/>
     * @param pLookupName �g�p���� lookup ��
     * @param pWildcard �u��������i{1} �Ȃǂ̕�����z��ɏ]���u������B�j
     * @param pManageId Code lookup Manager �� Bean ID
     * @return �Y���f�[�^�� List ���
     */
    public List<Map<String, String>> lookupForEach(String pLookupName, String pWildcards[], String pManageId, String pKeyParameter, String pValueParameter){

    	// Key ������
        String keyParameter = "key";
        if (pKeyParameter != null && pKeyParameter.length() > 0){
        	keyParameter = pKeyParameter; 
        }

        // value ������
        String valueParameter = "value";
        if (pValueParameter != null && pValueParameter.length() > 0){
        	valueParameter = pValueParameter;
        }

        // Colde Lookup Manager �� Bean ID ��
        String managerParam = this.codeLookupManagerParameterName;
        if (pManageId != null && pManageId.length() > 0) {
        	managerParam = "codeLookupManager";
        }

        // Code lookup �}�l�[�W������l�����蕜�A����B
        CodeLookupManager manager = (CodeLookupManager) this.request.getAttribute(managerParam);
        if (manager != null) {
        	Iterator<String> lookupKeys = manager.getKeysByLookup(pLookupName);
        	if (lookupKeys == null) return null;

    		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        	while(lookupKeys.hasNext()){
        		// �߂�l�p�̃��X�g�I�u�W�F�N�g
        		Map<String, String> map = new HashMap<String, String>();

        		// �擾�����f�[�^�� Map �ɐݒ肷��B
        		String key = lookupKeys.next();
        		map.put(keyParameter, key);
        		map.put(valueParameter, manager.lookupValue(pLookupName, key, pWildcards));

        		list.add(map);
        	}
        	return list;

        } else {
        	log.warn("WARNING: Code lookup manager not found");
            return null;
        }
    }
}
