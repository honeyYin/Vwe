$(document).ready( function() {
	// 自定义的编辑器配置项,此处定义的配置项将覆盖editor_config.js中的同名配置
	var articleAddEditorOption = {
		// window.UEDITOR_HOME_URL:"/news/ueditor/"
		// 这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
		// toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold']],
		toolbars:[
            ['source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch','autotypeset', '|',
                'blockquote', '|', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','selectall', 'cleardoc', '|', 'customstyle',
                'paragraph', '|','rowspacingtop', 'rowspacingbottom','lineheight', '|','fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', '|', '', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|','touppercase','tolowercase','|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright',
                'imagecenter', '|', 'insertimage', 'emotion','scrawl', 'insertvideo', 'attachment', 'map', 'gmap','pagebreak','template','background', '|',
                'horizontal', 'date', 'time', 'spechars', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|',
                'print', 'preview', 'searchreplace']
        ],
        //上传图片的最大宽度
        maxImageSideLength:560, 
		// focus时不清空初始化时的内容
		autoClearinitialContent : false,
		// 关闭字数统计
		wordCount : false,
		// 关闭elementPath
		elementPathEnabled : false
	};
	
	var articleAddEditor = new baidu.editor.ui.Editor(articleAddEditorOption);

	//ueditor
	function ueditor()
	{
		articleAddEditor.render('newsContent');
	}
	
	ueditor();
});