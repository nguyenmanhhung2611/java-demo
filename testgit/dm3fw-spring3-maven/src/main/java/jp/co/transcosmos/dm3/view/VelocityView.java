package jp.co.transcosmos.dm3.view;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * Velocity �ɂ�郌���_�����O�p View �N���X
 * URL �}�b�s���O���ɁA�umv:�e���v���[�g���v�Ń}�b�s���O���`����B
 * �umv:�e���v���[�g��:�v���p�e�B�t�@�C�����v���w�肷��ƁAVelocity Engine ���g�p����v���p�e�B�t�@�C����
 * �ʂɐݒ肷�鎖���\�B 
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.03.12  �V�K�쐬
 * H.Mizuno  2013.03.15  Velocity Propertie File �Ή�
 * H.Mizuno  2013.03.17  ToolContext �ɁA���N�G�X�g�A���X�|���X�A�̃I�u�W�F�N�g���i�[
 *
 * </pre>
*/
public class VelocityView extends AbstractView {

	// Velocity �e���v���[�g�t�@�C����
	private String templateFileName ="";

	// Velocity �v���p�e�B�E�t�@�C����
	private String propertiesFile = "velocity.properties";
	


	/**
     * �R���X�g���N�^�[<br/>
     * <br/>
     * @param templateFileName �e���v���[�g�t�@�C����
     * @param propertiesFile Velocity�@�v���p�e�B�t�@�C����
     */
	public VelocityView(String templateFileName, String propertiesFile){
		this.templateFileName = templateFileName;
		this.propertiesFile = propertiesFile;
	}


	
	/**
     * �R���X�g���N�^�[<br/>
     * <br/>
     * @param templateFileName �e���v���[�g�t�@�C����
     */
	public VelocityView(String templateFileName){
		this.templateFileName = templateFileName;
	}



	/**
     * Velocity ���g�p�����`�揈��<br/>
     * Velocity tools ��g�ݍ��� context �𐶐����ĕ`�悷��B<br />
     * Velocitu �̏������́AWEB-INF ���� propertiesFile �Ŏw�肳�ꂽ�v���p�e�B�t�@�C��<br />
     * ���g�p����B�@�f�t�H���g�́Avelocity.properties<br />
     * ���ɁAWEB-INF/velocity-tools.xml ���g�p���� Velocity tools �̃R���e�L�X�g����荞�ށB<br />
     * XSS �Ή��̖�������A���̃t�@�C���� VelocityView ���g�p����ꍇ�A�K�{�ƂȂ�B<br />
     * <br/>
     * @param model �u���ɂ��悤���� Map ���
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
     * @exception Exception
     */
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// �e���v���[�g�t�@�C�������w�肳��Ă��邩���`�F�b�N
		if (templateFileName == null || templateFileName.length() == 0 )
			throw new RuntimeException("templateFileName is empty");

		// Velocity Engine �̃C���X�^���X�����i�����I�u�W�F�N�g�j
		VelocityEngine velocity = new VelocityEngine();

		// Velocity ������
		if (this.propertiesFile == null || this.propertiesFile.length() == 0){
			// �v���p�e�B�t�@�C�������w�肳��Ă��Ȃ��ꍇ�A�f�t�H���g�l�ŏ�����
			velocity.init();
		} else {
			// �v���p�e�B�t�@�C�������w�肳��Ă���ꍇ�A���̃t�@�C�����g�p���ď�����
			String file = this.getServletContext().getRealPath("WEB-INF/" + this.propertiesFile);
			velocity.init(file);
		}

		// Velocity tools ��ǉ������R���e�L�X�g�𐶐�
		// �R�[�h���������AToolManager ���������� Context �͋��L���Ă����S�Ɏv����B
		// ���\��]�����A����������ʁA���X�|���X�̗򉻂��������ꍇ�̓��t�@�N�^�����O����������B
		String toolsfile = this.getServletContext().getRealPath("WEB-INF/velocity-tools.xml");
		ToolManager velocityToolManager = new ToolManager();
		velocityToolManager.configure(toolsfile);
		
		ToolContext t_ctx = velocityToolManager.createContext();
		t_ctx.put("request", request);
		t_ctx.put("response", response);
		t_ctx.put("servletContext", this.getServletContext());
		
		VelocityContext context = new VelocityContext(t_ctx);
		// �p�����[�^��ǉ������R���e�L�X�g�𐶐�
		if (model != null) {
			for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) model.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
		

        // �e���v���[�g�̐���
		Template template = velocity.getTemplate(this.templateFileName);

        // �e���v���[�g���}�[�W���ďo��
        StringWriter writer = new StringWriter();
        template.merge(context,writer);
        String encoding = (String)velocity.getProperty("output.encoding");
        byte out[] = writer.toString().getBytes(encoding);

        // ���X�|���X�ɒl��ݒ�
        response.setContentType("text/html;charset=" + encoding);
        response.setContentLength(out.length);
        response.setBufferSize(out.length);
        response.getOutputStream().write(out);
        response.getOutputStream().flush();
	}


	
	// setter�Agetter
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}
}
