package io.renren.controller;


import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.UserEntity;
import io.renren.form.RegisterForm;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 注册接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
@Api(tags="注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.insert(user);

        return R.ok();
    }

    /**
     * TODO 测试代码
     * @param args
     */
    public static void main(String[] args) {

        final  String s = "123456";

        System.out.println(DigestUtils.md5Hex(s));
        try {
            final  String s1 = md5(s);
            System.out.println(s1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private static String md5(final String input) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] digest = md.digest(input.getBytes());
        final String result = byte2hex(digest);
        return result;
    }
    /**
     * byte to string
     *
     * @param digest
     * @return
     */
    private static String byte2hex(final byte[] digest) {
        String des = "";
        String tmp = null;
        for (final byte element : digest) {
            tmp = (Integer.toHexString(element & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
