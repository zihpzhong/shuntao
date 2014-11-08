;(function($){
    
	//函数主体
	$.fn.stAutocomplete = function (opts) {
		 return this.each(function(){
             new jQuery.stAutoComplete(this , opts);
         });
	};
	
    jQuery.stAutoComplete = function(obj , opts){
    	
        var $input = $(obj);
        var $provList = null ;
        
        //产生一个选择器
        var $container = setupContainer();
        
	    $input.keyup(function(event){
	        	
	        switch (event.keyCode) {
		            case 40:
		                return;
		            case 38:
		                return ;
		            case 9:
		                return ;
		            case 27:
		                return ;
		            case 108:     //回车键 
		                return ;
	        }
        	
            //得到输入框的位置
            var offset = $input.offset();
            //得到输入框的高度,宽度
            var height = $input.height();
            var width = $input.width();
            //计算选择器的位置
            var cont_top = offset.top + height;
            var cont_left = offset.left;
            //设置选择器出现的位置
            $container.appendTo($("body")).css({
                'top': cont_top + 8,
                'left': cont_left
            });
            
            var _data = $.extend({query:$input.val()},opts.param);
            $.ajax({type: "post", url: opts.url, data:_data, dataType: 'json', 
				success: function (response) {
					var data = response ;
					if(data != null && data.length > 0)
						callback4CreateDiv(data , $provList) ;
				}
			});
            
            if ($container.is(":hidden")) {
                //当前选择器是隐藏的
                $container.show();
                event.stopPropagation();
            }
            else 
                if ($container.is(":visible")) {
                    //当前选择器是显示的
                    //停止事件冒泡
                    event.stopPropagation();
                }
        });
        //点击在选择器以外，隐藏它
        $(document).bind("click", function(event){
            var $target = $(event.target);
            var hideFag = $target.parent("div").attr("id");
            if (!(hideFag == "citypicker_container" || hideFag == "citypicker_caption" || hideFag == "citypicker_city" || hideFag == "citypicker_pro")) {
                $container.hide();
            }
        });
        
        //初始化选择器函数
        function setupContainer(){
            var container = document.createElement("div");
            var caption = document.createElement("div");
            var proList = document.createElement("div");
            
            
            var $container = $(container).attr("id", "citypicker_container");
            $container.addClass("mod_list_city") ;
            var $caption = $(caption).attr("id", "citypicker_caption");
            $caption.addClass("city_top");
            $provList = $(proList).attr("id", "citypicker_pro");
            $provList.addClass("list_wrap");
           
            $container.append($caption).append($provList);
            
            $("<strong>选择城市</strong>").appendTo($caption);
            $("<span href='javascript:void;' style='float:right;cursor:hand;'>关闭</span>").appendTo($caption).click(function(){
                $container.slideUp(100);
            });
            
            return $container;
        }
    }
})(jQuery);