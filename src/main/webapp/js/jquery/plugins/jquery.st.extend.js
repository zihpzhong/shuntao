/*
 * jQuery 函数扩展
 * 顺淘科技JS库
 * 版本： V 1.0
 * 日期：2014-10-26
 */
jQuery.ST = {
	//生成变化参数
	getVariableParameter : function () {
		var theday = new Date();
		var theyear = theday.getFullYear();
		var themonth = theday.getMonth()+1;
		var thedays = theday.getDate();
		var thehours = theday.getHours();
		var theminutes = theday.getMinutes();
		var theseconds = theday.getSeconds();
		var customRandom = Math.random();
		return (theyear+"-"+themonth+"-"+thedays+"-"+thehours+"-"+theminutes+"-"+theseconds+"-"+customRandom);
	},
	//只允许输入数字
	numberAllowed : function(e) {
		var key = window.event ? e.keyCode : e.which;
		//IE
		if (window.event) {
			if (key < 48 || key > 57) {
				if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
					e.preventDefault ();
				} else {
					e.returnValue = false;
				}
			}
		}
		//FireFox
		else {
			if ((key != 0) && (key != 8) && (key < 48 || key > 57)) {
				e.preventDefault();
			}
		}
	},
	//只允许输入数字和小数点
	numberAndPointAllowed : function(e) {
		var key = window.event ? e.keyCode : e.which;
		//IE
		if (window.event) {
			if ((key < 48 || key > 57) && key != 46) {
				if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
					e.preventDefault ();
				} else {
					e.returnValue = false;
				}
			}
		}
		//FireFox
		else {
			if ((key != 0) && (key != 8) && (key != 46) && (key < 48 || key > 57)) {
				e.preventDefault();
			}
		}
	},
	//不允许输入空格
	notAllowSpace : function(e) {
		var key = window.event ? e.keyCode : e.which;
		//IE
		if (window.event) {
			if (key == 32) {
				if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
					e.preventDefault ();
				} else {
					e.returnValue = false;
				}
			}
		}
		//FireFox
		else {
			if (key == 32) {
				e.preventDefault();
			}
		}
	},
	//判断一个字符串是否为字母、数字、下划线、汉字或其组合
	isChineseOrNumberOrLetter : function (value) {
		var result = true;
		if (value == null || value == undefined) {
			result = false;
		}
		else if (value.value == "" || value.replace(/^\s+|\s+$/g,"").length == 0) {
			result = false;
		}
		else {
			var reg = /^[_]+$/i;
			var reg_numOrLetter = /^[0-9a-zA-Z]+$/i;
			var reg_chinese = /^[\u4e00-\u9fa5]+$/i;
			for (var k = 0 ; k < value.length ; k ++ ) {
				var tempValue = value.charAt(k);
				if (!(reg.test(tempValue) || reg_numOrLetter.test(tempValue) || reg_chinese.test(tempValue))) {
					result = false;
					break;
				}
			} 
		}
		return result;
	},
	//文件下载
	downloadFile : function(file,newFileName,absolutePath) {
		if (newFileName) {
			if (absolutePath) {
				window.self.location.href = jsBasePath+'fileDownload?file='+encodeURI(file)+'&newFileName='+encodeURI(newFileName)+'&absolutePath=1';
			} else {
				window.self.location.href = jsBasePath+'fileDownload?file='+encodeURI(file)+'&newFileName='+encodeURI(newFileName);
			}
		} else {
			if (absolutePath) {
				window.self.location.href = jsBasePath+'fileDownload?file='+encodeURI(file)+'&absolutePath=1';
			} else {
				window.self.location.href = jsBasePath+'fileDownload?file='+encodeURI(file);
			}
		}
	},
	//即时时间显示
	clockon : function (id){
		var now = new Date();
		var year = now.getYear();
		var month = now.getMonth();
		var date = now.getDate();
		var day = now.getDay();
		var hour = now.getHours();
		var minu = now.getMinutes();
		var sec = now.getSeconds();
		var week;
		month = month+1;
		if(month<10)month="0"+month;
		if(date<10)date="0"+date;
		if(hour<10)hour="0"+hour;
		if(minu<10)minu="0"+minu;
		if(sec<10)sec="0"+sec;
		var arr_week = new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
		week = arr_week[day];
		var time = "";
		time = year+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
		if(navigator.userAgent.indexOf("Firefox")>0){
		time = (year+1900)+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
		document.getElementById(id).innerHTML=time;
		}
		else{
		time = year+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
		document.getElementById(id).innerHTML=time;
		}
		setTimeout("clockon()",200);
	}	
};