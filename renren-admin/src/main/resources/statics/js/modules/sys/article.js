$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/article/list',
        datatype: "json",
        colModel: [			
			{ label: '标 题', name: 'title', index: 'title', width: 80 },
			{ label: '类型', name: 'cid', index: 'cit', width: 80 },
			{ label: '更新时间', name: 'updateTime', index: 'updateTime', width: 80 },
			{ label: '备注', name: 'note', index: 'note', width: 80 }
		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');
//
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function(action) {
    if (action == 'uploadimage' || action == 'uploadscrawl') {
        // console.log("-action-:"+ action);
        return "http://localhost:8080/renren-admin/sys/article/upload"; //在这里返回我们实际的上传图片地址
    }else {
        // console.log("``````:" + action);
        // return "http://localhost:8080/renren-admin/sys/article/ueditor"; //在这里返回我们实际的上传图片地址
        return this._bkGetActionUrl.call(this, action);
	}
}
	// ue.serverUrl = baseURL + "sys/article/upload";
var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            name: null
        },
		showList: true,
		title: null,
		article: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.article = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.article.id == null ? "sys/article/save" : "sys/article/update";
			if (vm.article.id){
                vm.article.content = ue.getContent();

                // console.log("content:"+ vm.article.content);
			}
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.article),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/article/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/article/info/"+id, function(r){
                vm.article = r.article;
				ue.setContent(vm.article.content);
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		}
	}
});