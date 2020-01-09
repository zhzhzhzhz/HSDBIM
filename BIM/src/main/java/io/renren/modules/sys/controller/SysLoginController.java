/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.renren.common.annotation.UserLoginToken;
import io.renren.common.utils.R;
import io.renren.modules.sys.dto.LoginDTO;
import io.renren.modules.sys.entity.HsdLoginRecord;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysLoginService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.ToLoginService;
import io.renren.modules.sys.service.TokenService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.sys.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;

	@Autowired
	private SysLoginService loginService;

	@Autowired
	private ToLoginService toLoginService;

	@Autowired
	private TokenService tokenService;


	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha) {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			String name = token.getUsername();
            HsdLoginRecord hsdLoginRecord = new HsdLoginRecord();
			hsdLoginRecord.setUsername(name);
			loginService.save(hsdLoginRecord);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
	    
		return R.ok();
	}
	@ResponseBody
	@RequestMapping(value = "/sys/toLogin", method = RequestMethod.POST)
	public Object sysLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
		LoginDTO userForBase = toLoginService.userMesssage(username);
		String word = ShiroUtils.sha256(password, userForBase.getSalt());
		JSONObject jsonObject=new JSONObject();
		if(userForBase==null){
			jsonObject.put("msg","登录失败,用户不存在");
            jsonObject.put("code",200);
			return jsonObject;
		}else {
			if (!userForBase.getPassword().equals(word)){
				jsonObject.put("msg","登录失败,密码错误");
				jsonObject.put("code",300);
				return jsonObject;
			}else {
                String token = tokenService.getToken(userForBase);
                HsdLoginRecord hsdLoginRecord = new HsdLoginRecord();
                hsdLoginRecord.setUsername(userForBase.getUsername());
                loginService.save(hsdLoginRecord);
                jsonObject.put("msg","success");
                jsonObject.put("code",200);
				jsonObject.put("token", token);
				jsonObject.put("user", userForBase);
				return jsonObject;
			}
		}
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}

	/**
	 * 活跃用户量
	 */
	@UserLoginToken
	@RequestMapping("/sys/loginRecord/queryPeopleNum")
	@ResponseBody
	public R query(@RequestParam(value = "startTime",required = false) Date startTime,
				   @RequestParam(value = "endTime",required = false) Date endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start = null;
		String end = null;
		if(startTime != null && endTime != null) {
			start = sdf.format(startTime);
			end = sdf.format(endTime);
		}
		int num = loginService.queryPeopleNum(start, end);
		return R.ok().put("num",num);
	}
	
}
