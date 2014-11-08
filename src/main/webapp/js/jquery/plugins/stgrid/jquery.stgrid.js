/*
 * 顺淘科技Grid插件
 * 作者：caojian
 * 版本：V 1.0
 * 日期：2014-10-25
 */
;(function ($){
	//STGrid 函数主体
	$.fn.STGrid = function (opts,params) {
		//默认配置信息
		var defaults = {
			ajaxMethod : 'POST' //ajax加载方式
			,width : 780 //Grid的宽度
			,height : 380 //Grid的高度
			,pagination : true //是否分页查询
			,pageSize : 10 //每页显示的数量
			,maxPageSize : 500 //不分页时查询的最大数据条数
			,goPageNum : 5 //分页条中快捷定位数字的数量,小于5则不显示
			,queryParams : {} //查询条件
			,showSN : true //是否显示序号
			,selectAble : true //是否可选择表格
			,singleSelect : false //是否单选行
		};
		//检查opts参数
		if (!opts) {
			$.error('未指定opts参数！');
			return this;
		}
		//判断函数调用意图
		if (typeof opts === 'string') {
			//执行公用函数
			if (typeof $.fn.STGrid.methods[opts] === 'function') {
				return $.fn.STGrid.methods[opts](this,params);
			} else {
				$.error('未定义的函数：'+opts);
				return this;
			}
		} else if (typeof opts === 'object') {
			//初始化表格
			var options = $.extend(defaults, opts);
			init (this,options);
		}
		else {
			$.error('非法的 opts 参数！');
			return this;
		}
	};
	
	//初始化Grid，检查配置信息
	var init = function (jq,options) {
		if (!options.url) {
			$.error('初始化失败，未指定url参数！');
			return ;
		}
		if (!options.columns || options.columns.length == 0) {
			$.error('初始化失败，未指定columns参数！');
			return ;
		}
		if (jq.length == 0) {
			$.error('初始化失败，没有找到table元素！');
			return ;
		} 
		var thead = jq.find('thead');
		if (thead.length == 0) {
			$.error('初始化失败，没有找到thead元素！');
			return ;
		}
		var tbody = jq.find('tbody');
		if (tbody.length == 0) {
			$.error('初始化失败，没有找到tbody元素！');
			return ;
		}
		var tfoot = jq.find('tfoot');
		if (tfoot.length == 0) {
			$.error('初始化失败，没有找到tfoot元素！');
			return ;
		}
		//初始化表格样式
		jq.css('width',options.width);
		//将表格包装到DIV
		jq.wrap('<div class="tableWrap" style="width:'+options.width+'px;"></div>');
		//获取分页控制条容器DIV：pageBar并 设置宽度
		var tableId = jq.attr('id');
		var pageBarId = tableId+'-pageBar';
		var pageBar = $("#"+pageBarId);
		pageBar.css('width',options.width);
		//初始化thead
		for (var i=0; i<options.columns.length; i++) {
			var row = options.columns[i];
			var tr = $('<tr class="headStyle"></tr>').appendTo(thead);
			//显示序号
			if (options.showSN) {
				if (options.columns.length > 1) {
					$('<td rowspan="'+options.columns.length+'" style="width:50px; text-align:center;">序号</td>').appendTo(tr);
				} else {
					$('<td style="width:50px; text-align:center;">序号</td>').appendTo(tr);
				}
			}
			//是否能选择
			if (options.selectAble) {
				var _rowspan = '';
				if (options.columns.length > 1) {
					_rowspan = 'rowspan="'+options.columns.length+'"';
				}
				var _td = $('<td '+_rowspan+' style="width:40px; text-align:center;"></td>').appendTo(tr);
				if (!options.singleSelect) {
					$('<input id="chk_all" type="checkbox" />').appendTo(_td).bind('click',function () {
						var chk = $(this).prop('checked');
						if (chk) {
							selectAllRows (jq);
						} else {
							unSelectAllRows (jq);
						}
					});
				}
			}
			//初始化列标题
			for (var x=0; x<row.length; x++) {
				var col = row[x];
				var tmp = '';
				if (col.colspan && col.colspan > 1) {
					tmp += ' colspan="'+col.colspan+'" ';
				}
				if (col.rowspan && col.rowspan > 1) {
					tmp += ' rowspan="'+col.rowspan+'" ';
				}
				$('<td '+tmp+' style="width:'+col.width+'px; text-align:center;">'+col.title+'</td>').appendTo(tr);
			}
		}
		//检查完成，绑定设置信息到table元素
		if (jq.data('options')) {
			jq.removeData('options');
		}
		jq.data('options',options);
		//初始化完成 
		jq.data('init',true);
	};
	
	//从服务器加载数据
	var loadDataFromServer = function (jq, queryParams, pageNumber) {
		if (jq.data('init')) {
			var options = jq.data('options');
			var tbody = jq.find('tbody');
			//获取分页控制条容器DIV：pageBar并隐藏
			var tableId = jq.attr('id');
			var pageBarId = tableId+'-pageBar';
			var pageBar = $("#"+pageBarId);
			pageBar.hide();
			//如果存在全选checkbox，设置为未选中
			var sacb = $('#chk_all');
			if (sacb.length > 0) {
				sacb.prop('checked',false);
			}
			//创建请求参数对象，加入查询条件参数
			var _data = $.extend({variableParameter:getVariableParameter()},queryParams);
			if (options.pagination) {
				//分页查询，加入分页 参数
				if (pageNumber && pageNumber > 1) {
					$.extend(_data,{pageNumber:pageNumber, pageSize:options.pageSize});
				} else {
					$.extend(_data,{pageNumber:1, pageSize:options.pageSize});
				}
			} else {
				//如果不分页 ，则最多查询500条数据
				$.extend(_data,{pageNumber:1, pageSize:options.maxPageSize});
			}
			//加入ajax查询等待效果
			tbody.empty();
			var _td_num = jq.find('thead').find('tr:last').find('td').length;
			$('<tr style="line-height:50px;"><td colspan="'+_td_num+'"><div class="ajax-load-div"><div class="group"><div class="load-gif"></div><div class="load-txt">载入中，请稍候...</div></div></div></td></tr>').appendTo(tbody);
			//执行ajax查询
			$.ajax({type: options.ajaxMethod, url: options.url, data:_data, dataType: 'json', 
				success: function (response) {
					if (!response) {
						//查询数据失败
						tbody.empty();
						$('<tr style="line-height:50px;"><td colspan="'+_td_num+'"><div class="ajax-load-div"><div class="group"><div class="load-txt" style="color:red;">查询数据出错！</div></div></div></td></tr>').appendTo(tbody);
						return ;
					}
					if (!response.success) {
						//查询数据失败
						tbody.empty();
						$('<tr style="line-height:50px;"><td colspan="'+_td_num+'"><div class="ajax-load-div"><div class="group"><div class="load-txt" style="color:red;">'+response.errorMsg+'</div></div></div></td></tr>').appendTo(tbody);
						return ;
					}
					//查询 数据成功，解析数据并生成表格
					var pageObj = response.pageObj;
					var rows = response.rows;
					if (rows && rows.length > 0) {
						//循环创建表格并加入tbody中
						tbody.empty();
						for (var i=0; i<rows.length; i++) {
							var rowData = rows[i];
							var tr;
							///////////////////////////////创建TR条纹风格显示表格，分开处理奇数和偶数情况/////////////////////////////
							if (i % 2 == 1) {//奇数情况
								tr = $('<tr id="_stgrid_tr_'+i+'" class="bodyStyle2"></tr>').appendTo(tbody).bind('mouseover',function () {
									this.className = 'bodyStyleOver';//鼠标移入
								}).bind('mouseout',function () {
									this.className='bodyStyle2';//鼠标移出
								}).bind('dblclick',function () {//双击表格选中表格
									if (options.selectAble) {
										var _chk = $(this).find('._select_chk_');
										var _tmp = _chk.prop('checked');
										_chk.prop('checked',!_tmp);
										if (_tmp) {
											//不选
											var defaultCls = $(this).data('defaultCls');
											$(this).bind('mouseover',function(){this.className='bodyStyleOver';});
											$(this).bind('mouseout',function(){this.className=defaultCls;});
											$(this).attr('class','bodyStyleOver');
											//某个不选中，全选自动取消
											if (!options.singleSelect) {
												$('#chk_all').prop('checked',false);
											}
										} else {
											//选中
											$(this).attr('class','bodyStyleSelected');
											$(this).unbind('mouseover');
											$(this).unbind('mouseout');
											//控制单选
											if (options.singleSelect) {
												processSingleSelect (tbody, this);
											} else {
												//全部选中后，全选自动勾选
												if (tbody.find('._select_chk_:not(:checked)').length == 0) {
													$('#chk_all').prop('checked',true);
												}
											}
										}
									}
								}).data('defaultCls','bodyStyle2');//将表格的默认CSS绑定到表格数据区
							} else {//偶数情况
								tr = $('<tr id="_stgrid_tr_'+i+'" class="bodyStyle1"></tr>').appendTo(tbody).bind('mouseover',function () {
									this.className = 'bodyStyleOver';//鼠标移入
								}).bind('mouseout',function () {
									this.className='bodyStyle1';//鼠标移出
								}).bind('dblclick',function () {//双击表格选中表格
									if (options.selectAble) {
										var _chk = $(this).find('._select_chk_');
										var _tmp = _chk.prop('checked');
										_chk.prop('checked',!_tmp);
										if (_tmp) {
											//不选
											var defaultCls = $(this).data('defaultCls');
											$(this).bind('mouseover',function(){this.className='bodyStyleOver';});
											$(this).bind('mouseout',function(){this.className=defaultCls;});
											$(this).attr('class','bodyStyleOver');
											//某个不选中，全选自动取消
											if (!options.singleSelect) {
												$('#chk_all').prop('checked',false);
											}
										} else {
											//选中
											$(this).attr('class','bodyStyleSelected');
											$(this).unbind('mouseover');
											$(this).unbind('mouseout');
											//控制单选
											if (options.singleSelect) {
												processSingleSelect (tbody, this);
											} else {
												//全部选中后，全选自动勾选
												if (tbody.find('._select_chk_:not(:checked)').length == 0) {
													$('#chk_all').prop('checked',true);
												}
											}
										}
									}
								}).data('defaultCls','bodyStyle1');//将表格的默认CSS绑定到表格数据区
							}
							//////////////////////////////////////创建TD//////////////////////////////////////
							//显示序号，加入序号列
							if (options.showSN) {
								$('<td style="width:50px; text-align:center;">'+rowData['ROWNUMALIAS']+'</td>').appendTo(tr);
							}
							//是否能选择，加入选择checkbook列
							if (options.selectAble) {
								var _td = $('<td style="width:35px; text-align:center; text-indent: 0px;"></td>').appendTo(tr);
								//checkbook事件处理
								$('<input type="checkbox" class="_select_chk_" id="chk_'+rowData[options.idField]+'" value="'+rowData[options.idField]+'" />').appendTo(_td).bind('click',function () {
									//选择表格
									var _chk = $(this).prop('checked');
									//反向查找到TR
									var _ptr = $(this).closest('tr');
									if (_chk) {
										//选中
										_ptr.attr('class','bodyStyleSelected');
										_ptr.unbind('mouseover');
										_ptr.unbind('mouseout');
										//控制单选
										if (options.singleSelect) {
											processSingleSelect (tbody, _ptr);
										} else {
											//全部选中后，全选自动勾选
											if (tbody.find('._select_chk_:not(:checked)').length == 0) {
												$('#chk_all').prop('checked',true);
											}
										}
									} else {
										//未选中
										var defaultCls = $(_ptr).data('defaultCls');
										_ptr.bind('mouseover',function(){this.className='bodyStyleOver';});
										_ptr.bind('mouseout',function(){this.className=defaultCls;});
										_ptr.attr('class','bodyStyleOver');
										//某个不选中，全选自动取消
										if (!options.singleSelect) {
											$('#chk_all').prop('checked',false);
										}
									}
								});
							}
							//遍历数据并创建表格数据列
							var row = options.columns[options.columns.length - 1];
							for (var x=0; x<row.length; x++) {
								var col = row[x];
								$('<td style="width:'+col.width+'px; text-align:'+col.align+';">'+rowData[col.field]+'</td>').appendTo(tr);
							}
						}
						//////////////////////////////////////数据解析完成，表格生成 完成，加入分页控制//////////////////////////////////////
						if (options.pagination) {
							//将pageObj和queryParams存放到表格数据区
							if (jq.data('pageObj')) {
								jq.removeData('pageObj');
							}
							jq.data('pageObj',pageObj);
							if (jq.data('queryParams')) {
								jq.removeData('queryParams');
							}
							jq.data('queryParams',queryParams);
							pageBar.empty();
							//计算页数
							var pageNum = parseInt((parseInt(pageObj.total)+(parseInt(pageObj.pageSize)-1)) / parseInt(pageObj.pageSize));//计算总页数
							var pageStr = '<div class="chgPag" style="width:'+options.width+'px; text-align: center;">记录：'+pageObj.total+'条&nbsp;&nbsp;&nbsp;共：'+pageNum+'页&nbsp;&nbsp;&nbsp;第：'+pageObj.pageNumber+'页&nbsp;&nbsp;&nbsp;';
							//控制 上一页 下一页 显示
							if (pageNum == 1) {
								//只有一页
							} else if (pageNum > 1) {
								//显示数字快捷定位
								var goPgeIndexStr = '';
								var m = options.goPageNum;
								//当设置为大于等于5的数字时，才显示快捷定位条
								if (m >= 5) {
									if (pageNum <= m) {
										for (var i=1;i<=pageNum;i++) {
											if (i == pageObj.pageNumber) {
												goPgeIndexStr += '<a href="javascript:void(0);" class="lnkPagOn">'+i+'</a>';
											} else {
												goPgeIndexStr += '<a class="lnkPag" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'loadPage\','+i+');">'+i+'</a>';
											}
										}
									} else {
										var py = (options.goPageNum % 2 == 1) ? 0 : 1;
										//当前页：pageObj.pageNumber
										//总页数：pageNum
										//页码个数：options.goPageNum
										//偏移量：py
										if (pageObj.pageNumber <= parseInt(options.goPageNum/2) + py) {
											for (var i=1;i<=options.goPageNum;i++) {
												if (i == pageObj.pageNumber) {
													goPgeIndexStr += '<a href="javascript:void(0);" class="lnkPagOn">'+i+'</a>';
												} else {
													goPgeIndexStr += '<a class="lnkPag" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'loadPage\','+i+');">'+i+'</a>';
												}
											} 
										} else if (pageObj.pageNumber >= pageNum-(parseInt(options.goPageNum/2)+py)){
											for (var i=pageNum-options.goPageNum+1;i<=pageNum;i++) {
												if (i == pageObj.pageNumber) {
													goPgeIndexStr += '<a href="javascript:void(0);" class="lnkPagOn">'+i+'</a>';
												} else {
													goPgeIndexStr += '<a class="lnkPag" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'loadPage\','+i+');">'+i+'</a>';
												}
											} 
										} else {
											for (var i=pageObj.pageNumber-parseInt(options.goPageNum/2);i<=pageObj.pageNumber+parseInt(options.goPageNum/2)-py;i++) {
												if (i == pageObj.pageNumber) {
													goPgeIndexStr += '<a href="javascript:void(0);" class="lnkPagOn">'+i+'</a>';
												} else {
													goPgeIndexStr += '<a class="lnkPag" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'loadPage\','+i+');">'+i+'</a>';
												}
											} 
										}
									}
								}
								//上一页 下一页
								if (pageObj.pageNumber == 1) {
									pageStr += '<a href="javascript:void(0);" class="lnkPagNone">上一页</a>';
									pageStr += goPgeIndexStr;
									pageStr += '<a class="lnkPagBtn" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'goNextPage\');">下一页</a>';
								} else if (pageObj.pageNumber == pageNum) {
									pageStr += '<a class="lnkPagBtn" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'goPrevPage\');">上一页</a>';
									pageStr += goPgeIndexStr;
									pageStr += '<a href="javascript:void(0);" class="lnkPagNone">下一页</a>';
								} else {
									pageStr += '<a class="lnkPagBtn" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'goPrevPage\');">上一页</a>';
									pageStr += goPgeIndexStr;
									pageStr += '<a class="lnkPagBtn" href="javascript:void(0);" onclick="javascript:jQuery(\'#'+tableId+'\').STGrid(\'goNextPage\');">下一页</a>';
								}
							} else {}
							pageStr += '</div>';
							//加入分页控制条到DIV
							$(pageStr).appendTo(pageBar);
							pageBar.show();
						}
					} else {
						//没有查询到数据
						tbody.empty();
						$('<tr style="line-height:50px;"><td colspan="'+_td_num+'"><div class="ajax-load-div"><div class="group"><div class="load-txt" style="color:blue;">没有符合条件的数据！</div></div></div></td></tr>').appendTo(tbody);
					}
				},
				error: function () {
					//查询出错
					tbody.empty();
					$('<tr style="line-height:50px;"><td colspan="'+_td_num+'"><div class="ajax-load-div"><div class="group"><div class="load-txt" style="color:red;">查询数据出错，网络错误！</div></div></div></td></tr>').appendTo(tbody);
				}
			});
		} else {
			$.error('Grid未初始化！');
		}
	};
	
	//加载上一页
	var loadPrevPage = function (jq, args) {
		var  pageObj = jq.data('pageObj');
		var queryParams = jq.data('queryParams');
		var pageNumber = pageObj.pageNumber-1;
		if (pageNumber >= 1) {
			loadDataFromServer (jq, queryParams, pageNumber);
		}
	};
	
	//加载下一页
	var loadNextPage = function (jq, args) {
		var  pageObj = jq.data('pageObj');
		var queryParams = jq.data('queryParams');
		//计算页数
		var pageNum = parseInt((parseInt(pageObj.total)+(parseInt(pageObj.pageSize)-1)) / parseInt(pageObj.pageSize));//计算总页数
		var pageNumber = pageObj.pageNumber+1;
		if (pageNumber <= pageNum) {
			loadDataFromServer (jq, queryParams, pageNumber);
		}
	};
	
	//加载指定页
	var loadPageByIndex = function (jq, index) {
		var  pageObj = jq.data('pageObj');
		var queryParams = jq.data('queryParams');
		//计算页数
		var pageNum = parseInt((parseInt(pageObj.total)+(parseInt(pageObj.pageSize)-1)) / parseInt(pageObj.pageSize));//计算总页数
		index = parseInt (index);
		if (index >= 1 && index <= pageNum) {
			loadDataFromServer (jq, queryParams, index);
		}
	};
	
	//重新加载当前页
	var reloadPage = function (jq, args) {
		var  pageObj = jq.data('pageObj');
		var queryParams = jq.data('queryParams');
		loadDataFromServer (jq, queryParams, pageObj.pageNumber);
	};
	
	//全选 表格
	var selectAllRows = function (jq,args) {
		var tbody = jq.find('tbody');
		tbody.find("tr").each(function () {
			this.className = 'bodyStyleSelected';
			$(this).unbind('mouseover');
			$(this).unbind('mouseout');
			$(this).find("._select_chk_").prop('checked',true);
		});
	};
	
	//取消全选
	var unSelectAllRows = function (jq, args) {
		var tbody = jq.find('tbody');
		tbody.find("tr").each(function () {
			var defaultCls = $(this).data('defaultCls');
			this.className = defaultCls;
			$(this).bind('mouseover',function(){this.className='bodyStyleOver';});
			$(this).bind('mouseout',function(){this.className=defaultCls;});
			$(this).find("._select_chk_").prop('checked',false);
		});
	};
	
	//控制表格单选
	var processSingleSelect = function (tbody, tr) {
		tbody.find("._select_chk_:checked").each(function () {
			$(this).prop('checked',false);
			var _tr = $(this).closest('tr');
			var defaultCls = _tr.data('defaultCls');
			_tr.attr('class',defaultCls);
			_tr.bind('mouseover',function(){this.className='bodyStyleOver';});
			_tr.bind('mouseout',function(){this.className=defaultCls;});
		});
		$(tr).find("._select_chk_").prop('checked',true);
		$(tr).attr('class','bodyStyleSelected');
		$(tr).unbind('mouseover');
		$(tr).unbind('mouseout');
	};
	
	//获取选择的行，返回idField数组
	var getSelectIds = function (jq, args) {
		var result = new Array();
		var tbody = jq.find('tbody');
		tbody.find('._select_chk_:checked').each(function (){
			var t = $(this).val();
			if (t != null && t != undefined && $.trim(t) != '') {
				result.push(t);
			}
		});
		return result;
	};
	
	//修改表格尺寸
	var resizeGrid = function (jq, args) {
		jq.css('width',args.width);		
		jq.closest('div').css('width',args.width);
		var tableId = jq.attr('id');
		var pageBarId = tableId+'-pageBar';
		var pageBar = $("#"+pageBarId);
		pageBar.css('width',args.width);
		pageBar.find(".chgPag").css('width',args.width);
	};
	
	//生成变化参数
	var getVariableParameter = function () {
		var theday = new Date();
		var theyear = theday.getFullYear();
		var themonth = theday.getMonth()+1;
		var thedays = theday.getDate();
		var thehours = theday.getHours();
		var theminutes = theday.getMinutes();
		var theseconds = theday.getSeconds();
		var customRandom = Math.random();
		return (theyear+"-"+themonth+"-"+thedays+"-"+thehours+"-"+theminutes+"-"+theseconds+"-"+customRandom);
	};
	
	//暴露的公用函数
	$.fn.STGrid.methods = {
		//加载数据
		load : function (jq,args) {
			loadDataFromServer (jq, args);
		},
		//加载上一页
		goPrevPage : function (jq, args) {
			loadPrevPage (jq, args);
		},
		//加载下一页
		goNextPage : function (jq, args) {
			loadNextPage (jq, args);
		},
		//加载指定页
		loadPage : function (jq, args) {
			loadPageByIndex (jq, args);
		},
		//重新加载当前页
		reload : function (jq, args) {
			reloadPage(jq, args);
		},
		//全选表格
		selectAll : function (jq, args) {
			selectAllRows (jq, args);
		},
		//取消全选
		unSelectAll : function (jq, args) {
			unSelectAllRows (jq, args);
		},
		//获取选择的行，返回idField数组
		getSelections : function (jq, args) {
			return getSelectIds (jq, args);
		},
		//改变表格大小
		resize : function (jq, args) {
			resizeGrid (jq, args);
		}
	};
})(jQuery);