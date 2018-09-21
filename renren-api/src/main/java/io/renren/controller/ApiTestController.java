package io.renren.controller;


import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.R;
import io.renren.entity.UserEntity;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")
@Api(tags="测试接口")
public class ApiTestController {

    @Autowired
    UserService userService ;

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response=UserEntity.class)
    public R userInfo(@ApiIgnore @LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }


    /**
     * 根据用户信息表ID查询
     */
    @GetMapping("/query/{id}")
    @ApiOperation(value = "/query/{id}",notes = "根据标签信息表ID查询",response = UserEntity.class)
    public UserEntity query(@PathVariable Long id) {
        UserEntity entity = userService.selectById(id);
        return entity;
    }

}
