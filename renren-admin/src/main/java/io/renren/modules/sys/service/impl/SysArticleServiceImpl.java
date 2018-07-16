package io.renren.modules.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysArticleDao;
import io.renren.modules.sys.entity.SysArticleEntity;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("SysArticleService")
public class SysArticleServiceImpl extends ServiceImpl<SysArticleDao,SysArticleEntity> implements SysArticleService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");

        Page<SysArticleEntity> page = this.selectPage(
                new Query<SysArticleEntity>(params).getPage(),
                new EntityWrapper<SysArticleEntity>()
                        .like(StringUtils.isNotBlank(title),"title", title)
        );

        return new PageUtils(page);
    }

    @Override
    public void save(SysArticleEntity sysArticleEntity){

        sysArticleEntity.setCreateTime(new Date());
        sysArticleEntity.setUpdateTime(new Date());

        this.insert(sysArticleEntity);

    }
    @Override
    public void update(SysArticleEntity sysArticleEntity){

        sysArticleEntity.setUpdateTime(new Date());

        this.updateById(sysArticleEntity);

    }
}
