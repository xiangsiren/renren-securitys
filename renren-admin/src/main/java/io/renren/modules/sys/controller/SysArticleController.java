/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.controller;

import io.renren.common.xss.XssHttpServletRequestWrapper;
import io.renren.ueditor.ActionEnter;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.sys.entity.SysArticleEntity;
import io.renren.modules.sys.service.SysArticleService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;

/**
 * 文章管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("sys/article")
public class SysArticleController {

    private static final  Logger logger = LoggerFactory.getLogger(SysArticleController.class);
    @Autowired
    private SysArticleService sysArticleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:article:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysArticleService.queryPage(params);
        logger.warn("bbbbbbbbbbbbbbbbbbbbb");
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:article:info")
    public R info(@PathVariable("id") Long id){
        SysArticleEntity article = sysArticleService.selectById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:article:save")
    public R save(@RequestBody SysArticleEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysArticleService.save(dict);
//        sysArticleService.insert(dict);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:article:update")
    public R update(@RequestBody SysArticleEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysArticleService.update(dict);
//        sysArticleService.updateById(dict);

        return R.ok();
    }
    @RequestMapping("/updates")
    @RequiresPermissions("sys:article:update")
    public R update(HttpServletRequest request){

        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);

        SysArticleEntity dict = new SysArticleEntity() ;

        String title = orgRequest.getParameter("title");
        String content = orgRequest.getParameter("content");
        String note = orgRequest.getParameter("note");
        String cid = orgRequest.getParameter("cid");
        Long id = Long.valueOf(orgRequest.getParameter("id"));

        dict.setId(id);
        dict.setTitle(title);
        dict.setContent(content);
        dict.setNote(note);
        dict.setCid(Long.valueOf(cid));
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysArticleService.update(dict);
//        sysArticleService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:article:delete")
    public R delete(@RequestBody Long[] ids){
        sysArticleService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 上传文件
     */
    @RequestMapping("/upload")
//    @RequiresPermissions("sys:oss:all")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        String config = "{\"state\": \"SUCCESS\"," +
                "\"url\": \"" + url + "\"," +
                "\"title\": \"" + url + "\"," +
                "\"original\": \"" + url + "\"}";
        return config;
//        return url;
    }

    /**
     * 加载配置
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath,"config.json").exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        logger.info("dfsdfssdf");


    }
}
