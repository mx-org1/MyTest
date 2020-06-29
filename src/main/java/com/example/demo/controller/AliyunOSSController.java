package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.example.demo.util.AliyunOSSUtil;

/**
 * @Author: SnailClimb
 * @Date: 2018/12/2 16:56
 * @Description: 阿里云OSS服务Controller
 */
@Controller
@RequestMapping("/oss")
public class AliyunOSSController {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AliyunOSSUtil aliyunOSSUtil;

	/**
	 * 跳转到阿里云上传页面
	 *
	 * @return
	 */
	@RequestMapping("/toOSS")
	public String toOSS() {
		return "OSS";

	}

	/**
	 * 测试上传文件到阿里云OSS存储
	 *
	 * @return
	 */
	@RequestMapping("/testUpload")
	@ResponseBody
	public String testUpload() {
		File file = new File("E:/Picture/test.jpg");
		AliyunOSSUtil aliyunOSSUtil = new AliyunOSSUtil();
		String url = aliyunOSSUtil.upLoad(file);
		System.out.println(url);
		return "success";
	}

	/**
	 * 通过文件名下载文件
	 */
	@RequestMapping("/testDownload")
	@ResponseBody
	public String testDownload() {
		AliyunOSSUtil aliyunOSSUtil = new AliyunOSSUtil();
		aliyunOSSUtil.downloadFile("test/2018-12-04/e3f892c27f07462a864a43b8187d4562-rawpixel-600782-unsplash.jpg",
				"E:/Picture/e3f892c27f07462a864a43b8187d4562-rawpixel-600782-unsplash.jpg");
		return "success";
	}

	/**
	 * 列出某个文件夹下的所有文件
	 */
	@RequestMapping("/testListFile")
	@ResponseBody
	public String testListFile() {
		AliyunOSSUtil aliyunOSSUtil = new AliyunOSSUtil();
		aliyunOSSUtil.listFile();
		return "success";
	}

	/**
	 * 文件上传（供前端调用）
	 */
	@RequestMapping(value = "/uploadFile")
	public String uploadPicture(@RequestParam("file") MultipartFile file, Model model) {
		logger.info("文件上传");
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		try {
			if (file != null) {
				if (!"".equals(filename.trim())) {
					/*
					 * //以前的上传要先下载到本地再上传，其实可以虚拟下载
					 * File newFile = new File("D://mxUPD/" + filename);
					 * file.transferTo(newFile);
					 */
					File newFile = new File(filename);
					FileOutputStream os = new FileOutputStream(newFile);
					os.write(file.getBytes());
					os.close();
					file.transferTo(newFile);
					// 上传到OSS
					String uploadUrl = AliyunOSSUtil.upLoad(newFile);
					model.addAttribute("url", uploadUrl);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "OSSSuccess";
	}

	@ResponseBody
	@RequestMapping(value = "/uploadFileWx")
	public String uploadPictureWx(@RequestParam("file") MultipartFile file, Model model) {
		logger.info("文件上传");
		System.out.println("文件上传");
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		String uploadUrl = null;
		try {

			if (file != null) {
				if (!"".equals(filename.trim())) {
					File newFile = new File("D://mxUPD/" + filename);
					/*
					 * FileOutputStream os = new FileOutputStream(newFile);
					 * os.write(file.getBytes()); os.close();
					 */
					file.transferTo(newFile);
					// 上传到OSS
					uploadUrl = aliyunOSSUtil.upLoad(newFile);
					model.addAttribute("url", uploadUrl);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return uploadUrl;
	}

	//@ResponseBody  这里不知道为啥不加不报错
	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("文件下载");
			String fileName = "test/2019-07-09/74ef3e8fb89a44ebb1be54d078822a8a-图2.jpg";
			// 从阿里云进行下载
			OSSObject ossObject = AliyunOSSUtil.downloadWithAttach(fileName);
			// 已缓冲的方式从字符输入流中读取文本，缓冲各个字符，从而提供字符、数组和行的高效读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));

			InputStream inputStream = ossObject.getObjectContent();

			// 缓冲文件输出流
			BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			// 通知浏览器以附件形式下载
			// response.setHeader("Content-Disposition","attachment;filename="+
			// URLEncoder.encode(fileName,"UTF-8"));
			// 为防止 文件名出现乱码
			response.setContentType("application/doc");
			final String userAgent = request.getHeader("USER-AGENT");
			if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");// 其他浏览器
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开

			// 进行解码 如果上传时为了防止乱码 进行解码使用此方法
			// BASE64Decoder base64Decoder = new BASE64Decoder();//现在已经不用这个了用下边这个
			/*
			 * Encoder encoder = Base64.getEncoder(); byte[] encode =
			 * encoder.encode("abc".getBytes()); System.out.println(new String(encode));
			 * Decoder decoder=Base64.getDecoder(); byte[] decode = decoder.decode("abc");
			 * System.out.println(new String(decode));
			 */
//        byte[] car;
//        while (true) {
//            String line = reader.readLine();
//            if (line == null) break;
//            car =  base64Decoder.decodeBuffer(line);
//
//            outputStream.write(car);
//        }
//        reader.close();

			byte[] car = new byte[1024];
			int L;

			while ((L = inputStream.read(car)) != -1) {
				if (car.length != 0) {
					outputStream.write(car, 0, L);
				}
			}

			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();

		} catch (OSSException e) {

		}
	}
}