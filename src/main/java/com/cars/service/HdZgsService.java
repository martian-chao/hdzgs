package com.cars.service;

import com.cars.dao.HdZgsDao;
import com.cars.model.HdLcrqModel;
import com.cars.model.HdModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 接收端service
 * Created by liuyanchao
 * on 2018/8/14.
 */
@Service
@Transactional
public class HdZgsService {
    @Value("#{hdZgsDao}")
    private HdZgsDao hdZgsDao;

    /**
     * 判断是汇总数据否存在
     *
     * @param zcrq
     * @param lwdw
     * @param seqnum
     * @return
     */
    public boolean isExist(String zcrq, String lwdw, String seqnum) {
        return hdZgsDao.isExist(zcrq, lwdw, seqnum);
    }

    /**
     * 大于次日更新操作
     *
     * @param hdModel
     * @return
     */
    public int updateHdHz(HdModel hdModel) {
        return hdZgsDao.updateHdHz(hdModel);
    }

    /**
     * 小于等于次日更新操作
     *
     * @param hdModel
     * @return
     */
    public int updateHdHz2(HdModel hdModel) {
        return hdZgsDao.updateHdHz2(hdModel);
    }

    /**
     * 新增汇总数据
     *
     * @param hdModel
     * @return
     */
    public int saveHdHz(HdModel hdModel) {
        return hdZgsDao.saveHdHz(hdModel);
    }

    /**
     * 判断落成日期是否存在
     *
     * @param ljdm
     * @param lcrq
     * @return
     */
    public boolean isExistLcrq(String ljdm, String lcrq) {
        return hdZgsDao.isExistLcrq(ljdm, lcrq);
    }

    /**
     * 插入落成日期
     * @param hdLcrqModel
     * @return
     */
    public int saveHdLcrq(HdLcrqModel hdLcrqModel) {
        return hdZgsDao.saveHdLcrq(hdLcrqModel);
    }

}
