package com.fung.server.gameserver.content.config.skill;

import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/7/23 10:20
 */
public class TreatmentSkill extends Skill implements Model {

    /**
     * 治疗量
     */
    private int treatmentAmount;

    /**
     * 是否范围治疗
     */
    private String isRangeTreatment;


    public int getTreatmentAmount() {
        return treatmentAmount;
    }

    public void setTreatmentAmount(int treatmentAmount) {
        this.treatmentAmount = treatmentAmount;
    }

    public boolean isRangeTreatment() {
        return "TRUE".equals(isRangeTreatment);
    }

    public void setRangeTreatment(String rangeTreatment) {
        isRangeTreatment = rangeTreatment;
    }

    public String getIsRangeTreatment() {
        return this.isRangeTreatment;
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
