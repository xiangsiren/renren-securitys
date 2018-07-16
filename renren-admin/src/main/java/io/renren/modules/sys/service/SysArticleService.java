package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysArticleEntity;

import java.util.Map;

public interface SysArticleService  extends IService<SysArticleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public void save(SysArticleEntity config);

    public void update(SysArticleEntity config);

}
