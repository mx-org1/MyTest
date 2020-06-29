
package com.example.demo.controller.after927;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.AliyunOSSUtil;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月25日 下午4:28:44

* 类说明 vue文件上传和下载

*/
@Controller
public class VueUploadController {

	 @ResponseBody
	 @RequestMapping("vueUpload")
	 public String vueUpload(@ModelAttribute MultipartFile[] files) {
		 System.out.println("vue文件上传");
		 for (MultipartFile file : files) {
			 String filename = file.getOriginalFilename();
			 System.out.println(filename);
			 try {
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
						}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}

		 return "success";
	 }

	 	@ResponseBody
		@RequestMapping("/vueDownload")
		public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
			System.out.println("vue下载");
	 		String filename = "周杰伦-超人不会飞.mp3";
			String filePath = "d:/mxUPD";
			File file = new File(filePath + "/" + filename);
			if (file.exists()) { // 判断文件父目录是否存在
				response.setContentType("application/force-download");
				// 名字不支持中文的（目前还不知道为什么）现在知道了加上这个 URLEncoder.encode(filename, "UTF-8")
				response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "UTF-8"));

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
					e.printStackTrace();
				}
				System.out.println("----------file download" + filename);
				try {
					bis.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 是不执行的
			return "success";
		}

}
