$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/hsdCover/queryAll',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
            { label: '所属分类', name: 'typeMenu', sortable: false, width: 60 },
			{ label: '图层名称', name: 'typeName', sortable: false, width: 60 },
            { label: '图标', name: 'typePhoto',  width: 60, align: "center", sortable: false, editable: false, formatter: alarmFormatter },
			{ label: '创建人', name: 'createUser',  sortable:false,width: 60 },
            { label: '创建时间', name: 'createTime', sortable:false,width: 60 },
            { label: '修改时间', name: 'updateTime', width: 60 },

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
            typeName: null
		},
		showList: true,
		title: null,
        fileList:[],
        cityList: [
            {
                value: '1',
                label: '生活类'
            },
            {
                value: '2',
                label: '交通类'
            },
            {
                value: '3',
                label: '文体类'
            },
            {
                value: '4',
                label: '单位类'
            },
            {
                value: '5',
                label: '景观类'
            }
        ],
        hsdcover:{
		    typeName:"",
			typePhoto:"",
			typeMenu:"",
			createUser:"",
		},
        model:"",
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fileList = [];
		},
		update: function () {
			var id = getSelectedRow();
            vm.fileList = [];
			if(id == null){
				return ;
			}
			
			$.get(baseURL + "sys/hsdCover/findById/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.hsdcover = r.hsdcover;
                console.log(vm.hsdcover.typePhoto)
                vm.fileList.push(  { url: "http://192.168.50.16:8080"+r.hsdcover.typePhoto, isImage: true })
				vm.model=r.hsdcover.typeMenu;
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
				    url: baseURL + "sys/hsdCover/deleteMessage",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();

							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.hsdcover.id == null ? "sys/hsdCover/saveMessage" : "sys/hsdCover/updateMessage";
            var formdata=new FormData();
            if(url=="sys/hsdCover/updateMessage"){
                formdata.append("id",vm.hsdcover.id);
            }
            if(vm.fileList.length != 0) {
                if (vm.fileList[0].file == undefined) {
                    formdata.append("fileName", "");
                } else {
                    var photoUrl = vm.fileList[0].file;
                    formdata.append("fileName", photoUrl);
                }
            }else{
                formdata.append("fileName", "");
			}
			if(vm.model==null || vm.model==""){
			    alert("所属分类不能为空")
                return
            }else {
                formdata.append("typeMenu",vm.model);
            }
            if(vm.hsdcover.typeName==null || vm.hsdcover.typeName==""){
                alert("图层名称不能为空")
                return
            }else {
                formdata.append("typeName",vm.hsdcover.typeName);
            }
            //formdata.append("createUser",vm.hsdcover.createUser);
			console.log(formdata)
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                dataType: 'json',
                contentType: false,
                processData:false,
				data:formdata,
			    //data: JSON.stringify(vm.hsdcover),
			    success: function(r){
			    	if(r.code === 0){
			    	    vm.model="";
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'typeName': vm.q.typeName},
                page:page
            }).trigger("reloadGrid");
			vm.model="";
			vm.hsdcover.createUser="";
			vm.hsdcover.typeName="";
		},
	},

});
function alarmFormatter(cellvalue, options, rowdata) {
    return ' <img src="http://192.168.50.16:8080' + rowdata.typePhoto + '"  style="width:50px;height:50px;" />';
};

