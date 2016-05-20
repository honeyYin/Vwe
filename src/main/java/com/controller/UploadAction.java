package com.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.util.UploadUtil;

@Controller
public class UploadAction extends BaseController{
	
	
	@RequestMapping(value="/upload",method={RequestMethod.POST})
	public String upload(HttpServletRequest request,
						 @RequestParam(value = "filedata", required = false) MultipartFile file,
						 Model model) {
        //  获得第1张图片（根据前台的name名称得到上传的文件）   
        String titleImg = "{size}";
        if(!(file.getOriginalFilename() ==null || "".equals(file.getOriginalFilename()))) {  
        	
    		String savePath = request.getRealPath("/");
    		String mk = "res/upload/" + UploadUtil.retDateStringMk();
    		
    		titleImg = this.getFile(file,savePath,mk);  
    		titleImg = mk+"/"+titleImg;
//    		titleImg = savePath+mk+"/"+titleImg;
    		System.out.println(titleImg);
        }  
       
		return "{"+titleImg+"}";
	}
	private String getFile(MultipartFile imgFile,String path,String mk) {  
	        String fileName = imgFile.getOriginalFilename();  
	        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
	         String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());  
	         //对扩展名进行小写转换  
	         ext = ext.toLowerCase();  
	         //创建目录
	         UploadUtil.mkDirectory(path+mk);
	         
	         File firstFolder = new File(path+mk);     
	         firstFolder.mkdir();  
	         File file = new File(firstFolder,fileName);   
             try {  
                imgFile.transferTo(file);                   //保存上传的文件 ,目录不存在时
            } catch (IllegalStateException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }      
	         return fileName;  
	 }  
}
