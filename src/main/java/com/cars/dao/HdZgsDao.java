package com.cars.dao;

import com.cars.model.HdLcrqModel;
import com.cars.model.HdModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 接收端dao
 * Created by liuyanchao
 * on 2018/8/14.
 */
@Repository
public class HdZgsDao {
    @Value("#{jdbcTemplate}")
    private JdbcTemplate jdbcTemplate;
    @Value("#{namedParameterJdbcTemplate}")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 判断是否存在
     *
     * @param zcrq
     * @param lwdw
     * @param seqnum
     * @return
     */
    public boolean isExist(String zcrq, String lwdw, String seqnum) {
        String sql = "select count(*) from td_hd_hz where zcrq=? and lwdw=? and seqnum=?";
        int num = jdbcTemplate.queryForObject(sql, new Object[]{zcrq, lwdw, seqnum}, Integer.class);
        return num > 0 ? true : false;
    }

    /**
     * 大于次日更新操作
     *
     * @param hdModel
     * @return
     */
    public int updateHdHz(HdModel hdModel) {
        String sql = "update td_hd_hz set qqcs=:qqcs,qqds =:qqds,qqcz =:qqcz,pzcs =:pzcs,pzds =:pzds,pzcz =:pzcz," +
                "   jy =:jy,hzjy =:hzjy,hysr =:hysr,jsjj =:jsjj,yl4 =:yl4,yl5 =:yl5," +
                "   dzyxm =:dzyxm,ddh =:ddh,ddtz =:ddtz,ddtzhz =:ddtzhz,qsqqcs =:qsqqcs,hscs =:hscs," +
                "   shjycs =:shjycs,jhtz =:jhtz,tzyybz =:tzyybz" +
                "   where zcrq=:zcrq and lwdw=:lwdw and seqnum=:seqnum ";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(hdModel));
    }

    /**
     * 小于等于次日更新操作
     *
     * @param hdModel
     * @return
     */
    public int updateHdHz2(HdModel hdModel) {
        String sql = "update td_hd_hz set qqcs =:qqcs,qqds =:qqds,qqcz =:qqcz,pzcs =:pzcs,pzds =:pzds,pzcz =:pzcz," +
                "   jy =:jy,hzjy =:hzjy,hysr =:hysr,jsjj =:jsjj,yl4 =:yl4,yl5 =:yl5," +
                "   dzyxm =:dzyxm,ddh =:ddh,ddtz =:ddtz,ddtzhz =:ddtzhz,qsqqcs =:qsqqcs,hscs =:hscs," +
                "   shjycs =:shjycs,jhtz =:jhtz,tzyybz =:tzyybz,wpyyhz =:wpyyhz,rjhh =:rjhh,sprdm =:sprdm," +
                "   jdzc1 =:jdzc1,jdzcds1 =:jdzcds1,jddz1 =:jddz1,yjcs1 =:yjcs1,yjds1 =:yjds1,ope1 =:ope1," +
                "   jdzc2 =:jdzc2,jdzcds2 =:jdzcds2,jddz2 =:jddz2,yjcs2 =:yjcs2,yjds2 =:yjds2,ope2 =:ope2," +
                "   jdzc3 =:jdzc3,jdzcds3 =:jdzcds3,jddz3 =:jddz3,yjcs3 =:yjcs3,yjds3 =:yjds3,ope3 =:ope3," +
                "   jdzc4 =:jdzc4,jdzcds4 =:jdzcds4,jddz4 =:jddz4,zccz =:zccz,ope4 =:ope4" +
                "   where zcrq=:zcrq and lwdw=:lwdw and seqnum=:seqnum";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(hdModel));
    }

    /**
     * 新增
     *
     * @param hdModel
     * @return
     */
    public int saveHdHz(HdModel hdModel) {
        String sql = "insert into td_hd_hz(zcrq, lwdw, seqnum, jhid, pzycfh, qqlx, qqcs, qqds, qqcz, pzcs, pzds, pzcz," +
                "    wpyyhz, rjhh, sprdm, ybjh, ybjhds, dyjdjh, dyjdjhds, jdzc1, jdzcds1, jddz1, yjcs1, yjds1, ope1, " +
                "   jdzc2, jdzcds2, jddz2, jdtz1, jdtzds1, yjcs2, yjds2, yjqr, yjqrds, ope2, jdzc3, jdzcds3, jddz3, " +
                "   yjcs3, yjds3, ope3, jdzc4, jdzcds4, jddz4, zccz, ope4, htddh, fj, fz, fzhzzm, fhdwdm, fhdwsrm, " +
                "   fhdwmc, fhdwtz, fhdwtzm, fhbm, fhbmm, dfj, dfjhz, dj, djhz, dz, dzhzzm, shdwdm, shdwsrm, shdwmc, " +
                "   shdwtz, shdwtzm, shbm, shbmm, pm, hzpm, hzpl, ystz, ystzhz, hzg, hzghz, zdg, zdghz, dcf, th, cc, jy, " +
                "   hzjy, zyxm, txzbz, hqhw, czsh, hpsr, hysr, jsjj, yl1, yl2, yl3, yl4, yl5, yl6, yl7, jhtz, dzyxm, ddh," +
                "    ddtz, ddtzhz, qsqqcs, hscs, shjycs, tzyybz, lkyy1cs, lkyy2cs, lkyy3cs, lkyy4cs, lkyy5cs, lkyy6cs, " +
                "   lkyy, lkyybz, auto, ljdm)" +
                "   values(:zcrq,:lwdw,:seqnum,:jhid,:pzycfh,:qqlx,:qqcs,:qqds,:qqcz,:pzcs,:pzds,:pzcz,:wpyyhz,:rjhh," +
                "   :sprdm,:ybjh,:ybjhds,:dyjdjh,:dyjdjhds,:jdzc1,:jdzcds1,:jddz1,:yjcs1,:yjds1,:ope1,:jdzc2,:jdzcds2," +
                "   :jddz2,:jdtz1,:jdtzds1,:yjcs2,:yjds2,:yjqr,:yjqrds,:ope2,:jdzc3,:jdzcds3,:jddz3,:yjcs3,:yjds3,:ope3," +
                "   :jdzc4,:jdzcds4,:jddz4,:zccz,:ope4,:htddh,:fj,:fz,:fzhzzm,:fhdwdm,:fhdwsrm,:fhdwmc,:fhdwtz,:fhdwtzm," +
                "   :fhbm,:fhbmm,:dfj,:dfjhz,:dj,:djhz,:dz,:dzhzzm,:shdwdm,:shdwsrm,:shdwmc,:shdwtz,:shdwtzm,:shbm,:shbmm," +
                "   :pm,:hzpm,:hzpl,:ystz,:ystzhz,:hzg,:hzghz,:zdg,:zdghz,:dcf,:th,:cc,:jy,:hzjy,:zyxm,:txzbz,:hqhw,:czsh," +
                "   :hpsr,:hysr,:jsjj,:yl1,:yl2,:yl3,:yl4,:yl5,:yl6,:yl7,:jhtz,:dzyxm,:ddh,:ddtz,:ddtzhz,:qsqqcs,:hscs," +
                "   :shjycs,:tzyybz,:lkyy1cs,:lkyy2cs,:lkyy3cs,:lkyy4cs,:lkyy5cs,:lkyy6cs,:lkyy,:lkyybz,:auto,:ljdm)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(hdModel));

    }

    /**
     * 判断落成日期是否存在
     *
     * @param ljdm
     * @param lcrq
     * @return
     */
    public boolean isExistLcrq(String ljdm, String lcrq) {
        String sql = "select count(*) from td_hd_lcrq where ljdm=? and lcrq=?";
        int num = jdbcTemplate.queryForObject(sql, new Object[]{ljdm, lcrq}, Integer.class);
        return num > 0 ? true : false;
    }

    /**
     * 插入落成日期
     *
     * @param hdLcrqModel
     * @return
     */
    public int saveHdLcrq(HdLcrqModel hdLcrqModel) {
        String sql = "insert into td_hd_lcrq(ljdm,lcrq) values(:ljdm,:lcrq)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(hdLcrqModel));
    }
}
