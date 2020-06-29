package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@RequestMapping("/toUpload")
	public String hello() {
		System.out.println("登录");
		return "/upload";
	}

	@ResponseBody
	@RequestMapping("/xiazai")
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			// 上传文件路径
			// String path = request.getServletContext().getRealPath(
			// "/upload/");
			String path = "D://mxUPD/";
			System.out.println("path = " + path);
			// 上传文件名
			String filename = file.getOriginalFilename();
			// 新文件名
			// int lastIndexOf = filename.lastIndexOf(".");
			String time = new SimpleDateFormat("YYYYMMddhhmmss").format(new Date());
			System.out.println(time);
			// + * . | 都要加\\
			String[] split = filename.split("\\.");
			// \要这分割
			// String[] aa = "aaa\\bbb\\bccc".split("\\\\");
			String newFilename = split[0] + time + "." + split[1];
			File filepath = new File(path, newFilename);
			// 判断路径是否存在，如果不存在就创建一个
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			// 将上传文件保存到一个目标文件当中
			file.transferTo(new File(path + File.separator + newFilename));
			return "success";
		} else {
			return "error";
		}
	}

	@ResponseBody
	@RequestMapping("/download")
	public String downLoad(HttpServletResponse response) {
		String filename = "周杰伦 - 超 人不 会 飞.mp3";
		String filePath = "d:/mxUPD";
		File file = new File(filePath + "/" + filename);
		if (file.exists()) { // 判断文件父目录是否存在
			response.setContentType("application/force-download");
			// 名字不支持中文的（目前还不知道为什么）现在知道了加上这个 URLEncoder.encode(filename, "UTF-8")
			response.setHeader("Content-Disposition", "attachment;fileName=" + "111.mp3");

			byte[] buffer = new byte[1024];
			FileInputStream fis = null; // 文件输入流
			BufferedInputStream bis = null;

			OutputStream os = null; // 输出流
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer);
					i = bis.read(buffer);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----------file download" + filename);
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 是不执行的
		return "success";
	}

}
