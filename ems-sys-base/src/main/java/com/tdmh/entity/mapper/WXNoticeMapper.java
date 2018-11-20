package com.tdmh.entity.mapper;

import com.tdmh.param.WXNoticeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author litairan on 2018/11/16.
 */
@Mapper
@Component
public interface WXNoticeMapper {
    List<WXNoticeParam> listData();

    int create(WXNoticeParam param);

    WXNoticeParam getWXNoticeById(Integer id);

    int delete(List<WXNoticeParam> wxNotices);

    List<WXNoticeParam> search(@Param("wxNoticeTitle") String wxNoticeTitle);
}
