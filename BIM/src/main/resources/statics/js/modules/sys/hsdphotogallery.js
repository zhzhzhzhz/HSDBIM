$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/photoGallery/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: 'id', width: 10 ,key:true},
			{ label: '图片描述', name: 'name', index: 'name', sortable:false, width: 30 },
            { label: '图片', name: 'url',  width: 60, align: "center", sortable: false, editable: false, formatter: alarmFormatter },

		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 45,
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

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            name: null
        },
		showList: true,
		title: null,
		hsdPhoto: {},
        fileList:[],
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.hsdPhoto = {};
            vm.fileList = [];
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
			var url = vm.hsdPhoto.id == null ? "sys/photoGallery/save" : "sys/photoGallery/update";
            var formdata=new FormData();
            if(url=="sys/photoGallery/update"){
                formdata.append("id",vm.hsdPhoto.id);
            }
            /*if(vm.fileList.length != 0) {
                if (vm.fileList[0].file == undefined) {
                    formdata.append("url", "");
                } else {
                    var photoUrl = vm.fileList[0].file;
                    formdata.append("fileName", photoUrl);
                }
            }else{
                formdata.append("fileName", "");
            }*/
            if(vm.hsdPhoto.name==null || vm.hsdPhoto.name==""){
                alert("图片描述不能为空")
                return
            }else {
                formdata.append("name",vm.hsdPhoto.name);
            }
            if(vm.fileList.length==0){
            	alert("图片不能为空")
				return
			}else {
                if (vm.fileList[0].file == undefined) {
                    formdata.append("fileName", "");
                } else {
                    var photoUrl = vm.fileList[0].file;
                    formdata.append("fileName", photoUrl);
                }
			}
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                dataType: 'json',
                contentType: false,
                processData:false,
                data:formdata,
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
				    url: baseURL + "sys/photoGallery/delete",
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
			$.get(baseURL + "sys/photoGallery/info/"+id, function(r){
                vm.hsdPhoto = r.hsdPhoto;
                vm.fileList.push(  { url: "http://192.168.50.16:8080"+r.hsdPhoto.url, isImage: true })
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
			vm.fileList=[]
		}
	}
});
function alarmFormatter(cellvalue, options, rowdata) {
    return ' <img src="http://192.168.50.16:8080' + rowdata.url + '"  style="width:50px;height:50px;" />';
};
