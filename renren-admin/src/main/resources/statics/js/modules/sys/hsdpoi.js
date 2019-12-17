function  initlist () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/hsdpoi/findAll',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 30, key: true },
            { label: '所属图层', name: 'typeMenu', index: 'typeMenu', width: 80 },
            //{ label: '所属图层', name: 'typeId',  width: 60, align: "center", sortable: false, editable: false, formatter: alarmFormatter },
			{ label: '名称', name: 'name', index: 'name', width: 80 },
			{ label: '经度', name: 'lng', index: 'lng', width: 80 }, 			
			{ label: '纬度', name: 'lat', index: 'lat', width: 80 },
			{ label: '电话', name: 'tel', index: 'tel', width: 80 }, 			
			{ label: '地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '部门', name: 'depart', index: 'depart', width: 80 }, 			
			{ label: '简介', name: 'jianjie', index: 'jianjie', width: 80 }, 			
			{ label: '历史', name: 'history', index: 'history', width: 80 }

        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50,
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
}
function alarmFormatter(cellvalue, options, rowdata) {
    var name='';
    var zz=vm.cover;
    if (rowdata.typeId==null){
        name=''
    }
    else{
        for (var i=0;i<vm.cover.length;i++){
            var tcname=vm.cover[i].typeMenu;
            var tcid=vm.cover[i].id;
            if (tcid==rowdata.typeId){
                name=tcname;
            }
        }
    }

    return ' <p>' + name + '</p>';
}
getCover();
function getCover() {
    $.ajax({
        type: "POST",
        url: baseURL + "sys/hsdCover/queryAll",
        contentType: "application/json",
        //data: JSON.stringify(id),
        success: function(r){
            if(r.code === 0){
                vm.cover=r.page.list
                initlist();
            }else{
                alert(r.msg);
            }
        }
    });
}
var vm = new Vue({
	el:'#rrapp',
	data:{
        lat:'',
        lng:'',
        q:{
            name: null
        },
		showList: true,
		title: null,
		hsdPoi: {
            name:"",
            lat:'',
            lng:'',
            tel:"",
            address:"",
            type:"",
            jianjie:"",
            history:"",
            depart:"",
            typeId:"",
        },
        fileList: [],
        options: [
            {
                value: "1",
                label: '生活类'
            },
            {
                value: "2",
                label: '交通类'
            },
            {
                value: "3",
                label: '文体类'
            },
            {
                value: "4",
                label: '单位类'
            }
        ],
        typeList:[],
        value1: '',
        value2: '',
        cover:[],
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
            vm.fileList=[];
			vm.showList = false;
			vm.title = "新增";

		},
		update: function (event) {
			var id = getSelectedRow();
            ///vm.fileList=[];
			if(id == null){
				return ;
			}
            //点击修改按钮，发送ajax请求，获取图片数据路径放到fileList集合中，回显图片
            $.ajax({
                type: "POST",
                url: baseURL + "sys/hsdpoi/findPic",
                contentType: "application/json",
                data: JSON.stringify(id),
                success: function(r){
                    if(r.code === 0){
                        var picList = r.hsdPic.length;
                        for(var i = 0;i< picList;i++){
                            var picUrl = r.hsdPic[i].picture;
                            vm.fileList.push({ url:picUrl});
                        }
                        console.log(vm.fileList)
                    }else{
                        alert(r.msg);
                    }
                }
            });
            vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {

		    //$('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.hsdPoi.id == null ? "sys/hsdpoi/save" : "sys/hsdpoi/update";

                var formdata=new FormData();
                var files = vm.fileList;  // 获取上传图片的文件
                for(var i = 0;i<files.length;i++){
                    if(files[i].file == undefined){
                        formdata.append("picture",files[i].url);
                    }else{
                        formdata.append("filename",files[i].file);
                    }

                }

                if(url=="sys/hsdpoi/update"){
                    formdata.append("id",vm.hsdPoi.id);
                }
                if(vm.hsdPoi.name==null || vm.hsdPoi.name==""){
                    alert("名称不能为空");
                    return
                }else {
                    formdata.append('name',vm.hsdPoi.name);
                }
                if(vm.lng==null || vm.lng==""){
                    alert("经纬度不能为空");
                    return
                }else {
                    formdata.append('lng',vm.lng);
                    formdata.append('lat',vm.lat);
                }
                formdata.append('tel',vm.hsdPoi.tel);
                formdata.append('address',vm.hsdPoi.address);
                //formdata.append('type',vm.hsdPoi.type);
                formdata.append('depart',vm.hsdPoi.depart);
                formdata.append('jianjie',vm.hsdPoi.jianjie);
                formdata.append('history',vm.hsdPoi.history);
                if(vm.value2==null || vm.value2==""){
                    alert("所属图层不能为空");
                    return
                }else{
                    formdata.append('typeId',vm.value2);
                }
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    dataType: 'json',
                    data: formdata,
                    contentType:false,//ajax上传图片需要添加
                    processData:false,//ajax上传图片需要添加
                   // data: JSON.stringify(vm.hsdPoi),
                    success: function(r){
                        /*vm.hsdPoi.typeId="";
                        vm.hsdPoi.name="";
                        vm.hsdPoi.lat="";
                        vm.hsdPoi.lng="";
                        vm.hsdPoi.tel="";
                        vm.hsdPoi.jianjie="";
                        vm.hsdPoi.depart="";
                        vm.hsdPoi.address="";
                        vm.hsdPoi.history="";
                        vm.hsdPoi.type="";
                        vm.lat="";
                        vm.lng="";
                        vm.value1="";
                        vm.value2="";
                        vm.fileList="";*/
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                             //$('#btnSaveOrUpdate').button('reset');
                             //$('#btnSaveOrUpdate').dequeue();
                        }else{
                            alert(r.msg);
                           // $('#btnSaveOrUpdate').button('reset');
                            //$('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			//});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/hsdpoi/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "sys/hsdpoi/info/"+id, function(r){
                vm.hsdPoi = r.hsdPoi;
                vm.lat = r.hsdPoi.lat;
                vm.lng = r.hsdPoi.lng;
                vm.value1 = r.hsdPoi.typeMenu;
                vm.value2 = r.hsdPoi.typeId;
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/hsdCover/queryMenu",
                    contentType: "application/json",
                    processData:false,
                    data:JSON.parse(vm.value1),
                    //data: JSON.stringify(ids),
                    success: function(r){
                        vm.typeList=r.hsdcover;
                        console.log(vm.typeList)
                    }
                });
            });
		},

		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name':vm.q.name},
                page:page
            }).trigger("reloadGrid");
            vm.hsdPoi.typeId="";
            vm.hsdPoi.name="";
            vm.hsdPoi.lat="";
            vm.hsdPoi.lng="";
            vm.hsdPoi.tel="";
            vm.hsdPoi.jianjie="";
            vm.hsdPoi.depart="";
            vm.hsdPoi.address="";
            vm.hsdPoi.history="";
            vm.hsdPoi.type="";
            vm.lat="";
            vm.lng="";
            vm.value1="";
            vm.value2="";
            vm.fileList=[];
            $('#btnSaveOrUpdate').button('reset');
            $('#btnSaveOrUpdate').dequeue();
		},
        deletePic:function(file,index){
            if(file.file == null){  //如果选择的是修改操作，点击删除图片执行下面操作
                console.log(vm.fileList)
                var picUrl = file.url;
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/hsdpoi/clickDelPic",
                    contentType: "application/json",
                    data: JSON.stringify(picUrl),
                    success: function(r){
                        if(r.code === 0){
                           /* for(var i = 0;i< r.hsdPic.length;i++){
                                var picUrl = r.hsdPic[i].picture;
                                vm.fileList.push({ url: picUrl});
                            }*/
                            vm.fileList.splice(index.index,1)
                            layer.msg("删除成功", {icon: 1});
                        }else{
                            alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            }else {                         //如果是新建，执行下面操作，点击删除单张图片
                vm.fileList.splice(index.index,1)
            }
        },
        clickHsdCover : function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/hsdCover/queryMenu",
                contentType: "application/json",
                processData:false,
                data:JSON.parse(vm.value1),
                //data: JSON.stringify(ids),
                success: function(r){
                    vm.typeList=r.hsdcover;
                    console.log(vm.typeList)
                }
            });
        }
        

	}
});

