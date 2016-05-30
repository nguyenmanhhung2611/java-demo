package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �ݔ����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.09  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaHousingSpecialtyForm extends HousingEquipForm {
    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingSpecialtyForm() {
        super();
    }

    /** ���߃t���O */
    private String command;
    /** �������CD */
    private String housingKindCd;
    /** �����ԍ� */
    private String housingCd;
    /** �������� */
    private String displayHousingName;
    /** �ݔ��}�X�^�̐ݔ�CD */
    private String[] mstEquipCd;
    /** �ݔ����� */
    private String[] equipName;

    /**
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command �Z�b�g���� command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return housingKindCd
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * @param housingKindCd �Z�b�g���� housingKindCd
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }

    /**
     * @return housingCd
     */
    public String getHousingCd() {
        return housingCd;
    }

    /**
     * @param housingCd �Z�b�g���� housingCd
     */
    public void setHousingCd(String housingCd) {
        this.housingCd = housingCd;
    }

    /**
     * @return displayHousingName
     */
    public String getDisplayHousingName() {
        return displayHousingName;
    }

    /**
     * @param displayHousingName �Z�b�g���� displayHousingName
     */
    public void setDisplayHousingName(String displayHousingName) {
        this.displayHousingName = displayHousingName;
    }

    /**
     * @return mstEquipCd
     */
    public String[] getMstEquipCd() {
        return mstEquipCd;
    }

    /**
     * @param mstEquipCd �Z�b�g���� mstEquipCd
     */
    public void setMstEquipCd(String[] mstEquipCd) {
        this.mstEquipCd = mstEquipCd;
    }

    /**
     * @return equipName
     */
    public String[] getEquipName() {
        return equipName;
    }

    /**
     * @param equipName �Z�b�g���� equipName
     */
    public void setEquipName(String[] equipName) {
        this.equipName = equipName;
    }

    /**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housing �������I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setDefaultData(Housing housing, List<JoinResult> equipList) throws Exception {
		// ������{�����擾����B
		HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

		// �����ԍ����Z�b�g����B
		this.setHousingCd(housingInfo.getHousingCd());
		// �\���p���������Z�b�g����B
		this.setDisplayHousingName(housingInfo.getDisplayHousingName());

		// �����ݔ������擾����B
		Map<String, jp.co.transcosmos.dm3.core.vo.EquipMst> housingEquipMap = housing.getHousingEquipInfos();
		// �����ݔ�CD
		String[] housingEquipCd = new String[housingEquipMap.size()];
		int i = 0;
		for (String key : housingEquipMap.keySet()) {
			housingEquipCd[i] = housingEquipMap.get(key).getEquipCd();
			i++;
		}
		// �����ݔ�CD���Z�b�g����B
		this.setEquipCd(housingEquipCd);

		int len = equipList.size();
		// �}�X�^�̐ݔ�CD
		String[] mstEquipCd = new String[len];
		// �ݔ�����
		String[] equipName = new String[len];
		for (int j = 0; j < len; j++) {
			mstEquipCd[j] = ((EquipMst)equipList.get(j).getItems().get("equipMst")).getEquipCd();
			equipName[j] = ((EquipMst)equipList.get(j).getItems().get("equipMst")).getEquipName();
		}
		// �}�X�^�̐ݔ�CD���Z�b�g����B
		this.setMstEquipCd(mstEquipCd);
		// �ݔ����̂��Z�b�g����B
		this.setEquipName(equipName);
	}
}
