package com.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dao.PaperImageDao;
import com.entity.PaperImage;
import com.util.DateUtil;
import com.util.StringUtil;
import com.util.UploadUtil;

@Controller
public class UploadAction extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final DecimalFormat   df   =new   java.text.DecimalFormat("0.0000");
	@Autowired
	private PaperImageDao imageDao;
	
	@RequestMapping(value="/upload",method={RequestMethod.POST})
	public String upload(HttpServletRequest request,
						 @RequestParam(value = "filedata", required = false) MultipartFile file,
						 Model model) {
		 String titleImg = "{size}";
		try{
	        //  获得第1张图片（根据前台的name名称得到上传的文件）   
	        if(!(file.getOriginalFilename() ==null || "".equals(file.getOriginalFilename()))) {  
	        	
	    		String savePath = request.getRealPath("/");
	    		String mk = "res/upload/" + UploadUtil.retDateStringMk();
	    		
	    		titleImg = this.getFile(file,savePath,mk);  
	    		titleImg = mk+"/"+titleImg;
	//    		titleImg = savePath+mk+"/"+titleImg;
	    		System.out.println(titleImg);
	        }  
		}catch(Exception ex){
			
		}
		return "{"+titleImg+"}";
	}
	private String getFile(MultipartFile imgFile,String path,String mk) throws UnsupportedEncodingException {  
	        String orFileName = imgFile.getOriginalFilename();  
//	        fileName = new String (fileName.getBytes("ISO8859-1"),"UTF-8");
	        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
	         String ext = orFileName.substring(orFileName.lastIndexOf(".")+1,orFileName.length());  
	         //对扩展名进行小写转换  
	         ext = ext.toLowerCase();  
			 String fileName = DateUtil.getStringDateShort2()+StringUtil.getRandomStringWithNum(6)+"."+ext;
	         //创建目录
	         UploadUtil.mkDirectory(path+mk);
	         
	         File firstFolder = new File(path+mk);     
	         firstFolder.mkdir();  
	         File file = new File(firstFolder,fileName);   
             try {  
                imgFile.transferTo(file);   //保存上传的文件 ,目录不存在时
	              //获取宽高
	   	         getFileLH(file,mk+"/"+fileName);
            } catch (IllegalStateException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }      
	         return fileName;  
	 }  
	
	@SuppressWarnings("unchecked")
	private void getFileLH(File imgFile,String fileName){
		try{
         	 ImageInputStream imagein= ImageIO.createImageInputStream(imgFile);
         	 BufferedImage bufferedImg = ImageIO.read(imagein);
         	 int imgWidth = bufferedImg.getWidth();
         	 int imgHeight = bufferedImg.getHeight();
         	 
         	 PaperImage entity = new PaperImage();
         	 entity.setImgUrl(fileName);
         	 entity.setHeight(imgHeight);
         	 entity.setWidth(imgWidth);
         	 double ratio  = (double)imgWidth/imgHeight;
         	 entity.setRatio(df.format(ratio));
         	 entity.setSize(imgFile.length());
         	 imageDao.save(entity);
		}catch(Exception e){
         		e.printStackTrace();
         		logger.warn("record img size error",e);
        }
	}
	
}
