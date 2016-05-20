<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<% 
    //仅做示例用，请自行修改
	String path = "upload/images";
	String imgStr ="";//所有图片的路径 
	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	path += "/" + formater.format(new Date());
	String realpath = getRealPath(request)+"/"+path;
	List<File> files = getFiles(realpath,new ArrayList());
	for(File file :files ){
		imgStr+=file.getPath().replace(getRealPath(request),"")+"ue_separate_ue";
	}
	if(imgStr!=""){
        imgStr = imgStr.substring(0,imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
    }
	out.print(imgStr);		
%>
<%!
public List getFiles(String realpath, List files) {
	
	File realFile = new File(realpath);
	if (realFile.isDirectory()) {
		File[] subfiles = realFile.listFiles();
		for(File file :subfiles ){
			if(file.isDirectory()){
				getFiles(file.getAbsolutePath(),files);
			}else{
				if(!getFileType(file.getName()).equals("")) {
					files.add(file);
				}
			}
		}
	}
	return files;
}
/*
** 图片管理的根路径
*/
public String getRealPath(HttpServletRequest request){
	ServletContext application = request.getSession().getServletContext();
	String str = application.getRealPath("//");
	return new File(str)+"";
}

public String getFileType(String fileName){
	String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
	Iterator<String> type = Arrays.asList(fileType).iterator();
	while(type.hasNext()){
		String t = type.next();
		if(fileName.endsWith(t)){
			return t;
		}
	}
	return "";
}
%>